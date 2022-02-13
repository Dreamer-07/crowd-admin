package pers.prover07.crowd.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping("/to/edit/{id}")
    public String doEdit(@PathVariable("id") Integer id, ModelMap modelMap) {
        // 根据 id 查询 Admin 信息
        Admin admin = adminService.getAdminById(id);
        // 保存到请求域中
        modelMap.addAttribute("admin", admin);
        return "admin/edit";
    }

    @RequestMapping("/add")
    public String saveAdmin(Admin admin) {
        // 保存用户信息
        adminService.saveAdmin(admin);

        // 返回 admin/page 页面
        return "redirect:/admin/page?pageNum=" + Integer.MAX_VALUE;
    }

    @RequestMapping("/edit")
    public String editAdmin(Admin admin,
                            @RequestParam(value = "keyword", defaultValue = "") String keyword,
                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        adminService.editAdmin(admin);
        return "redirect:/admin/page?pageNum=" + pageNum + "&keyword=" + keyword;
    }

    @GetMapping("/page")
    public String getPageInfo(
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            ModelMap modelMap
    ) {
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);
        modelMap.addAttribute(CrowdAttrNameConstant.PAGE_INFO, pageInfo);
        return "admin/page";
    }

    @RequestMapping("/remove/{id}")
    public String removeById(
            @PathVariable("id") Integer id,
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum
    ) {
        adminService.removeById(id);
        return "redirect:/admin/page?pageNum=" + pageNum + "&keyword=" + keyword;
    }

}
