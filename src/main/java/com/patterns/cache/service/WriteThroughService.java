package com.patterns.cache.service;

import com.patterns.cache.dto.ProductRequest;
import com.patterns.cache.dto.ProductResponse;
import com.patterns.cache.entity.Product;
import com.patterns.cache.exception.ProductNotFoundException;
import com.patterns.cache.mapper.ProductMapper;
import com.patterns.cache.repository.ProductCacheRepository;
import com.patterns.cache.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WriteThroughService {
    private final ProductRepository repository;
    private final ProductCacheRepository productCacheRepository;
    private final ProductMapper mapper;

    public ProductResponse createProductWriteThrough(ProductRequest request) {
        Product product = repository.save(mapper.toEntity(request));
        log.info("Write-Through: Product saved in DB!");
        productCacheRepository.save(mapper.toCache(product));
        log.info("Write-Through: Product saved in Cache!");
        return mapper.toResponse(product);
    }

    public ProductResponse updateProductWriteThrough(Long id, ProductRequest request) {
        Product product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        mapper.updateEntity(request, product);
        Product updated = repository.save(product);
        log.info("Write-Through: Product updated in DB!");
        productCacheRepository.save(mapper.toCache(updated));
        log.info("Write-Through: Product Updated in Cache!");
        return mapper.toResponse(updated);
    }

}
