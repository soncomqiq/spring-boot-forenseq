package io.forensic.springboot.iSNPs;

import org.springframework.data.repository.CrudRepository;

public interface iSNPsRepository extends CrudRepository<iSNPs, iSNPsIdentity> {
	
//	List<ForenseqX> findByIdSampleYear(String sampleYear);
//	List<ForenseqX> findByIdSampleId(String sampleYear);
}
