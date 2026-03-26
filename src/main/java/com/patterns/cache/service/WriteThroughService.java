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
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WriteThroughService {
    private final ProductRepository repository;
    private final ProductCacheRepository cacheRepository;
    private final ProductMapper mapper;

    @Transactional
    public ProductResponse createProductWriteThrough(ProductRequest request) {
        Product product = repository.save(mapper.toEntity(request));
        log.info("Write-Through: Product saved in DB!");
        try {
            cacheRepository.save(mapper.toCache(product));
            log.info("Write-Through: Product saved in Cache!");
        } catch (Exception e) {
            log.error("Write-Through: Cache save failed, rolling back DB transaction", e);
            throw new RuntimeException("Failed to save to cache", e);
        }
        return mapper.toResponse(product);
    }

    @Transactional
    public ProductResponse updateProductWriteThrough(Long id, ProductRequest request) {
        Product product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        mapper.updateEntity(request, product);
        Product updated = repository.save(product);
        log.info("Write-Through: Product updated in DB!");
        try {
            cacheRepository.save(mapper.toCache(updated));
            log.info("Write-Through: Product Updated in Cache!");
        } catch (Exception e) {
            log.error("Write-Through: Cache update failed, rolling back DB transaction", e);
            throw new RuntimeException("Failed to update cache", e);
        }
        return mapper.toResponse(updated);
    }

}
