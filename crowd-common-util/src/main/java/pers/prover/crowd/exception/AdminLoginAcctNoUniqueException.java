package pers.prover.crowd.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author by Prover07
 * @classname AdminLoginAcctNoUnique
 * @description TODO
 * @date 2022/2/13 12:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminLoginAcctNoUniqueException extends RuntimeException{

    /**
     * 错误页面的地址
     */
    private String errorPageUrl;

    public AdminLoginAcctNoUniqueException(String errorPageUrl) {
        super();
        this.errorPageUrl = errorPageUrl;
    }

    public AdminLoginAcctNoUniqueException(String message, String errorPageUrl) {
        super(message);
        this.errorPageUrl = errorPageUrl;
    }
}
