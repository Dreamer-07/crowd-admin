package pers.prover07.crowd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.prover07.crowd.dao.AdminMapper;
import pers.prover07.crowd.entity.Admin;
import pers.prover07.crowd.entity.AdminExample;
import pers.prover07.crowd.service.AdminService;

import java.util.List;

/**
 * @author by Prover07
 * @classname AdminServiceImpl
 * @description TODO
 * @date 2022/2/10 11:33
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }
}
