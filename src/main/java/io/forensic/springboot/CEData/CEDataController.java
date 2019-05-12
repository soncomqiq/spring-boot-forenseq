package io.forensic.springboot.CEData;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.forensic.springboot.forenseqX.ForenseqX;

@RestController
@RequestMapping("/api/resources/cedata")
public class CEDataController {
	
	@Autowired
	private CEDataService cEDataService;

	@RequestMapping("/cedata")
	public List<CEData> getAllTopic() {
		return cEDataService.getAllTopics();
	}
	
	@RequestMapping("/uploadcedatafile")
	@CrossOrigin()
	public String testExcel() {
		System.out.println("Start CE_data");
		cEDataService.readExcelData("uploads/temp.xlsx");
		System.out.println("End CE_Data");
		return "Success";
	}
	
	@RequestMapping("/cedata/{id}&&{sid}&&{locus}&&{genotype}&&{from}")
	public Optional<CEData> getTopic(@PathVariable String id,@PathVariable String sid,@PathVariable String locus,@PathVariable String genotype,@PathVariable String from) {
		return cEDataService.getPerson(new CEDataIdentity(id,sid, locus, genotype, from));
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/cedata")
	public void addTopic(@RequestBody CEData cEData) {
		cEDataService.addPerson(cEData);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/cedata/{id}")
	public void updateTopic(@RequestBody CEData cEData,@PathVariable String id) {
		cEDataService.updatePerson(id, cEData);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/cedata/{id}&&{sid}&&{locus}&&{genotype}&&{from}")
	public void deleteTopic(@PathVariable String id,@PathVariable String sid,@PathVariable String locus,@PathVariable String genotype,@PathVariable String from) {
		cEDataService.deletePerson(new CEDataIdentity(id,sid, locus, genotype, from));
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getByID")
	public List<CEData> getforenseq(@RequestParam(value = "sampleID") String sid,
			@RequestParam(value = "sampleYear") String sy) {
		return cEDataService.getForenseqById(sid, sy);
	}
}
