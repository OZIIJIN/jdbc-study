package jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import jdbc.vo.AccountVO;

public class AccountDAO {

	//JDBC 관련된 변수 관리
	private Connection conn = null;

	private PreparedStatement stat = null;

	private ResultSet rs = null;

	private final String url = "jdbc:postgresql://localhost:5432/messenger";
	private final String username = "yejin";
	private final String password = "pass";

	//SQL 쿼리
	private final String ACCOUNT_INSERT = "INSERT INTO account(ID, USERNAME, PASSWORD) "
		+ "VALUES((SELECT coalesce(MAX(ID), 0) + 1 FROM ACCOUNT A), ?, ?)";

	private final String ACCOUNT_SELECT = "SELECT * FROM account WHERE ID = ?";

	//CRUD 기능 메서드
	public Integer insertAccount(AccountVO vo) {
		var id = -1;
		try {
			String[] resultId = {"id"};
			conn = DriverManager.getConnection(url, username, password);
			stat = conn.prepareStatement(ACCOUNT_INSERT, resultId);
			stat.setString(1, vo.getUsername());
			stat.setString(2, vo.getPassword());
			stat.executeUpdate();

			try (ResultSet rs = stat.getGeneratedKeys()) {
				if (rs.next()) {
					id = rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return id;

	}

	public AccountVO selectAccount(Integer id) {

		AccountVO vo = null;

		try {
			conn = DriverManager.getConnection(url, username, password);
			stat = conn.prepareStatement(ACCOUNT_SELECT);
			stat.setInt(1, id);
			var rs = stat.executeQuery();

			if (rs.next()) {
				vo = new AccountVO();
				vo.setId(rs.getInt("ID"));
				vo.setUsername(rs.getString("USERNAME"));
				vo.setPassword(rs.getString("PASSWORD"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return vo;
	}
}
