package org.example.ProductLogic;
import org.example.ProductsRepository.KurlyFrozenComment;
import org.example.ProductsRepository.KurlyFrozenCommentRepository;
import org.example.ProductsRepository.KurlyFrozenRepository;
import org.example.ProductsRepository.KurlyFrozen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ProductService {

    @Autowired
    private KurlyFrozenCommentRepository kurlyFrozenCommentRepository;

    @Autowired
    private KurlyFrozenRepository kurlyFrozenRepository;

    @Transactional
    public void saveProductFrozen(KurlyFrozen productFrozen) {
        kurlyFrozenRepository.save(productFrozen);
    }
    public List<KurlyFrozen> getAllFrozen() {
        return kurlyFrozenRepository.findAll();
    }

    public KurlyFrozen getProductById(Long id) {
        return kurlyFrozenRepository.findById(id).orElse(null);
    }

    public KurlyFrozen getProductByEndpoint(String endpoint) {
        return kurlyFrozenRepository.findByEndpoint(endpoint).orElse(null);
    }

    @Transactional
    public void saveComment(KurlyFrozenComment kurlyFrozenComment) {
        kurlyFrozenCommentRepository.save(kurlyFrozenComment);
    }

//    public void saveRefrigerator(CPRefrigerator product) {
//        cpRefrigeratorRepository.save(product);
//    }
//
//    public List<CPRefrigerator> getAllRefrigerator() {
//        return cpRefrigeratorRepository.findAll();
//    }
//
//    @Autowired
//    private CPNotebookRepository cpNotebookRepository;
//
//    public void saveNotebook(CPNotebook notebook) {
//        cpNotebookRepository.save(notebook);
//    }
//
//    public List<CPNotebook> getAllNotebook() {
//        return cpNotebookRepository.findAll();
//    }
}