package pers.prover07.crowd.service;

import com.github.pagehelper.PageInfo;
import pers.prover07.crowd.entity.Role;

import java.util.List;

/**
 * @author by Prover07
 * @classname RoleService
 * @description TODO
 * @date 2022/2/14 20:40
 */
public interface RoleService {

    public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword);

    public void saveRole(Role role);

    public void editRole(Role role);

    public void removeRoleByIds(List<Integer> ids);

    List<Role> getAssignedRole(Integer adminId);

    List<Role> getUnAssignedRole(Integer adminId);

    void saveRoleAdminRelation(Integer adminId, List<Integer> assignedRoleList);
}
