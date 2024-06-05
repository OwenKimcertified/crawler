from airflow import DAG
from airflow.operators.python import PythonOperator
from kafka import KafkaConsumer, TopicPartition
from kafka.admin import KafkaAdminClient
from datetime import datetime
from google.cloud import bigquery
from google.oauth2 import service_account
import pymongo, re

# parser

def parse(string):
    pattern = r'(\w+)\s*=\s*(.*?)(?=\s*,\s*\w+\s*=|\s*})'
    matches = re.findall(pattern, string)
    parsed_dict = {}
    for key, value in matches:
        try:
            parsed_dict[key] = int(value)
        except ValueError:
            try:
                parsed_dict[key] = float(value)
            except ValueError:
                parsed_dict[key] = value.strip("'")
    
    return parsed_dict

# bigquery args

credentials = service_account.Credentials.from_service_account_file(
    './config/google_auth_key.json')

gcp_client = bigquery.Client(credentials = credentials, project = 'decoded-oxide-401216')
dset = 'daily_crawling_data'
schema = [
    bigquery.SchemaField("id", "INTEGER"),
    bigquery.SchemaField("product_name", "STRING"),
    bigquery.SchemaField("origin_price", "FLOAT"),
    bigquery.SchemaField("discount_price", "FLOAT"),
    bigquery.SchemaField("rocket", "BOOLEAN"),
    bigquery.SchemaField("rating", "FLOAT"),
    bigquery.SchemaField("card_charge_discount_wow_only", "STRING"),
    bigquery.SchemaField("coucash_payback_wow_only", "STRING"),
    bigquery.SchemaField("page_num", "INTEGER"),
]

# mongodb args

str_pass = "mongodb+srv://owen:test123123@cluster0.ijpzrma.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
mgdb_client = pymongo.MongoClient(str_pass)
db = mgdb_client['http_request_meta_data']
meta_collection = db['page_meta_data']

# kafka args

brokers = ["kafka1:19091", "kafka2:19092", "kafka3:19093"]

consumer = KafkaConsumer(
    group_id = "test",  
    bootstrap_servers = brokers, 
    auto_offset_reset = "earliest",  # offset reset
    fetch_max_bytes = 52428800,  # max byte
    max_poll_records = 500,  # max rec
    value_deserializer = lambda x: x.decode('utf-8')
)

admin_client = KafkaAdminClient(
    bootstrap_servers = brokers
)


### dag code start

# airflow args

default_args = {
    'owner': 'airflow',
    'start_date': datetime.now(),
    'retries': 1
}

def create_table(**context):
    pass


def consume_from_kafka(**context): 
    topic_list = [tpics for tpics in admin_client.list_topics() if tpics.startswith("put")]
    meta_list = [docs for docs in meta_collection.find()]

    for topics, meta_data in zip(topic_list, meta_list):
        table_id = f"{datetime.now().date().strftime('%Y-%m-%d')}{topics}"
        table_ref = gcp_client.dataset(dset).table(table_id)
        table = bigquery.Table(table_ref, schema = schema)
        gcp_client.create_table(table)
        
        if meta_data["timestamp_store"].date() == default_args["start_date"].date():
            tp = TopicPartition(topics, 0)
            consumer.assign([tp])
            for _, msgs_list in consumer.poll(timeout_ms = 1000).items():
                data_to_insert = []
                for msg in msgs_list:
                    print(f"Received message: {msg.value}")
                    dict_msg = parse(msg.value)
                    data_to_insert.append(dict_msg)
                gcp_client.insert_rows(table_ref, data_to_insert, selected_fields = table.schema)
            consumer.commit()
    consumer.close()
    

dag = DAG('kafka_consumer',
          default_args = default_args,
          description = 'Consume messages from Kafka topic',
          schedule_interval = None)

t1 = PythonOperator(
    task_id = 'kafka',
    python_callable = consume_from_kafka,
    dag = dag
)


t1