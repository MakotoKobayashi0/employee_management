package jp.co.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.repository.AdministratorRepository;
import jp.co.sample.repository.EmployeeRepository;

@Controller
@RequestMapping("/employee-management")
public class SampleController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private AdministratorRepository administoratorRepository;
	
	@RequestMapping("")
	public String index() {
		Administrator administrator = new Administrator();
		administrator.setName("Makoto Kobayashi");
		administrator.setMailAddress("hoge@gmail.com");
		administrator.setPassword("makoto");
		//administoratorRepository.insert(administrator);

		//System.out.println(administoratorRepository.findByMailAddressAndPassword("hoge@gmail.com", "makoto"));
		employeeRepository.findAll().forEach(System.out::println);
		System.out.println(employeeRepository.load(2));
		return "administrator/login";
	}

}
