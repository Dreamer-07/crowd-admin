package pers.prover.crowd.exception;

/**
 * 登录失败异常
 *
 * @author by Prover07
 * @classname LoginFailedException
 * @description TODO
 * @date 2022/2/11 19:59
 */
public class LoginFailedException extends RuntimeException {

    public LoginFailedException() {
    }

    public LoginFailedException(String message) {
        super(message);
    }
}
