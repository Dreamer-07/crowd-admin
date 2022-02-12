package pers.prover.crowd.exception;

/**
 * @author by Prover07
 * @classname AccessForbiddenException
 * @description 权限不足异常
 * @date 2022/2/12 11:19
 */
public class AccessForbiddenException extends RuntimeException {

    public AccessForbiddenException() {
    }

    public AccessForbiddenException(String message) {
        super(message);
    }
}
