package io.forensic.springboot.forenseqY;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ForenseqYRepository extends CrudRepository<ForenseqY, ForenseqYIdentity> {
	
	@Query(value = "select * from forenseqy where sample_id = :sid and sample_year = :sy ;", nativeQuery = true)
	List<Object[]> findAllByID(@Param("sid") String sid, @Param("sy") String sy);
}
