package pers.prover07.crowd.service;

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
}