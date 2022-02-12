package pers.prover07.crowd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pers.prover.crowd.constant.CrowdAttrNameConstant;
import pers.prover07.crowd.entity.Admin;
import pers.prover07.crowd.service.AdminService;

import javax.servlet.http.HttpSession;

/**
 * @author by Prover07
 * @classname AdminController
 * @description TODO
 * @date 2022/2/11 20:14
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/login")
    public String doLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session
    ) {
        // 调用 service 查找 admin 信息
        Admin admin = adminService.getAdminByUserInfo(username, password);
        // 将查找成功的 admin 登录信息保存到 Session 中
        session.setAttribute(CrowdAttrNameConstant.LOGIN_ADMIN, admin);
        return "redirect:/main";
    }

    @RequestMapping("/logout")
    public String doLogout(HttpSession session) {
        // 注销会话
        session.invalidate();
        return "redirect:/login";
    }

}
