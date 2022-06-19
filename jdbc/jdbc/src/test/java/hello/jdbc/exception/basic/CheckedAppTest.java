package hello.jdbc.exception.basic;

import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CheckedAppTest {
    @Test
    void test() {
        Controller controller = new Controller();


        assertThatThrownBy(() -> controller.request())
                .isInstanceOf(SQLException.class);
    }

    class Controller {
        Service service = new Service();

        public void request() throws SQLException, ConnectException {
            service.logic();
        }
    }

    class Service {
        NetworkClient networkClient = new NetworkClient();
        Repository repository = new Repository();

        public void logic() throws SQLException, ConnectException {
            repository.call();
            networkClient.call();
        }
    }

    class NetworkClient {

        public void call() throws ConnectException {
            throw new ConnectException("연결 실패");
        }
    }

    class Repository {

        public void call() throws SQLException {
            throw new SQLException("ex");
        }
    }
}
