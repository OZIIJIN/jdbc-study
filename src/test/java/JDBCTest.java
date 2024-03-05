import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class JDBCTest {

	@Test
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
			statement.execute();

			// 제일 중요한 자원 해제
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		//then
		//DB 확인
	}

}
