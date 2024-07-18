package com.bulk;

import org.springframework.stereotype.Service;

@Service
public interface DemoService {
	
	public ResponseModel fetchDataIn15Sec();
	
	public ResponseModel fetchDataIn10Sec();

	public ResponseModel fetchDataIn5Sec();
}
