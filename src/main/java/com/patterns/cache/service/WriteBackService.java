package com.patterns.cache.service;

import com.patterns.cache.dto.ProductRequest;
import com.patterns.cache.dto.ProductResponse;
import com.patterns.cache.dto.WriteOperation;
import com.patterns.cache.entity.ProductCache;
import com.patterns.cache.mapper.ProductMapper;
import com.patterns.cache.repository.ProductCacheRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WriteBackService {

    private final ProductCacheRepository productCacheRepository;
    private final ProductMapper mapper;
    private final WriteBackQueueService queueService;
    private final AsyncProductService asyncProductService;

    public ProductResponse updateProductWriteBack(Long id, ProductRequest request) {
        ProductCache cacheEntity = productCacheRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found in cache: " + id));

        log.info("Write-Back: Found product in Cache!");
        mapper.updateCacheEntity(request, cacheEntity);
        productCacheRepository.save(cacheEntity);
        log.info("Write-Back: Cache Updated!");
        asyncProductService.persistAsync(id, request);
        return mapper.toResponseFromCache(cacheEntity);
    }

    public ProductResponse updateProductWriteBackQueue(Long id, ProductRequest request) {
        ProductCache cacheEntity = productCacheRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found in cache: " + id));

        log.info("Write-Back: Found Product in Cache!");
        mapper.updateCacheEntity(request, cacheEntity);
        productCacheRepository.save(cacheEntity);
        log.info("Write-Back: cache Updated!");
        queueService.enqueue(new WriteOperation(id, request));
        return mapper.toResponseFromCache(cacheEntity);
    }
}
