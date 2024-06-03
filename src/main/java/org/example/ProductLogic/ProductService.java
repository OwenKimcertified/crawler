package org.example.ProductLogic;
import org.example.ProductsRepository.CPNotebook;
import org.example.ProductsRepository.CPNotebookRepository;
import org.example.ProductsRepository.CPRefrigerator;
import org.example.ProductsRepository.CPRefrigeratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {

    @Autowired
    private CPRefrigeratorRepository cpRefrigeratorRepository;

    public void saveRefrigerator(CPRefrigerator product) {
        cpRefrigeratorRepository.save(product);
    }

    public List<CPRefrigerator> getAllRefrigerator() {
        return cpRefrigeratorRepository.findAll();
    }

    @Autowired
    private CPNotebookRepository cpNotebookRepository;

    public void saveNotebook(CPNotebook notebook) {
        cpNotebookRepository.save(notebook);
    }

    public List<CPNotebook> getAllNotebook() {
        return cpNotebookRepository.findAll();
    }
}