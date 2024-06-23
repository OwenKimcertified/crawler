package org.example.ProductsRepository;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "kurly_product_frozen_comment")
public class KurlyFrozenComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_comment", columnDefinition = "TEXT") // MySQL INF type
    private String comment;

    @Column(name = "product_key", unique = false)
    private String key;

    @Builder
    public KurlyFrozenComment(String comment, String key) {
        this.comment = comment;
        this.key = key;
    }
}
