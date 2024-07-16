package com.bulk;

import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService{

	@Override
	public String fetchDataIn10Sec() {
		for(int i=0; i<10; i++) {
			try {
				Thread.currentThread().sleep(1000);
				System.out.println("(fetchDataIn10Sec) Thread sleeping for------" +i+ " seconds");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	
		return "Response from 10 seconds delayed call ";
	}

	@Override
	public String fetchDataIn5Sec() {
		for(int i=0; i<5; i++) {
			try {
				Thread.currentThread().sleep(1000);
				System.out.println("(fetchDataIn5Sec) Thread sleeping for------" +i+ " seconds");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	
		return "Response from 5 seconds delayed call ";
	}
	
	
}
