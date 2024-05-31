package org.example.ProductsRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CPNotebookRepository extends JpaRepository<CPNotebook, Integer> {

}