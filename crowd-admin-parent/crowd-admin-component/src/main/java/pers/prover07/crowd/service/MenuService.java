package pers.prover07.crowd.service;

import pers.prover07.crowd.entity.Menu;

import java.util.List;

public interface MenuService {

    List<Menu> getAll();

    void saveTreeNode(Menu menu);

    void editMenuInfo(Menu menu);

    void removeById(Integer id);
}
