package com.base;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;

@RestController
public class Controller {
	
	private final RestTemplate restTemplate;

	public Controller(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@GetMapping("/callDemo")
	@Bulkhead(name = "backendA", fallbackMethod = "callDemoFallBack")
	public ResponseEntity<String> callDemo() {
		String url = "http://localhost:8081/demo";
		try {
			ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
			return ResponseEntity.ok(response.getBody());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
		}
	}

	public ResponseEntity<String> callDemoFallBack() {
		return ResponseEntity.ok("Response from FallBack Method");
	}
}
