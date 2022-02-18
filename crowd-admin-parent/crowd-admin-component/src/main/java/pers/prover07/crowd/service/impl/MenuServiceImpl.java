package pers.prover07.crowd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.prover07.crowd.dao.MenuMapper;
import pers.prover07.crowd.entity.Menu;
import pers.prover07.crowd.entity.MenuExample;
import pers.prover07.crowd.service.MenuService;

import java.util.List;

/**
 * @author by Prover07
 * @classname MenuServiceImpl
 * @description TODO
 * @date 2022/2/15 21:21
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getAll() {
        return menuMapper.selectByExample(null);
    }

    @Override
    public void saveTreeNode(Menu menu) {
        menuMapper.insert(menu);
    }

    @Override
    public void editMenuInfo(Menu menu) {
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public void removeById(Integer id) {
        menuMapper.deleteByPrimaryKey(id);
    }
}
