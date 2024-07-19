package com.bulk;

import org.springframework.stereotype.Service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DemoServiceImpl implements DemoService {

	@Override
	@Bulkhead(name = "backendA", fallbackMethod = "getDefaultFor10Seconds", type = Bulkhead.Type.SEMAPHORE)
	public ResponseModel fetchDataIn10Sec() {
		for (int i = 0; i < 10; i++) {
			try {
				Thread.currentThread().sleep(1000);
				log.info("(fetchDataIn-10-Sec) Thread sleeping for------" + i + " seconds");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		ResponseModel response = new ResponseModel("Response in 10 Seconds");

		return response;
	}

	public ResponseModel getDefaultFor10Seconds(Throwable ex) {
		log.error("getDefaultFor10Seconds() is busy");
		log.debug("Returning default");
		ResponseModel response = new ResponseModel("Response from FallBack for fetchDataIn10Sec");

		return response;
	}

	@Override
	@Bulkhead(name = "backendB", fallbackMethod = "getDefaultFor5Seconds", type = Bulkhead.Type.SEMAPHORE)
	public ResponseModel fetchDataIn5Sec() {
		for (int i = 0; i < 5; i++) {
			try {
				Thread.currentThread().sleep(1000);
				log.info("(fetchDataIn-5-Sec) Thread sleeping for------" + i + " seconds");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		ResponseModel response = new ResponseModel("Response in 5 Seconds");

		return response;
	}

	public ResponseModel getDefaultFor5Seconds(Throwable ex) {
		log.error("getDefaultFor5Seconds() is busy");
		log.debug("Returning default");
		ResponseModel response = new ResponseModel("Response from FallBack for getDefaultFor5Seconds");

		return response;
	}

	@Override
	@Bulkhead(name = "backendC", fallbackMethod = "getDefaultFor15Seconds", type = Bulkhead.Type.SEMAPHORE)
	public ResponseModel fetchDataIn15Sec() {
		for (int i = 0; i < 15; i++) {
			try {
				Thread.currentThread().sleep(1000);
				log.info("(fetchDataIn-15-Sec) Thread sleeping for------" + i + " seconds");
				if (i == 12) {
					IllegalArgumentException ill = new IllegalArgumentException();
					throw ill;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		ResponseModel response = new ResponseModel("Response in 15 Seconds");

		return response;
	}

	public ResponseModel getDefaultFor15Seconds(Throwable ex) {
		log.error("getDefaultFor15Seconds() is busy");
		log.debug("Returning default");
		ResponseModel response = new ResponseModel("Response from FallBack for getDefaultFor15Seconds");

		return response;
	}

}
