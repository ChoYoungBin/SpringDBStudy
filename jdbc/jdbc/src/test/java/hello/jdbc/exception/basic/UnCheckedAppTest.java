package hello.jdbc.exception.basic;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UnCheckedAppTest {
    @Test
    void unchecked() {
        Controller controller = new Controller();


        assertThatThrownBy(() -> controller.request())
                .isInstanceOf(RuntimeSQLException.class);
    }

    class Controller {
        Service service = new Service();

        public void request() {
            service.logic();
        }
    }

    class Service {
        NetworkClient networkClient = new NetworkClient();
        Repository repository = new Repository();

        public void logic() {
            repository.call();
            networkClient.call();
        }
    }

    class NetworkClient {

        public void call() {
            throw new RuntimeConnectionException("연결 실패");
        }
    }

    class Repository {
        public void call() {
            try {
                runSQL();
            } catch (SQLException e) {
                throw new RuntimeSQLException(e);
            }
        }

        public void runSQL() throws SQLException {
            throw new SQLException();
        }
    }

    class RuntimeConnectionException extends RuntimeException {

        public RuntimeConnectionException(String message) {
            super(message);
        }
    }

    class RuntimeSQLException extends RuntimeException {
        public RuntimeSQLException(Throwable cause) {
            super(cause);
        }
    }
}
