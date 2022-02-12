package pers.prover.crowd.util;

import com.sun.org.apache.bcel.internal.generic.NEW;
import pers.prover.crowd.constant.HttpRespMsgConstant;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 存放项目一些常用方法的工具类
 * @author by Prover07
 * @classname CrowdUtil
 * @description TODO
 * @date 2022/2/11 10:52
 */
public class CrowdUtil {

    /**
     * 对 source 字符串数据进行 md5 加密后返回
     * @param source
     * @return
     */
    public static String md5(String source){
        // 提高代码健壮性
        if (source == null || source.trim().length() == 0) {
            throw new RuntimeException(HttpRespMsgConstant.STRING_INVALIDATE);
        }
        // 定义加密方式
        String algorithm = "md5";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            // 执行加密
            byte[] output = messageDigest.digest(source.getBytes());
            // 创建 BigInteger 对象
            int signum = 1;
            BigInteger bigInteger = new BigInteger(signum, output);
            // 按照 16 进制将 bigInteger 值转换为字符串
            int radix = 16;
            return bigInteger.toString(radix);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断请求是否为 Ajax 请求(true - 是; false - 不是)
     * @param request
     * @return
     */
    public static boolean judgeRequestType(HttpServletRequest request) {
        String acceptHeader = request.getHeader("Accept");
        String xRequestedWithHeader = request.getHeader("X-Requested-With");
        return (acceptHeader != null && acceptHeader.contains("application/json"))
                ||
                ("XMLHttpRequest".equals(xRequestedWithHeader));
    }

}
