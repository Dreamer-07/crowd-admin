package pers.prover07.crowd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.prover.crowd.util.ResultEntity;
import pers.prover07.crowd.entity.Role;
import pers.prover07.crowd.service.AuthService;
import pers.prover07.crowd.service.RoleService;

import java.util.List;

/**
 * @author by Prover07
 * @classname AssignController
 * @description TODO
 * @date 2022/2/18 9:59
 */
@RequestMapping("/assign")
@Controller
public class AssignController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @RequestMapping("/get/role/auth/{roleId}")
    @ResponseBody
    public ResultEntity<List<Integer>> getRoleAssignedAuthByRoleId(@PathVariable("roleId") Integer roleId) {
        List<Integer> authIds = authService.getRoleAssignedByRoleId(roleId);
        return ResultEntity.success(authIds);
    }

    @RequestMapping("/role/to/auth/save")
    @ResponseBody
    public ResultEntity<String> saveRoleAuthRelation(
            @RequestParam("roleId") Integer roleId,
            @RequestParam(value = "authIds", required = false) List<Integer> authIds
    ) {
        authService.saveRoleAuthRelation(roleId, authIds);
        return ResultEntity.success();
    }

    /**
     * 分配角色给管理员
     * @param adminId
     * @param modelMap
     * @return
     */
    @RequestMapping("/role/to/admin/{adminId}/page")
    public String assignRoleToAdminPage(@PathVariable("adminId") Integer adminId, ModelMap modelMap) {

        // 查询已分配的角色
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);

        // 查询未分配的角色
        List<Role> unAssignedRoleList = roleService.getUnAssignedRole(adminId);


        modelMap.addAttribute("assignedRoleList", assignedRoleList);
        modelMap.addAttribute("unAssignedRoleList", unAssignedRoleList);
        modelMap.addAttribute("adminId", adminId);

        return "assign/role-admin";

    }

    @RequestMapping("/role/to/admin/save")
    public String saveRoleAdminRelation(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("keyword") String keyword,
            @RequestParam("adminId") Integer adminId,
            @RequestParam(value = "assignedRoleList", required = false) List<Integer> assignedRoleList
    ) {
        roleService.saveRoleAdminRelation(adminId, assignedRoleList);
        return "redirect:/admin/page?pageNum=" + pageNum + "&keyword=" + keyword;
    }
}
