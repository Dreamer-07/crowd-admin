package pers.prover07.crowd.dao;

import org.apache.ibatis.annotations.Param;
import pers.prover.crowd.util.ResultEntity;
import pers.prover07.crowd.entity.Auth;
import pers.prover07.crowd.entity.AuthExample;

import java.util.List;

public interface AuthMapper {
    long countByExample(AuthExample example);

    int deleteByExample(AuthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Auth record);

    int insertSelective(Auth record);

    List<Auth> selectByExample(AuthExample example);

    Auth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByExample(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByPrimaryKeySelective(Auth record);

    int updateByPrimaryKey(Auth record);

    List<Integer> selectAssignedByRoleId(Integer roleId);

    List<String> selectAssignedAuthNameByRoleIds(@Param("roleIds") List<Integer> roleIds);

    void deleteRoleRelation(Integer roleId);

    void saveRoleAuthRelation(@Param("roleId") Integer roleId,@Param("authIds") List<Integer> authIds);
}