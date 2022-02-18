package pers.prover07.crowd.service;

import pers.prover.crowd.util.ResultEntity;
import pers.prover07.crowd.entity.Auth;
import pers.prover07.crowd.entity.Role;

import java.util.List;

public interface AuthService {
    List<Auth> getAllAuth();

    List<Integer> getRoleAssignedByRoleId(Integer roleId);

    void saveRoleAuthRelation(Integer roleId, List<Integer> authIds);
}
