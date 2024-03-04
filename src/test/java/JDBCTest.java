import org.junit.jupiter.api.Test;

public class JDBCTest {

	@Test
	void jdbcTest() {
		// docker run -p 5432:5432 -e POSTGRES_PASSWORD=pass -e POSTGRES_USER=teasun -e POSTGRES_DB=messenger --name postgres_boot -d postgres

		// docker exec -i -t postgres_boot bash
		// su - postgres
		// psql --username teasun --dbname messenger
		// \list (데이터 베이스 조회)
		// \dt (테이블 조회)

		// IntelliJ Database 에서도 조회
	}

}
