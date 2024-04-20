package org.example;
import org.example.Products.CPNotebook;
import org.example.Products.CPNotebookRepository;
import org.example.Products.CPRefrigerator;
import org.example.Products.CPRefrigeratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {

    @Autowired
    private CPRefrigeratorRepository cpRefrigeratorRepository;

    public void saveRefrigerator(CPRefrigerator product) {
        // Product 엔티티를 데이터베이스에 저장
        cpRefrigeratorRepository.save(product);
    }

    public List<CPRefrigerator> getAllRefrigerator() {
        // 데이터베이스에 저장된 모든 List<Refrigerator> 제품 조회
        return cpRefrigeratorRepository.findAll();
    }

    @Autowired
    private CPNotebookRepository cpNotebookRepository;

    public void saveNotebook(CPNotebook notebook) {
        cpNotebookRepository.save(notebook);
    }

    public List<CPNotebook> getAllNotebook() {
        // 데이터베이스에 저장된 모든 List<Refrigerator> 제품 조회
        return cpNotebookRepository.findAll();
    }
}