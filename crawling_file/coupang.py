from playwright.sync_api import sync_playwright
import random, time, asyncio, re, json, requests


randint = random.uniform(1.4, 5.8)
term = time.sleep(randint)

server_url = "http://localhost:8080/api/product/put/coupang/notebook"
url2 = "https://www.coupang.com/"

headers = {"Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
           "Accept-Language": "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7",
           "Referer": "https://www.google.com",
           "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36"}

headers2 = {"Content-Type": "application/json"}
product_list = ["냉장고", "노트북", ""]



"https://www.coupang.com/np/search?q={상품}&channel=recent&component=&eventCategory=SRP&trcid=&traid=&sorter=scoreDesc&minPrice=&maxPrice=&"
"priceRange=&filterType=&listSize=36&filter=&isPriceRange=false&brand=&offerCondition=&rating=0&page={페이지}&rocketAll=false&searchIndexingToken=1=9&backgroundColor="

with sync_playwright() as p:
    browser = p.chromium.launch()
    context = browser.new_context(extra_http_headers = headers)
    page = context.new_page()
    page.goto("https://www.coupang.com/np/search?q=냉장고&channel=recent&component=&eventCategory=SRP&trcid=&traid=&sorter=scoreDesc&minPrice=&maxPrice=&priceRange=&filterType=&listSize=36&filter=&isPriceRange=false&brand=&offerCondition=&rating=0&page=1&rocketAll=false&searchIndexingToken=1=9&backgroundColor=")
    term
    dct = dict()
    item_list = page.locator("li.search-product").all()
    for i in item_list:
        dct["productName"] = i.locator(".name").inner_text()
        dct["price"] = int(re.sub(r",", "", i.locator(".price-value").inner_text()))
        print(json.dumps(dct, ensure_ascii = False))
        requests.put(server_url, json = dct, headers = headers2)
        print(f"put data {dct['productName']}\n{dct['price']} in server")

    term
    browser.close()