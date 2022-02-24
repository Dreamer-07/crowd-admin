package pers.prover07.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pers.prover.crowd.constant.HttpRespMsgConstant;
import pers.prover.crowd.exception.AdminLoginAcctNoUniqueException;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @Override
    public void saveAdmin(Admin admin) {
        // 对密码进行加密
        String userPswd = admin.getUserPswd();
        String encodedUserPswd = passwordEncoder.encode(userPswd);
        admin.setUserPswd(encodedUserPswd);

        // 保存到数据库
        try {
            adminMapper.insert(admin);
        } catch (Exception e) {
            e.printStackTrace();

            // 如果出现 UNIQUE 索引重复问题就抛出 AdminLoginAcctNoUnique 异常
            if (e instanceof DuplicateKeyException) {
                throw new AdminLoginAcctNoUniqueException(HttpRespMsgConstant.SYSTEM_ERROR_ADMIN_UNIQUE, "admin/add");
            }
        }
    }

    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        // 开启 PageHelper 分页
        PageHelper.startPage(pageNum, pageSize);

        // 调用查询方法
        List<Admin> adminList = adminMapper.selectAdminByKeyword(keyword);

        // 封装成 PageInfo 对象
        return new PageInfo<>(adminList);
    }

    @Override
    public void removeById(Integer id) {
        adminMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Admin getAdminById(Integer id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    @Override
    public void editAdmin(Admin admin) {
        // 根据注解并只更新属性不为 null 的字段值
        adminMapper.updateByPrimaryKeySelective(admin);
    }

    @Override
    public Admin getAdminByLoginAcct(String loginAcct) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(loginAcct);
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        if (CollectionUtils.isEmpty(admins)) {
            throw new LoginFailedException(HttpRespMsgConstant.ADMIN_NOT_EXISTS);
        }
        return admins.get(0);
    }
}
