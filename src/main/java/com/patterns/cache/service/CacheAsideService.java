package com.patterns.cache.service;

import com.patterns.cache.dto.ProductResponse;
import com.patterns.cache.entity.Product;
import com.patterns.cache.entity.ProductCache;
import com.patterns.cache.exception.ProductNotFoundException;
import com.patterns.cache.mapper.ProductMapper;
import com.patterns.cache.repository.ProductCacheRepository;
import com.patterns.cache.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheAsideService {

    private final ProductRepository repository;
    private final ProductCacheRepository cacheRepository;
    private final ProductMapper mapper;

    public ProductResponse getProductCacheAside(Long id) {
        Optional<ProductCache> cached = cacheRepository.findById(id);
        if (cached.isPresent()) {
            log.info("Cache-Aside: Found Product in Cache!");
            return mapper.toResponseFromCache(cached.get());
        }

        log.info("Cache-Aside: Did not found product in cache");
        Product product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        cacheRepository.save(mapper.toCache(product));
        log.info("Cache-Aside: Returning product from the database!");
        return mapper.toResponse(product);
    }

    public List<ProductResponse> getAllProductsCacheAside() {
        return null;
    }
}
