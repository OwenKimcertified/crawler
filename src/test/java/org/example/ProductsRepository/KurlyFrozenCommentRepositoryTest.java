package org.example.ProductsRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class KurlyFrozenCommentRepositoryTest {

    @Autowired
    private KurlyFrozenRepository kurlyFrozenRepository;

    @Autowired
    private KurlyFrozenCommentRepository kurlyFrozenCommentRepository;

    private KurlyFrozen kurlyFrozen;

    @BeforeEach
    void setUp() {
        kurlyFrozen = KurlyFrozen.builder()
                .name("Frozen Pizza")
                .describe("Delicious frozen pizza")
                .origin_price(1000)
                .discount_price(800)
                .reply_count(10)
                .lucifer("test_lucifer")
                .build();
        kurlyFrozenRepository.save(kurlyFrozen);
    }

    @Test
    public void testSaveComment() {
        KurlyFrozenComment comment = KurlyFrozenComment.builder()
                .comment("Great pizza!")
                .build();

        kurlyFrozenCommentRepository.save(comment);

        assertThat(comment.getId()).isNotNull();
        assertThat(comment.getComment()).isEqualTo("Great pizza!");
        assertThat(comment.getKurlyFrozen()).isEqualTo(kurlyFrozen);
    }

    @Test
    public void testFindCommentById() {
        KurlyFrozenComment comment = KurlyFrozenComment.builder()
                .comment("Great pizza!")
                .build();

        kurlyFrozenCommentRepository.save(comment);

        KurlyFrozenComment foundComment = kurlyFrozenCommentRepository.findById(comment.getId()).orElse(null);

        assertThat(foundComment).isNotNull();
        assertThat(foundComment.getId()).isEqualTo(comment.getId());
        assertThat(foundComment.getComment()).isEqualTo("Great pizza!");
    }

    @Test
    public void testDeleteComment() {
        KurlyFrozenComment comment = KurlyFrozenComment.builder()
                .comment("Great pizza!")
                .build();

        kurlyFrozenCommentRepository.save(comment);

        kurlyFrozenCommentRepository.delete(comment);

        assertThat(kurlyFrozenCommentRepository.findById(comment.getId())).isEmpty();
    }
}