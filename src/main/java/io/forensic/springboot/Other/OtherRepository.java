package io.forensic.springboot.Other;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import io.forensic.springboot.person.Person;

public interface OtherRepository extends CrudRepository<Person, String>, OtherRepositoryCustom{
	
	@Query(value = "SELECT DISTINCT ak.kit FROM AutosomalKit ak", nativeQuery = true)
	public List<String> findDistinctAutosomalKit();

	@Query(value = "SELECT ak.locus FROM AutosomalKit ak WHERE ak.kit = :kit", nativeQuery = true)
	public List<String> getLocusAutosomalKit(@Param("kit") String kit);
	
	@Query(value = "SELECT DISTINCT ak.kit FROM YKit ak", nativeQuery = true)
	public List<String> findDistinctYKit();
	
	@Query(value = "SELECT ak.locus FROM YKit ak WHERE ak.kit = :kit", nativeQuery = true)
	public List<String> getLocusYKit(@Param("kit") String kit);

	@Query(value = "SELECT DISTINCT ak.kit FROM XKit ak", nativeQuery = true)
	public List<String> findDistinctXKit();
	
	@Query(value = "SELECT ak.locus FROM XKit ak WHERE ak.kit = :kit", nativeQuery = true)
	public List<String> getLocusXKit(@Param("kit") String kit);

	@Query(value = "SELECT DISTINCT locus FROM forenseq;", nativeQuery = true)
	public List<String> findLocusAutosom();

	@Query(value = "SELECT DISTINCT locus FROM forenseqY;", nativeQuery = true)
	public List<String> findLocusY();

	@Query(value = "SELECT DISTINCT locus FROM forenseqX;", nativeQuery = true)
	public List<String> findLocusX();

	@Query(value = "SELECT Allele, count(*) FROM forenseq WHERE _Type = \"Yes\" and locus = :locus GROUP BY Allele ORDER BY Allele;", nativeQuery = true)
	public List<Object[]> findStatsGraphA(@Param("locus") String locus);

	@Query(value = "SELECT Allele, count(*) FROM forenseqY WHERE _Type = \"Yes\" and locus = :locus GROUP BY Allele ORDER BY Allele;", nativeQuery = true)
	public List<Object[]> findStatsGraphY(@Param("locus") String locus);

	@Query(value = "SELECT Allele, count(*) FROM forenseqX WHERE _Type = \"Yes\" and locus = :locus GROUP BY Allele ORDER BY Allele;", nativeQuery = true)
	public List<Object[]> findStatsGraphX(@Param("locus") String locus);

	@Query(value = "select lft.locus, lft.Total, rht.Hetero from (SELECT Locus, COUNT(distinct sample_id) as Total from forenseq WHERE _Type = \"Yes\" GROUP BY Locus) lft LEFT JOIN (SELECT a.Locus, COUNT(*) AS Hetero FROM (SELECT Sample_Year, Sample_ID,Locus, COUNT(*) AS Amount FROM forenseq WHERE _Type=\"Yes\" GROUP BY Locus, Sample_Year, Sample_ID) a WHERE a.Amount = 2 GROUP BY a.Locus) rht ON lft.locus = rht.locus union select lft.locus, lft.Total, rht.Hetero from (SELECT Locus, COUNT(distinct sample_id) as Total from forenseqX WHERE _Type = \"Yes\" GROUP BY Locus) lft LEFT JOIN (SELECT a.Locus, COUNT(*) AS Hetero FROM (SELECT Sample_Year, Sample_ID,Locus, COUNT(*) AS Amount FROM forenseqX WHERE _Type=\"Yes\" GROUP BY Locus, Sample_Year, Sample_ID) a WHERE a.Amount = 2 GROUP BY a.Locus) rht ON lft.locus = rht.locus union select lft.locus, lft.Total, rht.Hetero from (SELECT Locus, COUNT(distinct sample_id) as Total from forenseqY WHERE _Type = \"Yes\" GROUP BY Locus) lft LEFT JOIN (SELECT a.Locus, COUNT(*) AS Hetero FROM (SELECT Sample_Year, Sample_ID,Locus, COUNT(*) AS Amount FROM forenseqY WHERE _Type=\"Yes\" GROUP BY Locus, Sample_Year, Sample_ID) a WHERE a.Amount = 2 GROUP BY a.Locus) rht ON lft.locus = rht.locus;" , nativeQuery = true)
	public List<Object[]> getHetero();
}
