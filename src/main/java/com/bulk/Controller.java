package com.bulk;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	private DemoService demoService;

	public Controller(DemoService demoService) {
		this.demoService = demoService;
	}

	@GetMapping("/demo")
	public ResponseEntity<String[]> demo() {

		String response10 = demoService.fetchDataIn10Sec();
		String response5 = demoService.fetchDataIn5Sec();
		
		String[] responses = { response10, response5 };

        return ResponseEntity.ok(responses);
	}
}
