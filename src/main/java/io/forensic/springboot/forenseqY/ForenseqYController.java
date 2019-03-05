package io.forensic.springboot.forenseqY;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForenseqYController {
	
	@Autowired
	private ForenseqYService forenseqYService;

	@RequestMapping("/forenseqys")
	public List<ForenseqY> getAllForenseq() {
		return forenseqYService.getAllForenseqs();
	}
	
	@RequestMapping("/uploadforenseqyfile")
	@CrossOrigin()
	public String testExcel() {
		System.out.println("Start Y STRs");
		forenseqYService.readExcelData("uploads/temp.xlsx");
		System.out.println("End Y STRs");
		return "Success";
	}
	
//	@RequestMapping("/forenseqy/sample_year={id}&&sample_id={sid}&&locus={locus}&&allele={allele}")
//	public ForenseqY getRazor(@PathVariable String id,@PathVariable String sid,@PathVariable String locus,@PathVariable String allele) {
//		return forenseqYService.getForenseq(new ForenseqYIdentity(id,sid,locus,allele));
//	}
	
	@RequestMapping(method=RequestMethod.POST, value="/forenseqys")
	public void addRazor(@RequestBody ForenseqY forenseqY) {
		forenseqYService.addForenseq(forenseqY);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/forenseqy/{id}")
	public void updateRazor(@RequestBody ForenseqY forenseqY,@PathVariable String id) {
		forenseqYService.updateForenseq(id, forenseqY);
	}
	
//	@RequestMapping(method=RequestMethod.DELETE, value="/forenseqy/sample_year={id}&&sample_id={sid}&&locus={locus}&&allele={allele}")
//	public void deleteRazor(@PathVariable String id,@PathVariable String sid,@PathVariable String locus,@PathVariable String allele) {
//		forenseqYService.deleteForenseq(new ForenseqYIdentity(id,sid,locus,allele));
//	}
}
