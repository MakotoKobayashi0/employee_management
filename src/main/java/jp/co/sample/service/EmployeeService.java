package jp.co.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.domain.Employee;
import jp.co.sample.repository.EmployeeRepository;

/**
 * 従業員を操作するサービスクラス.
 * 
 * @author Makoto
 *
 */
@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	/**
	 * 従業員を全件検索する.
	 * 
	 * @return 社員のリスト
	 */
	public List<Employee> showList() {
		return employeeRepository.findAll();
	}

	/**
	 * idで主キー検索を行う.
	 * 
	 * @param id ID
	 * @return 社員の詳細情報
	 */
	public Employee showDetail(Integer id) {
		return employeeRepository.load(id);
	}

	/**
	 * 従業員の詳細情報を更新する.
	 * 
	 * @param employee 従業員の詳細情報
	 */
	public void update(Employee employee) {
		employeeRepository.update(employee);
	}
}
