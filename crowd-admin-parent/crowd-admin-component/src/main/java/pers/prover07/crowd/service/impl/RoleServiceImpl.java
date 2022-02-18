package pers.prover07.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.prover07.crowd.dao.RoleMapper;
import pers.prover07.crowd.entity.Role;
import pers.prover07.crowd.entity.RoleExample;
import pers.prover07.crowd.service.RoleService;

import java.util.List;

/**
 * @author by Prover07
 * @classname RoleServiceImpl
 * @description TODO
 * @date 2022/2/14 20:40
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {
        //开启分页
        PageHelper.startPage(pageNum, pageSize);

        // 查询数据
        List<Role> roleList = roleMapper.selectRoleByKeyword(keyword);

        return new PageInfo<>(roleList);
    }

    @Override
    public void saveRole(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public void editRole(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public void removeRoleByIds(List<Integer> ids) {
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andIdIn(ids);

        roleMapper.deleteByExample(roleExample);
    }

    @Override
    public List<Role> getAssignedRole(Integer adminId) {
        return roleMapper.selectAssignedRole(adminId);
    }

    @Override
    public List<Role> getUnAssignedRole(Integer adminId) {
        return roleMapper.selectUnAssignedRole(adminId);
    }

    @Override
    public void saveRoleAdminRelation(Integer adminId, List<Integer> assignedRoleList) {
        // 删除原有关系
        roleMapper.deleteAdminRelation(adminId);
        // 添加新的关系
        if (assignedRoleList != null && assignedRoleList.size() > 0) {
            roleMapper.insertRoleAdminRelation(adminId, assignedRoleList);
        }
    }
}
