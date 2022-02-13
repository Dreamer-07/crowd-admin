package pers.prover07.crowd.service;

import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import pers.prover07.crowd.entity.Admin;

import java.util.List;

/**
 * @author by Prover07
 * @classname AdminService
 * @description TODO
 * @date 2022/2/10 11:33
 */
public interface AdminService {

    /**
     * 根据用户信息获取 Admin 用户
     * @param username
     * @param password
     * @return
     */
    Admin getAdminByUserInfo(String username, String password);


    /**
     * 保存 Admin 信息
     * @param admin
     */
    void saveAdmin(Admin admin);

    /**
     * 获取 Admin 列表分页信息
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

    /**
     * 根据id删除 Admin 信息
     * @param id
     */
    void removeById(Integer id);

    /**
     * 根据 id 获取 Admin 信息
     * @param id
     */
    Admin getAdminById(Integer id);

    /**
     * 修改 Admin
     * @param admin
     */
    void editAdmin(Admin admin);
}