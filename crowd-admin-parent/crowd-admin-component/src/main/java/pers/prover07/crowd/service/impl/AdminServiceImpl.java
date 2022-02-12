package pers.prover07.crowd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.prover.crowd.constant.HttpRespMsgConstant;
import pers.prover.crowd.exception.LoginFailedException;
import pers.prover.crowd.util.CrowdUtil;
import pers.prover07.crowd.dao.AdminMapper;
import pers.prover07.crowd.entity.Admin;
import pers.prover07.crowd.entity.AdminExample;
import pers.prover07.crowd.service.AdminService;

import java.util.List;
import java.util.Objects;

/**
 * @author by Prover07
 * @classname AdminServiceImpl
 * @description TODO
 * @date 2022/2/10 11:33
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin getAdminByUserInfo(String username, String password) {
        // 1.根据用户信息查询用户信息
        // 1) 构建查询条件
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(username);
        // 2) 查询数据库
        List<Admin> adminList = adminMapper.selectByExample(adminExample);

        // 2.避免数据异常
        if (adminList == null || adminList.size() == 0) {
            throw new LoginFailedException(HttpRespMsgConstant.LOGIN_FAILED);
        }
        if (adminList.size() > 1) {
            throw new RuntimeException(HttpRespMsgConstant.SYSTEM_ERROR_ADMIN_UNIQUE);
        }
        Admin admin = adminList.get(0);
        if (admin == null) {
            throw new LoginFailedException(HttpRespMsgConstant.LOGIN_FAILED);
        }
        // 3.判断密码是否相同
        // 1) 对表单提交的密码进行加密
        String encodedPassword = CrowdUtil.md5(password);
        // 2) 判断数据库中的密码和 encodedPassword 密码是否相同
        if (!Objects.equals(admin.getUserPswd(), encodedPassword)) {
            throw new LoginFailedException(HttpRespMsgConstant.LOGIN_FAILED);
        }
        return admin;
    }
}
