package pers.prover.crowd.exception;

/**
 * @author by Prover07
 * @classname AdminLoginAcctNoUnique
 * @description TODO
 * @date 2022/2/13 12:40
 */
public class AdminLoginAcctNoUniqueException extends RuntimeException{

    public AdminLoginAcctNoUniqueException() {
        super();
    }

    public AdminLoginAcctNoUniqueException(String message) {
        super(message);
    }
}
