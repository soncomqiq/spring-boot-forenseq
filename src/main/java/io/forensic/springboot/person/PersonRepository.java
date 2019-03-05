package io.forensic.springboot.person;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, PersonIdentity> {

	@Query(value = "SELECT count(*) FROM person", nativeQuery = true)
	public int getNumberOfPerson();
//	
//	List<AutosomalKit> findByIdSampleYear(String sampleYear);
//	List<AutosomalKit> findByIdSampleId(String sampleYear);
}
