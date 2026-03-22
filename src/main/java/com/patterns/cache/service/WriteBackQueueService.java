package com.patterns.cache.service;

import com.patterns.cache.dto.WriteOperation;
import com.patterns.cache.entity.Product;
import com.patterns.cache.exception.ProductNotFoundException;
import com.patterns.cache.mapper.ProductMapper;
import com.patterns.cache.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
@RequiredArgsConstructor
@Slf4j
public class WriteBackQueueService {

    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final BlockingQueue<WriteOperation> queue = new LinkedBlockingQueue<>();
    private volatile boolean running = true;
    private Thread workerThread;

    @PostConstruct
    public void startWorker() {
        workerThread = new Thread(this::processQueue);
        workerThread.setName("write-back-worker");
        workerThread.start();
        log.info("Write-back worker thread started");
    }

    @PreDestroy
    public void stopWorker() {
        running = false;
        workerThread.interrupt();
        log.info("Write-back worker thread stopped");
    }

    public void enqueue(WriteOperation operation) {
        queue.offer(operation);
        log.info("Operation enqueued for product ID: {}", operation.getProductId());
    }

    private void processQueue() {
        while (running) {
            try {
                WriteOperation operation = queue.take();
                persistToDatabase(operation);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.warn("Worker thread interrupted");
            }
        }
    }

    private void persistToDatabase(WriteOperation operation) {
        try {
            Product product = repository.findById(operation.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(operation.getProductId()));
            mapper.updateEntity(operation.getRequest(), product);
            repository.save(product);
            log.info("Product ID {} persisted to database", operation.getProductId());
        } catch (Exception e) {
            log.error("Error persisting product ID {}: {}", operation.getProductId(), e.getMessage());
        }
    }

    public int getQueueSize() {
        return queue.size();
    }
}
