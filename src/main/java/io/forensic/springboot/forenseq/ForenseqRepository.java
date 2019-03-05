package io.forensic.springboot.forenseq;

import org.springframework.data.repository.CrudRepository;

public interface ForenseqRepository extends CrudRepository<Forenseq, ForenseqIdentity> {
	
//	List<ForenseqX> findByIdSampleYear(String sampleYear);
//	List<ForenseqX> findByIdSampleId(String sampleYear);
}
