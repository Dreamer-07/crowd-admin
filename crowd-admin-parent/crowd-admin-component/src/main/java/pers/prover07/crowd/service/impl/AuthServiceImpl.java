package pers.prover07.crowd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.prover.crowd.util.ResultEntity;
import pers.prover07.crowd.dao.AuthMapper;
import pers.prover07.crowd.entity.Auth;
import pers.prover07.crowd.entity.Role;
import pers.prover07.crowd.service.AuthService;

import java.util.List;

/**
 * @author by Prover07
 * @classname AuthSericeImpl
 * @description TODO
 * @date 2022/2/18 15:06
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Override
    public List<Auth> getAllAuth() {
        return authMapper.selectByExample(null);
    }

    @Override
    public List<Integer> getRoleAssignedByRoleId(Integer roleId) {
        return authMapper.selectAssignedByRoleId(roleId);
    }

    @Override
    public void saveRoleAuthRelation(Integer roleId, List<Integer> authIds) {
        authMapper.deleteRoleRelation(roleId);

        if (authIds != null && authIds.size() > 0) {
            authMapper.saveRoleAuthRelation(roleId, authIds);
        }
    }
}
