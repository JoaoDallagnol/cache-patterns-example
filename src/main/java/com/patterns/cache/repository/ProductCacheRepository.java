package com.patterns.cache.repository;

import com.patterns.cache.entity.ProductCache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCacheRepository extends CrudRepository<ProductCache, Long> {
}
