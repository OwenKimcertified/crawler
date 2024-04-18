from playwright.sync_api import sync_playwright
import random, time, re, json, requests 
from crawling_utils import is_exist_element
# import asynico

randint = random.uniform(1.4, 5.8)
term = time.sleep(randint)

server_url = "http://localhost:8080/api/product/put/coupang/notebook"

# secret
