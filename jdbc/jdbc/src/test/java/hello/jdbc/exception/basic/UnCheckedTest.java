package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
public class UnCheckedTest {

    @Test
    void unChecked_catch() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void unChecked_Throws() {
        Service service = new Service();

        assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf(MyUncheckedException.class);
    }

    /**
     * RuntimeException을 상속받은 예외는 언체크 예외가 된다.
     */
    class MyUncheckedException extends RuntimeException {
        public MyUncheckedException(String message) {
            super(message);
        }
    }

    /**
     * UnChecked Exceptions은
     * 예외를 잡지 않으면 자동으로 밖으로 던진다.
     */
    class Service {
        Repository repository = new Repository();

        public void callCatch() {

            try {
                repository.call();
            } catch (MyUncheckedException e) {
                log.info("unCheckedException ={}", e.getMessage(), e);
            }
        }

        /**
         * 예외를 잡지 않아도, 자연스럽게 상위로 넘어감
         * throws 예외 선언 하지 않아도 된다.
         */
        public void callThrow() {
            repository.call();
        }

    }

    class Repository {
        public void call() {
            throw new MyUncheckedException("ex");
        }
    }
}
