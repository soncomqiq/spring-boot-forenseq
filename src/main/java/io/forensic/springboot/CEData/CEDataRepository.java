package io.forensic.springboot.CEData;

import org.springframework.data.repository.CrudRepository;

public interface CEDataRepository extends CrudRepository<CEData, CEDataIdentity> {
//	
//	List<AutosomalKit> findByIdSampleYear(String sampleYear);
//	List<AutosomalKit> findByIdSampleId(String sampleYear);
}
