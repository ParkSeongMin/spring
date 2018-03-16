package org.springframework.samples.mvc.repository;

import org.springframework.samples.mvc.domain.Sample;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleRepository extends JpaRepository<Sample, Long> {
	
	Sample findByName(@Param("name") String name); 

}
