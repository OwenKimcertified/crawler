from playwright.sync_api import sync_playwright

def fetch_comments_from_page(page):
    comments = page.query_selector_all(".comment")
    return [comment.inner_text() for comment in comments]

def comment_batch_generator(url, batch_size=32):
    with sync_playwright() as p:
        browser = p.chromium.launch()
        page = browser.new_page()
        page.goto(url)

        comments = []
        while True:
            new_comments = fetch_comments_from_page(page)
            if not new_comments:
                break
            comments.extend(new_comments)

            while len(comments) >= batch_size:
                batch = comments[:batch_size]
                comments = comments[batch_size:]
                yield batch

            # 다음 페이지로 이동
            next_button = page.query_selector(".next-page")
            if next_button and next_button.is_visible():
                next_button.click()
                page.wait_for_load_state("networkidle")
            else:
                break

        # 남아 있는 댓글들을 마지막 배치로 반환
        if comments:
            yield comments

        browser.close()

# 예제 사용
url = "https://xxx.com/comments"  # 실제 댓글 페이지 URL로 대체
comment_batches = comment_batch_generator(url)

# 배치 출력
for batch in comment_batches:
    print(batch)