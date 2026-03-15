package com.patterns.cache.controller;

import com.patterns.cache.dto.ProductRequest;
import com.patterns.cache.dto.ProductResponse;
import com.patterns.cache.dto.SyncStatusResponse;
import com.patterns.cache.service.WriteBehindService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class WriteBehindController {

    private final WriteBehindService service;

    @PutMapping("/products/{id}/write-behind")
    public ResponseEntity<ProductResponse> updateProductWriteBehind(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        ProductResponse response = service.update(id, request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @GetMapping("/products/{id}/sync-status")
    public ResponseEntity<SyncStatusResponse> getProductSyncStatus(@PathVariable Long id) {
        service.getProductSyncStatus(id);
        SyncStatusResponse response = new SyncStatusResponse(
                id,
                "SYNCED",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }
}
