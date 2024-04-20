package org.example.Products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CPRefrigeratorRepository extends JpaRepository<CPRefrigerator, Integer> {

}