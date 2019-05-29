package jp.co.sample.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Employee;
import jp.co.sample.form.UpdateEmployeeForm;
import jp.co.sample.service.EmployeeService;

/**
 * @author Makoto
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	/**
	 * UpdateEmployeeFormを自動的にrequestスコープに格納する.
	 * 
	 * @return 従業員更新フォーム
	 */
	@ModelAttribute
	public UpdateEmployeeForm SetupUpdateEmployeeForm() {
		return new UpdateEmployeeForm();
	}

	/**
	 * 従業員リストページにフォワード.
	 * 
	 * @param model 従業員一覧表示のためのmodel
	 * @return 従業員一覧画面
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		model.addAttribute("employeeList", employeeService.showList());
		return "employee/list";
	}

	/**
	 * 従業員詳細画面にフォワード.
	 * 
	 * @param id    従業員id
	 * @param model 従業員詳細ページに渡す従業員の詳細情報を格納
	 * @return 従業員詳細画面
	 */
	@RequestMapping("/showDetail")
	public String showDetail(String id, Model model) {
		model.addAttribute("employee", employeeService.showDetail(Integer.parseInt(id)));
		return "employee/detail";
	}

	/**
	 * 扶養人数を更新する.
	 * 
	 * @param form  idと扶養人数
	 * @param model モデル
	 * @return 従業員一覧にリダイレクトする.
	 */
	@RequestMapping("/update")
	public String update(UpdateEmployeeForm form) {
		Employee employee = employeeService.showDetail(Integer.parseInt(form.getId()));
		employee.setDependentsCount(Integer.parseInt(form.getDependentsCount()));
		employeeService.update(employee);
		return "redirect:/employee/showList";
	}
}
