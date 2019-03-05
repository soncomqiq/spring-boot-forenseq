package io.forensic.springboot.person;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
	
	@Autowired
	private PersonService personService;

	@RequestMapping("/uploadpersonfile")
	@CrossOrigin()
	public String testExcel() {
		System.out.println("Start PERSON");
		personService.readExcelData("uploads/temp.xlsx");
		System.out.println("End PERSON");
		return "Success";
	}
	
	@RequestMapping("/numberofperson")
	@CrossOrigin()
	public int getNumberOfPerson() {
		return personService.getNumberOfPerson();
	}
	
	@RequestMapping("/persons")
	public List<Person> getAllTopic() {
		return personService.getAllTopics();
	}
	
//	@RequestMapping("/persons/sample_year={id}&&sample_id={sid}")
//	public Person getTopic(@PathVariable String id,@PathVariable String sid) {
//		return personService.getPerson(new PersonIdentity(id,sid));
//	}
	
	@RequestMapping(method=RequestMethod.POST, value="/persons")
	public void addTopic(@RequestBody Person person) {
		personService.addPerson(person);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/persons/{id}")
	public void updateTopic(@RequestBody Person person,@PathVariable String id) {
		personService.updatePerson(id, person);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/persons/sample_year={id}&&sample_id={sid}")
	public void deleteTopic(@PathVariable String id,@PathVariable String sid) {
		personService.deletePerson(new PersonIdentity(id,sid));
	}
}
