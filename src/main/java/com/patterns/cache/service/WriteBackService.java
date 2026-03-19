package com.patterns.cache.service;

import com.patterns.cache.dto.ProductRequest;
import com.patterns.cache.dto.ProductResponse;
import com.patterns.cache.entity.Product;
import com.patterns.cache.entity.ProductCache;
import com.patterns.cache.exception.ProductNotFoundException;
import com.patterns.cache.mapper.ProductMapper;
import com.patterns.cache.repository.ProductCacheRepository;
import com.patterns.cache.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WriteBackService {

    private final ProductRepository productRepository;
    private final ProductCacheRepository productCacheRepository;
    private final ProductMapper mapper;

    public ProductResponse updateProductWriteBack(Long id, ProductRequest request) {
        ProductCache cacheEntity = mapper.toCacheFromRequest(id, request);
        productCacheRepository.save(cacheEntity);

        persistAsync(id, request);
        return mapper.toResponseFromCache(cacheEntity);
    }

    public ProductResponse updateProductWriteBackQueue(Long id, ProductRequest request) {
        return null;
    }


    @Async
    public void persistAsync(Long id, ProductRequest request) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        mapper.updateEntity(request, product);
        productRepository.save(product);
    }
}
