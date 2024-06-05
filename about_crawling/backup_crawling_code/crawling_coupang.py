from playwright.async_api import async_playwright
import random, re, json, asyncio, aiohttp, time
from crawling_utils import *
from pymongo import MongoClient
from datetime import datetime
import re, asyncio

str_pass = "mongodb+srv://owen:test123123@cluster0.ijpzrma.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
client = MongoClient(str_pass)
db = client['http_request_meta_data']
meta_collection = db['page_meta_data']


randint = random.uniform(1.4, 3.2)

bserver_url = "http://localhost:80/api/product/put/coupang/"


# crawling header
headers = {"Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
           "Accept-Language": "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7",
           "Referer": "https://www.google.com",
           "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36"}

# backend server header
headers2 = {"Content-Type": "application/json"}

items = {
    '노트북': 'notebook',
    '냉장고': 'refrigerator',
}

async def crawling_from_coupang(item, page_num, item_eng):
    """
    loads coupang's item that you want.
    this function needs item, page, item_name - "lower letter in english, only singular" 
    for example crawling_from_coupang("노트북", 1, "notebook")
    """
    async with async_playwright() as p:
            browser = await p.chromium.launch(proxy={"server": "socks5://localhost:9050"})
            context = await browser.new_context(extra_http_headers = headers)
            page = await context.new_page()

            pggt = await page.goto(f"https://www.coupang.com/np/search?q={item}&channel=recent&component=&eventCategory=SRP&trcid=&traid=&sorter=scoreDesc&minPrice=&maxPrice=&priceRange=&filterType=&listSize=36&filter=&isPriceRange=false&brand=&offerCondition=&rating=0&page={page_num}&rocketAll=false&searchIndexingToken=1=9&backgroundColor=")
            
            meta_data = {
            'request': {
                'method':  pggt.request.method,
                'url':  pggt.request.url,
                'domain': re.sub(r'/$', '', re.sub(r'^https?://', '',  pggt.request.url)),
                'header':  dict(pggt.request.headers)
            },
            'response': {
                'status':  pggt.status,
                'url':  pggt.url,
                'domain': re.sub(r'/$', '', re.sub(r'^https?://', '',  pggt.url)),
                'header':  dict(pggt.headers)
            },
            'timestamp_page':  pggt.headers['date'],
            'timestamp_store':  datetime.utcnow(),
            'tag' : item,
            'tag2' : page_num
            }
        
            meta_collection.insert_one(meta_data)

            pggt
            await asyncio.sleep(randint)
            dct = dict()
            item_list = await page.locator("li.search-product").all()
            for p in item_list:
                dct["product_name"] = await p.locator(".name").inner_text()
                
                if await is_exist_element(p, ".price-value") == True:
                    dct["discount_price"] = int(re.sub(",", "", await p.locator(".price-value").inner_text()))
                else:
                    dct["discount_price"] = 0

                if await is_exist_element(p, ".base-price") == True:
                    dct["origin_price"] = int(re.sub(",", "", await p.locator(".base-price").inner_text()))
                else:
                    dct["origin_price"] = 0            
                
                if await is_exist_element(p, ".rocket.badge") == True:
                    dct["rocket"] = "True"
                else:
                    dct["rocket"] = "False"

                if await is_exist_element(p, ".rating") == True:
                    dct['rating'] = float(await p.locator(".rating").inner_text())
                else:
                    dct['rating'] = 0

                if await is_exist_element(p, ".ccid-txt") == True:
                    dct["card_charge_discount_wow_only"] = await p.locator(".ccid-txt").inner_text()
                else:
                    dct["card_charge_discount_wow_only"] = "False"                

                
                if await is_exist_element(p, ".reward-cash-txt") == True:
                    dct["coucash_payback_wow_only"] = await p.locator(".reward-cash-txt").inner_text()
                else:
                    dct["coucash_payback_wow_only"] = "False"          

                dct["page_num"] = page_num

                await put_request(bserver_url + item_eng, dct, headers2)
                print(f"""{dct["product_name"]} \n
                          {dct["discount_price"]} \n
                          {dct["origin_price"]} \n
                          {dct["rocket"]} {dct['rating']} \n
                          {dct["card_charge_discount_wow_only"]} \n
                          {dct["coucash_payback_wow_only"]}
                          {dct["page_num"]} """)

            await browser.close()

async def main(total_page):
    start_time = time.time() 
    tasks = list()

    for item, item_eng in items.items():
        for page_num in range(1, total_page + 1):
            task = asyncio.create_task(crawling_from_coupang(item, page_num, item_eng))
            tasks.append(task)

    await asyncio.gather(*tasks)
    end_time = time.time()

    elapsed_time = end_time - start_time  
    print(f"Total elapsed time: {elapsed_time} seconds")

if __name__ == "__main__":
    total_page = 5
    asyncio.run(main(total_page))