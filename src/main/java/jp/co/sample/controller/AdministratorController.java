package jp.co.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
import jp.co.sample.form.UpdateEmployeeForm;
import jp.co.sample.service.AdministratorService;

/**
 * 管理者情報を操作するコントローラー.
 * 
 * @author Makoto
 *
 */
@Controller
@RequestMapping("/")
public class AdministratorController {

	@Autowired
	AdministratorService administratorService;

	@Autowired
	private HttpSession session;

	/**
	 * InsertAdministratorFormをrequestスコープに自動的に格納する.
	 * 
	 * @return 管理者登録フォーム
	 */
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		return new InsertAdministratorForm();
	}

	/**
	 * LoginFormをrequestスコープに自動的に格納する.
	 * 
	 * @return ログイン情報フォーム
	 */
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}

	/**
	 * 管理者情報登録画面にフォワード.
	 * 
	 * @return 管理者情報登録画面
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert";
	}

	/**
	 * 管理者情報を登録する.
	 * 
	 * @param form フォーム(リクエストパラメータ情報)
	 * @return ログイン画面
	 */
	@RequestMapping("/insert")
	public String insert(InsertAdministratorForm form) {
		Administrator administrator = new Administrator();
		BeanUtils.copyProperties(form, administrator);
		administratorService.insert(administrator);
		return "redirect:/";
	}

	/**
	 * administrator/loginにフォワードする.
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login";
	}

	/**
	 * ログイン処理を行う.
	 * 
	 * @param form   フォーム
	 * @param result エラー情報を格納する
	 * @return パスワードとメールアドレスが一致したら従業員リストにフォワード
	 * @return 一致しない場合はログインページにフォワード
	 */
	@RequestMapping("/login")
	public String login(LoginForm form, BindingResult result) {
		Administrator administrator = administratorService.login(form.getMailAddress(), form.getPassword());
		if (administrator == null) {
			result.rejectValue("mailAddress", null, "メールアドレスまたはパスワードが不正です");
			return "administrator/login";
		} else {
			session.setAttribute("admin_name", administrator.getName());
			return "forward:employee/showList";
		}
	}
	
	/**
	 * セッションを破棄してトップページに遷移する.
	 * 
	 * @param form 
	 * @param model
	 * @return トップページにリダイレクトする.
	 */
	@RequestMapping("/logout")
	public String logout(UpdateEmployeeForm form, Model model) {
		session.invalidate();
		return "redirect:/";
	}
	

}