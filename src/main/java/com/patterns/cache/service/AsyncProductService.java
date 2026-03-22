package com.patterns.cache.service;

import com.patterns.cache.dto.ProductRequest;
import com.patterns.cache.entity.Product;
import com.patterns.cache.exception.ProductNotFoundException;
import com.patterns.cache.mapper.ProductMapper;
import com.patterns.cache.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AsyncProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Async("writeBackExecutor")
    public void persistAsync(Long id, ProductRequest request) {
        Product product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        log.info("Write-Back-Async: Found product in DB, starting operation");
        mapper.updateEntity(request, product);
        repository.save(product);
        log.info("Write-Back-Async: Product Updated");
    }
}
