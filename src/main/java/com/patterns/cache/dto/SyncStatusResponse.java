package com.patterns.cache.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncStatusResponse {

    private Long productId;
    private String status;
    private LocalDateTime cachedAt;
    private LocalDateTime persistedAt;
}
