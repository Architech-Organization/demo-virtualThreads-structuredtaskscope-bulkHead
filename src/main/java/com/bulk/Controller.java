package com.bulk;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private DemoService demoService;
    private ExecutorService executorService;

    public Controller(DemoService demoService) {
        this.demoService = demoService;
        this.executorService = Executors.newFixedThreadPool(10); 
    }

    @GetMapping("/demo")
    public ResponseEntity<String[]> demo() {

        Future<String> response10Future = executorService.submit(() -> demoService.fetchDataIn10Sec());
        Future<String> response5Future = executorService.submit(() -> demoService.fetchDataIn5Sec());

        try {
            String response10 = response10Future.get();
            String response5 = response5Future.get();
            String[] responses = { response10, response5 };
            return ResponseEntity.ok(responses);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new String[] { "Error occurred: " + e.getMessage() });
        }
    }
}
