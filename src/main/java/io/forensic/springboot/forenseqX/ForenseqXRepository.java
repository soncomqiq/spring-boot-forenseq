package io.forensic.springboot.forenseqX;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ForenseqXRepository extends CrudRepository<ForenseqX, ForenseqXIdentity> {
	
	@Query(value = "select * from forenseqx where sample_id = :sid and sample_year = :sy ;", nativeQuery = true)
	List<Object[]> findAllByID(@Param("sid") String sid, @Param("sy") String sy);
}
