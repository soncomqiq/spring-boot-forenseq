package io.forensic.springboot.forenseq;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForenseqController {
	
	@Autowired
	private ForenseqService forenseqService;

	@RequestMapping("/forenseqs")
	public List<Forenseq> getAllForenseq() {
		return forenseqService.getAllForenseqs();
	}
	
	@RequestMapping("/uploadforenseqfile")
	@CrossOrigin()
	public String testExcel() {
		System.out.println("Start Autosomal");
		forenseqService.readExcelData("uploads/temp.xlsx");
		System.out.println("End Autosomal");
		return "Success";
	}
	
//	@RequestMapping("/forenseq/sample_year={id}&&sample_id={sid}&&locus={locus}&&allele={allele}")
//	public Forenseq getRazor(@PathVariable String id,@PathVariable String sid,@PathVariable String locus,@PathVariable String allele) {
//		return forenseqService.getForenseq(new ForenseqIdentity(id,sid,locus,allele));
//	}
	
	@RequestMapping(method=RequestMethod.POST, value="/forenseqs")
	public void addRazor(@RequestBody Forenseq forenseq) {
		forenseqService.addForenseq(forenseq);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/forenseq/{id}")
	public void updateRazor(@RequestBody Forenseq forenseq,@PathVariable String id) {
		forenseqService.updateForenseq(id, forenseq);
	}
	
//	@RequestMapping(method=RequestMethod.DELETE, value="/forenseq/sample_year={id}&&sample_id={sid}&&locus={locus}&&allele={allele}")
//	public void deleteRazor(@PathVariable String id,@PathVariable String sid,@PathVariable String locus,@PathVariable String allele) {
//		forenseqService.deleteForenseq(new ForenseqIdentity(id,sid,locus,allele));
//	}
}
