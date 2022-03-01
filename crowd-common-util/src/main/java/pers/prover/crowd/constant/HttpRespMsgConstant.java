package pers.prover.crowd.constant;

/**
 * Http 响应信息
 *
 * @author by Prover07
 * @classname CrowdConstant
 * @description TODO
 * @date 2022/2/11 11:19
 */
public class HttpRespMsgConstant {

    public static final String LOGIN_FAILED = "账号/密码错误";
    public static final String LOGIN_ACCT_ALREADY_IN_USE = "账号已被注册";
    public static final String ACCESS_FORBIDDEN = "请登录后再访问";
    public static final String ACCESS_DENIED = "没有相关权限";
    public static final String STRING_INVALIDATE = "字符串数据不合法";
    public static final String SYSTEM_ERROR_ADMIN_UNIQUE = "系统错误:用户不唯一";
    public static final String SYSTEM_ERROR_REDIS_SAVE = "系统错误:保存到 Redis 失败";

    public static final String ADMIN_NOT_EXISTS = "用户不存在";

    public static final String AUTH_SEND_MESSAGE_ERROR = "auth:发送手机验证码失败";
}
