from playwright.sync_api import sync_playwright
import random, time, asyncio, re, json, requests
from sync_utils import *

randint = random.uniform(1.4, 5.8)
term = time.sleep(randint)

server_url = "http://localhost:80/api/product/put/coupang/notebook"
url2 = "https://www.coupang.com/"

headers = {    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36",
                "Accept-Language": "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7",
                "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
                "Referer": "https://www.google.com"}

headers2 = {"Content-Type": "application/json"}
start_time = time.time()

for k in range(1,2):
    with sync_playwright() as p:
        browser = p.chromium.launch(proxy={"server": "socks5://localhost:9050"})
        context = browser.new_context(extra_http_headers = headers)
        page = context.new_page()
        page.goto(f"https://www.coupang.com/np/search?q=냉장고&channel=recent&component=&eventCategory=SRP&trcid=&traid=&sorter=scoreDesc&minPrice=&maxPrice=&priceRange=&filterType=&listSize=36&filter=&isPriceRange=false&brand=&offerCondition=&rating=0&page={k}&rocketAll=false&searchIndexingToken=1=9&backgroundColor=")
        term
        dct = dict()
        item_list = page.locator("li.search-product").all()
        for p in item_list:
            dct["product_name"] = p.locator(".name").inner_text()
            
            if is_exist_element(p, ".price-value") == True:
                dct["discount_price"] = int(re.sub(",", "", p.locator(".price-value").inner_text()))
            else:
                dct["discount_price"] = 0

            if is_exist_element(p, ".base-price") == True:
                dct["origin_price"] = int(re.sub(",", "", p.locator(".base-price").inner_text()))
            else:
                dct["origin_price"] = 0            
            
            if is_exist_element(p, ".rocket.badge") == True:
                dct["rocket"] = "True"
            else:
                dct["rocket"] = "False"

            if is_exist_element(p, ".rating") == True:
                dct['rating'] = float(p.locator(".rating").inner_text())
            else:
                dct['rating'] = 0

            if is_exist_element(p, ".ccid-txt") == True:
                dct["card_charge_discount_wow_only"] = p.locator(".ccid-txt").inner_text()
            else:
                dct["card_charge_discount_wow_only"] = "False"                

            
            if is_exist_element(p, ".reward-cash-txt") == True:
                dct["coucash_payback_wow_only"] = p.locator(".reward-cash-txt").inner_text()
            else:
                dct["coucash_payback_wow_only"] = "False"     
            
            print(json.dumps(dct, ensure_ascii = False))
            requests.put(server_url, json = dct, headers = headers2)
            print(f"put data {dct['product_name']}\n{dct['origin_price']}\n{dct['discount_price']}\n{dct['rocket']}\n{dct['rating']}\n{dct['card_charge_discount_wow_only']}\n{dct['coucash_payback_wow_only']} in server")

        term
        browser.close()

    end_time = time.time()

    elapsed_time = end_time - start_time
    print(f"Total elapsed time: {elapsed_time} seconds")