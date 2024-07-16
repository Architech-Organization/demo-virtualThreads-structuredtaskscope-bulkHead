package com.bulk;

import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService{

	@Override
	public String fetchDataIn10Sec() {
		for(int i=0; i<10; i++) {
			try {
				Thread.currentThread().sleep(1000);
				System.out.println("(fetchDataIn-10-Sec) Thread sleeping for------" +i+ " seconds");
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
				System.out.println("(fetchDataIn-5-Sec) Thread sleeping for------" +i+ " seconds");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	
		return "Response from 5 seconds delayed call ";
	}

	@Override
	public String fetchDataIn15Sec() {
		for(int i=0; i<15; i++) {
			try {
				Thread.currentThread().sleep(1000);
				System.out.println("(fetchDataIn-15-Sec) Thread sleeping for------" +i+ " seconds");
				if (i==12) {
				    IllegalArgumentException ill= new IllegalArgumentException();
				    throw ill;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	
		return "Response from 15 seconds delayed call ";
	}
	
	
}
