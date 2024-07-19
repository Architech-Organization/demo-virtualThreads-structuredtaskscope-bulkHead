package com.bulk;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.StructuredTaskScope;
import java.util.function.Supplier;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	private DemoService demoService;
	private ExecutorService executorService;

	public Controller(DemoService demoService) {
		this.demoService = demoService;
		this.executorService = Executors.newFixedThreadPool(3);
	}

//	@GetMapping("/demo")
//	public ResponseEntity<Object[]> demo() {
//
//		Future<ResponseModel> response10Future = executorService.submit(() -> demoService.fetchDataIn10Sec());
//		Future<ResponseModel> response5Future = executorService.submit(() -> demoService.fetchDataIn5Sec());
//
//		try {
//			ResponseModel response10 = response10Future.get();
//			ResponseModel response5 = response5Future.get();
//			Object[] responses = { response10, response5 };
//			return ResponseEntity.ok(responses);
//		} catch (InterruptedException | ExecutionException e) {
//			e.printStackTrace();
//			return ResponseEntity.status(500).body(new String[] { "Error occurred: " + e.getMessage() });
//		}
//	}

	@GetMapping("/demo")
	public ResponseEntity<String> demo() {

		try (var scope = new StructuredTaskScope.ShutdownOnSuccess<ResponseModel>()) {
			Supplier<ResponseModel> response10Future = scope.fork(() -> demoService.fetchDataIn10Sec());
			Supplier<ResponseModel> response5Future = scope.fork(() -> demoService.fetchDataIn5Sec());

			scope.join();

//            String response10=null;
//            try {
//           if(response10Future.get()!=null) {
//        	   response10 = response10Future.get();
//           }
//           else {
//        	   response10 =null;
//           }
//            }
//            catch(IllegalStateException ex) {
//            	String response5 = response5Future.get();
//                String[] responses = { response10, response5 };
//                return ResponseEntity.ok(responses);
//            }
//            String response5 = response5Future.get();
//            String[] responses = { response10, response5 };
			ResponseModel successfulResponse = scope.result();

			return ResponseEntity.ok(successfulResponse.getDelayedResponse());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
		}
	}

	@GetMapping("/demo2")
	public ResponseEntity<String[]> demo2() {
		try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
			Supplier<ResponseModel> response15Supplier = scope.fork(() -> demoService.fetchDataIn15Sec());
			Supplier<ResponseModel> response10Supplier = scope.fork(() -> demoService.fetchDataIn10Sec());
			Supplier<ResponseModel> response5Supplier = scope.fork(() -> demoService.fetchDataIn5Sec());

			scope.join();
			scope.throwIfFailed();

			ResponseModel response15 = response15Supplier.get();
			ResponseModel response10 = response10Supplier.get();
			ResponseModel response5 = response5Supplier.get();
			String[] responses = { response15.getDelayedResponse(), response10.getDelayedResponse(), response5.getDelayedResponse() };
			return ResponseEntity.ok(responses);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(new String[] { "Error occurred: " + e.getMessage() });
		}
	}
}
