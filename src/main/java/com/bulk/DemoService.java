package com.bulk;

import org.springframework.stereotype.Service;

@Service
public interface DemoService {
	
	public String fetchDataIn15Sec();
	
	public String fetchDataIn10Sec();

	public String fetchDataIn5Sec();
}
