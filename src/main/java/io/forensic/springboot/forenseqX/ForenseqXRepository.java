package io.forensic.springboot.forenseqX;

import org.springframework.data.repository.CrudRepository;

public interface ForenseqXRepository extends CrudRepository<ForenseqX, ForenseqXIdentity> {
	
//	List<ForenseqX> findByIdSampleYear(String sampleYear);
//	List<ForenseqX> findByIdSampleId(String sampleYear);
}
