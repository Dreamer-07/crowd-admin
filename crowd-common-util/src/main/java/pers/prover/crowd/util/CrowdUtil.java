package pers.prover.crowd.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import org.apache.http.HttpResponse;
import pers.prover.crowd.constant.HttpRespMsgConstant;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 存放项目一些常用方法的工具类
 *
 * @author by Prover07
 * @classname CrowdUtil
 * @description TODO
 * @date 2022/2/11 10:52
 */
public class CrowdUtil {

    /**
     * 对 source 字符串数据进行 md5 加密后返回
     *
     * @param source
     * @return
     */
    public static String md5(String source) {
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
     *
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


    public static ResultEntity sendPhoneCode(
            String host, String path, String method, String appcode,
            String phone, String phoneCode, String templateId
    ) {
        //String host = "https://dfsns.market.alicloudapi.com";
        //String path = "/data/send_sms";
        //String method = "POST";
        //String appcode = "你自己的AppCode";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("content", "code:" + phoneCode);
        bodys.put("phone_number", phone);
        bodys.put("template_id", templateId);


        try {
            HttpResponse response = HttpUtil.doPost(host, path, method, headers, querys, bodys);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                return ResultEntity.success();
            }
            return ResultEntity.fail(response.getStatusLine().getReasonPhrase());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.fail(e.getMessage());
        }
    }

    /**
     * 生成 {@param count} 位数的验证码
     * 原理：
     * - (Math.random()*9 + 1): 生成 1~10 之间的 double
     * - Math.pow(10,count-1): 获取 10 的 @{@param count-1} 次方
     * - (int) 转换为整数
     *
     * @param count
     * @return
     */
    public static String generatedCode(int count) {
        return String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, count - 1)));
    }

    /**
     * 生成文件到 OSS 服务器
     *
     * @param endpoint        OSS Endpoint 节点
     * @param bucketName
     * @param accessKeyId
     * @param accessKeySecret
     * @param inputStream     文件输入流
     * @param fileName        文件名
     * @return
     */
    public static boolean uploadFileToOSS(
            String endpoint,
            String bucketName,
            String accessKeyId,
            String accessKeySecret,
            InputStream inputStream,
            String fileName
    ) {
        // 创建 Oss 客户端连接
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 生成上传文件目录
        String folderName = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        // 拼接文件路径
        String filePathUrl = folderName + "/" + fileName;

        // 通过 OSS Client 上传文件并得到响应结果数据
        try {
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, filePathUrl, inputStream);

            // 如果上传成功 -> getResponse != null
            return putObjectResult.getResponse() != null;
        } catch (Exception e) {
            return false;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
