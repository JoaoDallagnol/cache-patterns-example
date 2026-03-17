package com.patterns.cache.service;

import com.patterns.cache.dto.ProductResponse;
import com.patterns.cache.entity.Product;
import com.patterns.cache.entity.ProductCache;
import com.patterns.cache.exception.ProductNotFoundException;
import com.patterns.cache.mapper.ProductMapper;
import com.patterns.cache.repository.ProductCacheRepository;
import com.patterns.cache.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CacheAsideProductService {

    private final ProductRepository repository;
    private final ProductCacheRepository cacheRepository;
    private final ProductMapper mapper;

    public ProductResponse getById(Long id) {
        Optional<ProductCache> cached = cacheRepository.findById(id);
        if (cached.isPresent()) {
            return mapper.toResponseFromCache(cached.get());
        }

        Product product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        cacheRepository.save(mapper.toCache(product));
        return mapper.toResponse(product);
    }

    public List<ProductResponse> getAll() {
        return null;
    }
}
