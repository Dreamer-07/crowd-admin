package pers.prover07.crowd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.prover07.crowd.entity.Admin;
import pers.prover07.crowd.service.AdminService;

import java.util.List;

/**
 * @author by Prover07
 * @classname TestController
 * @description TODO
 * @date 2022/2/10 11:32
 */
@Controller
public class TestController {

    @Autowired
    private AdminService adminService;


    @RequestMapping("/test/ssm")
    public String testSSM(ModelMap modelMap) {
        List<Admin> adminList = adminService.getAll();
        modelMap.addAttribute("adminList", adminList);
        return "target";
    }

}
