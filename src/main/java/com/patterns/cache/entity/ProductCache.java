package com.patterns.cache.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RedisHash(value = "products", timeToLive = 180)
@Getter
@Setter
@AllArgsConstructor
public class ProductCache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    private String category;
    private Integer stock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
