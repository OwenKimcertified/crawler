from playwright.sync_api import sync_playwright
# Circular import error, module dependent

def is_exist_element(Locator_Frame, element):
    """
    find element in locator frame \n
    if you want to return False when element is not in locator frame, use this
    """

    try:
        Locator_Frame.wait_for_selector(element, timeout = 3000)
        return True
    except:
        return False