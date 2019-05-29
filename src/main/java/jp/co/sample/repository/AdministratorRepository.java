package jp.co.sample.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Administrator;

/**
 * 管理者情報テーブルを操作するリポジトリ.
 * 
 * @author Makoto
 *
 */
@Repository
public class AdministratorRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Administrator> ADMINISTRATOR_ROW_MAPPER = (rs, i) -> {
		Administrator administrator = new Administrator();
		administrator.setId(rs.getInt("id"));
		administrator.setName(rs.getString("name"));
		administrator.setMailAddress(rs.getString("mail_address"));
		administrator.setPassword(rs.getString("password"));

		return administrator;
	};

	/**
	 * 管理者情報を登録する.
	 * 
	 * @param administrator 登録したい管理者情報
	 */
	public void insert(Administrator administrator) {
		String sql = "insert into administrators(name, mail_address, password) "
				+ "values(:name, :mail_address, :password)";
		
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("name", administrator.getName())
				.addValue("mail_address", administrator.getMailAddress())
				.addValue("password", administrator.getPassword());
		
		template.update(sql, param);
	}
	
	/**
	 * @param mailAddress 入力されたメールアドレス.
	 * @param password 入力されたパスワード.
	 * @return mail_addressとpasswordが一致したadministratorを返す.
	 */
	public Administrator findByMailAddressAndPassword(String mailAddress, String password) {
		String sql = "select id, name, mail_address, password "
				+ "from administrators "
				+ "where mail_address = :mail_address and password = :password";
		
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("mail_address", mailAddress)
				.addValue("password", password);
		
		Administrator administrator;
		try {
			administrator = template.queryForObject(sql, param, ADMINISTRATOR_ROW_MAPPER);
		} catch (IncorrectResultSizeDataAccessException e) {
			administrator = null;
		}
		
		return administrator;
	}
	
}
