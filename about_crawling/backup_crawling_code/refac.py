from playwright.async_api import async_playwright
from pymongo import MongoClient
from datetime import datetime
from crawling_utils import *
import random, re, json, asyncio, aiohttp, time

# MongoDB 
str_pass = "mongodb+srv://owen:test123123@cluster0.ijpzrma.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
client = MongoClient(str_pass)
db = client['http_request_meta_data']
meta_collection = db['page_meta_data']

# url, header
bserver_url = "http://localhost:80/api/product/put/coupang/"
headers = {
    "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
    "Accept-Language": "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7",
    "Referer": "https://www.google.com",
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36",
    "Priority": "u=0, i"
}
headers2 = {"Content-Type": "application/json"}

# item list
items = {
    '노트북': 'notebook',
    '냉장고': 'refrigerator',
}

async def store_meta_data(request, response, item, page_num):
    meta_data = {
        'request': {
            'method': request.method,
            'url': request.url,
            'domain': re.sub(r'/$', '', re.sub(r'^https?://', '', request.url)),
            'header': dict(request.headers)
        },
        'response': {
            'status': response.status,
            'url': response.url,
            'domain': re.sub(r'/$', '', re.sub(r'^https?://', '', response.url)),
            'header': dict(response.headers)
        },
        'timestamp_page': response.headers['date'],
        'timestamp_store': datetime.utcnow(),
        'tag': item,
        'tag2': page_num
    }
    print(f"Storing meta data for {item} page {page_num}")
    meta_collection.insert_one(meta_data)

async def extract_product_data(page, item_eng, page_num):
    print(f"Extracting product data for {item_eng} page {page_num}")
    try:
        item_list = await page.locator("li.search-product").all()
        if not item_list:
            print(f"No products found for {item_eng} page {page_num}")
        for p in item_list:
            dct = {}
            dct["product_name"] = await p.locator(".name").inner_text()
            dct["discount_price"] = int(re.sub(",", "", await p.locator(".price-value").inner_text())) if await is_exist_element(p, ".price-value") else 0
            dct["origin_price"] = int(re.sub(",", "", await p.locator(".base-price").inner_text())) if await is_exist_element(p, ".base-price") else 0
            dct["rocket"] = "True" if await is_exist_element(p, ".rocket.badge") else "False"
            dct['rating'] = float(await p.locator(".rating").inner_text()) if await is_exist_element(p, ".rating") else 0
            dct["card_charge_discount_wow_only"] = await p.locator(".ccid-txt").inner_text() if await is_exist_element(p, ".ccid-txt") else "False"
            dct["coucash_payback_wow_only"] = await p.locator(".reward-cash-txt").inner_text() if await is_exist_element(p, ".reward-cash-txt") else "False"
            dct["page_num"] = page_num

            print(f"Extracted data: {dct}")
            await put_request(bserver_url + item_eng, dct, headers2)
    except Exception as e:
        print(f"Error extracting product data for {item_eng} page {page_num}: {e}")

async def crawl_page(p, item, page_num, item_eng):
    context = await p.chromium.launch(
        proxy={"server": "socks5://localhost:9050"}
    )
    page = await context.new_page()
    print(f"Crawling {item} page {page_num}")
    response = await page.goto(f"https://www.coupang.com/np/search?q={item}&channel=recent&component=&eventCategory=SRP&trcid=&traid=&sorter=scoreDesc&minPrice=&maxPrice=&priceRange=&filterType=&listSize=36&filter=&isPriceRange=false&brand=&offerCondition=&rating=0&page={page_num}&rocketAll=false&searchIndexingToken=1=9&backgroundColor=", timeout=60000)
    
    await store_meta_data(response.request, response, item, page_num)
    await extract_product_data(page, item_eng, page_num)

    await context.close()

async def crawl_with_delay(p, item, page_num, item_eng):
    await crawl_page(p, item, page_num, item_eng)
    delay = random.uniform(1.4, 3.2)
    print(f"Sleeping for {delay} seconds")
    await asyncio.sleep(delay)

async def main(total_page):
    start_time = time.time()
    async with async_playwright() as p:
        tasks = [crawl_with_delay(p, item, page_num, item_eng) for item, item_eng in items.items() for page_num in range(1, total_page + 1)]
        await asyncio.gather(*tasks)
    elapsed_time = time.time() - start_time
    print(f"Total elapsed time: {elapsed_time} seconds")

if __name__ == "__main__":
    total_page = 3
    asyncio.run(main(total_page))