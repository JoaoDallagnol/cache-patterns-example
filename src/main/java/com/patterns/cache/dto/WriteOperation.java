package com.patterns.cache.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WriteOperation {
    private Long productId;
    private ProductRequest request;
}
