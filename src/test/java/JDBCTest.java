import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JDBCTest {

	@Test
	@DisplayName("JDBC 테이블 생성 실습")
	void jdbcTest() throws SQLException {
		//given

		// docker run -p 5432:5432 -e POSTGRES_PASSWORD=pass -e POSTGRES_USER=teasun -e POSTGRES_DB=messenger --name postgres_boot -d postgres

		// docker exec -i -t postgres_boot bash
		// su - postgres
		// psql --username yejin --dbname messenger
		// \list (데이터 베이스 조회)
		// \dt (테이블 조회)

		// 각 어플리케이션에서 이 컨테이너에 접속하기 위해서는
		// url 경로로 username과 password로 접속하겠다.
		String url = "jdbc:postgresql://localhost:5432/messenger";
		String username = "yejin";
		String password = "pass";

		//when
		try {
			Connection connection = DriverManager.getConnection(url, username, password);
			String creatSql = "CREATE TABLE ACCOUNT (id SERIAL PRIMARY KEY, username varchar(255), password varchar(255))";
			// 쿼리 실행
			PreparedStatement statement = connection.prepareStatement(creatSql);
			statement.execute(); //execute -> 쿼리를 날림

			// 제일 중요한 자원 해제
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		//then
		//DB 확인
	}
	@Test
	@DisplayName("JDBC 삽입/조회 실습")
	void jdbcInsertSelectTest() throws SQLException {
		// given
		String url = "jdbc:postgresql://localhost:5432/messenger";
		String username = "yejin";
		String password = "pass";

		// when
		//자동으로 close(자원해제) 하는 방법 : try() -> 괄호 안에 connection 넣어주기
		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			System.out.println("Connection created: " + connection);

			String insertSql = "INSERT INTO ACCOUNT (id, username, password) VALUES ((SELECT coalesce(MAX(ID), 0) + 1 FROM ACCOUNT A), 'user1', 'pass1')";
			try (PreparedStatement statement = connection.prepareStatement(insertSql)) {
				statement.execute();
			}

			// then
			String selectSql = "SELECT * FROM ACCOUNT";
			try (PreparedStatement statement = connection.prepareStatement(selectSql)) {
				var rs = statement.executeQuery();
				while (rs.next()) {
					System.out.printf("%d, %s, %s", rs.getInt("id"), rs.getString("username"),
						rs.getString("password"));
				}
			}
		}
	}

}
