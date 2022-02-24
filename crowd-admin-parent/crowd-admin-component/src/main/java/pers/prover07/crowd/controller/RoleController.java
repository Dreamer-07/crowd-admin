package pers.prover07.crowd.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.prover.crowd.util.ResultEntity;
import pers.prover07.crowd.entity.Role;
import pers.prover07.crowd.service.RoleService;

import java.util.List;

/**
 * @author by Prover07
 * @classname RoleController
 * @description TODO
 * @date 2022/2/14 20:52
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PreAuthorize("hasRole('部长')")
    @RequestMapping("/page")
    public ResultEntity<PageInfo<Role>> getPageInfo(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "keyword", defaultValue = "") String keyword
    ) {
        return ResultEntity.success(roleService.getPageInfo(pageNum, pageSize, keyword));
    }

    @RequestMapping("/save")
    public ResultEntity<Object> saveRole(@RequestParam("roleName") String name) {
        roleService.saveRole(new Role(null, name));
        return ResultEntity.success();
    }

    @RequestMapping("/edit")
    public ResultEntity<Object> editRole(Role role) {
        roleService.editRole(role);
        return ResultEntity.success();
    }

    @RequestMapping("/remove")
    public ResultEntity<Object> removeRoleByIds(@RequestBody List<Integer> ids) {
        roleService.removeRoleByIds(ids);
        return ResultEntity.success();
    }

}
