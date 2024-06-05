from playwright.sync_api import sync_playwright
import asyncio, aiohttp
# Circular import error, module dependent

async def is_exist_element(Locator_Frame, element):
    """
    find element in locator frame \n
    if you want to return False when element is not in locator frame, use this
    """
    element_cnt = len(await Locator_Frame.locator(element).all())

    if element_cnt > 0:
        return True
    else:
        return False
        
async def put_request(url, json_data, headers):
    """
    RestAPI request apply for async.
    need url, data(json_type) and header
    """
    async with aiohttp.ClientSession(headers = headers) as session:
        async with session.put(url, json = json_data) as response:
            return await response.text()