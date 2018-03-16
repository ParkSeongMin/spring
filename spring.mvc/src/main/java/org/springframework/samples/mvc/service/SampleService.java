package org.springframework.samples.mvc.service;

import java.util.List;

import org.springframework.samples.mvc.domain.Sample;

public interface SampleService {
	
	Sample findById(Long id);

	Sample save(Sample sample);
	
	List<Sample> findAll();
	
	void clearCache();

	void deleteAll();
	
}
