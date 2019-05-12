package io.forensic.springboot.forenseq;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ForenseqRepository extends CrudRepository<Forenseq, ForenseqIdentity> {

	@Query(value = "select * from forenseq where sample_id = :sid and sample_year = :sy ;", nativeQuery = true)
	List<Object[]> findAllByID(@Param("sid") String sid, @Param("sy") String sy);
}
