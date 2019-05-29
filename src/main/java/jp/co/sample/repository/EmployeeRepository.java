package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Employee;

/**
 * 従業員情報を管理するリポジトリ.
 * 
 * @author Makoto
 *
 */
@Repository
public class EmployeeRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
		Employee employee = new Employee();
		employee.setId(rs.getInt("id"));
		employee.setName(rs.getString("name"));
		employee.setImage(rs.getString("image"));
		employee.setGender(rs.getString("gender"));
		employee.setHireDate(rs.getDate("hire_date"));
		employee.setMailAddress(rs.getString("mail_address"));
		employee.setZipCode(rs.getString("zip_code"));
		employee.setAddress(rs.getString("address"));
		employee.setTelephone(rs.getString("telephone"));
		employee.setCharacteristics(rs.getString("characteristics"));
		employee.setDependentsCount(rs.getInt("dependents_count"));

		return employee;
	};

	/**
	 * 全従業員リストを取得する.
	 * 
	 * @return employeeList 全従業員が格納されたArrayListを入社日順で返す.
	 */
	public List<Employee> findAll() {
		String sql = "select id, name, image, gender," + " hire_date, mail_address, zip_code, address, "
				+ "telephone, characteristics, dependents_count " + "from employees " + "order by hire_date";
		List<Employee> employeeList = template.query(sql, EMPLOYEE_ROW_MAPPER);

		return employeeList;
	}

	/**
	 * idで主キー検索を行う.
	 * 
	 * @param id 指定されたid
	 * @return 指定されたidに対応するemployeeを返す
	 */
	public Employee load(Integer id) {
		String sql = "select id, name, image, gender," + " hire_date, mail_address, zip_code, address, "
				+ "telephone, characteristics, dependents_count " + "from employees " + "where id = :id";

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		Employee employee = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);

		return employee;
	}

	/**
	 * 従業員情報を更新する
	 * 
	 * @param employee 従業員情報
	 */
	public void update(Employee employee) {
		String sql = "update employees set dependents_count=:dependentsCount where id = :id";

		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);
		template.update(sql, param);
	}
}
