package pers.prover.crowd.util;

import lombok.Data;

/**
 * 同一返回结果集
 *
 * @author by Prover07
 * @classname ResultEntityUtil
 * @description TODO
 * @date 2022/2/11 9:57
 */
@Data
public class ResultEntity<T> {

    private boolean success;
    private String message;
    private T data;

    public static <Type> ResultEntity<Type> success() {
        return ResultEntity.get(true, null, null);
    }

    public static <Type> ResultEntity<Type> success(Type data) {
        return ResultEntity.get(true, data, null);
    }

    public static <Type> ResultEntity<Type> success(Type data, String message) {
        return ResultEntity.get(true, data, message);
    }

    public static <Type> ResultEntity<Type> fail() {
        return ResultEntity.get(false, null, null);
    }

    public static <Type> ResultEntity<Type> fail(Type data) {
        return ResultEntity.get(false, data, null);
    }

    public static ResultEntity fail(String message) {
        return ResultEntity.get(false, null, message);
    }

    public static <Type> ResultEntity<Type> fail(Type data, String message) {
        return ResultEntity.get(false, data, message);
    }


    public static <Type> ResultEntity<Type> get(boolean success, Type data, String message) {
        ResultEntity<Type> resultEntity = new ResultEntity<>();
        resultEntity.success = success;
        resultEntity.data = data;
        resultEntity.message = message;
        return resultEntity;
    }

}
