package org.example.ProductsRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KurlyFrozenRepository extends JpaRepository<KurlyFrozen, Long>{
    Optional<KurlyFrozen> findByEndpoint(String endpoint);
}
