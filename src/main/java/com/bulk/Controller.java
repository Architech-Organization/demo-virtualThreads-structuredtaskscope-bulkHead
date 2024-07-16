package com.bulk;

import java.util.concurrent.ExecutionException;
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
        this.executorService = Executors.newFixedThreadPool(10); 
    }

	/*
	 * @GetMapping("/demo") public ResponseEntity<String[]> demo() {
	 * 
	 * Future<String> response10Future = executorService.submit(() ->
	 * demoService.fetchDataIn10Sec()); Future<String> response5Future =
	 * executorService.submit(() -> demoService.fetchDataIn5Sec());
	 * 
	 * try { String response10 = response10Future.get(); String response5 =
	 * response5Future.get(); String[] responses = { response10, response5 }; return
	 * ResponseEntity.ok(responses); } catch (InterruptedException |
	 * ExecutionException e) { e.printStackTrace(); return
	 * ResponseEntity.status(500).body(new String[] { "Error occurred: " +
	 * e.getMessage() }); } }
	 */
    
    @GetMapping("/demo")
    public ResponseEntity<String> demo() {

    	 try (var scope = new StructuredTaskScope.ShutdownOnSuccess<String>()) {
    		 Supplier<String> response10Future = scope.fork(() -> demoService.fetchDataIn10Sec());
    		 Supplier<String> response5Future = scope.fork(() -> demoService.fetchDataIn5Sec());

    	     
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
            return ResponseEntity.ok(scope.result());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
        }
    }
}
