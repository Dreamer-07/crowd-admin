package pers.prover07.crowd.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.prover.crowd.util.ResultEntity;
import pers.prover07.crowd.entity.Menu;
import pers.prover07.crowd.service.MenuService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author by Prover07
 * @classname MenuController
 * @description TODO
 * @date 2022/2/15 21:22
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    private Logger logger = LoggerFactory.getLogger(MenuController.class);

    @RequestMapping("/tree")
    public ResultEntity<Menu> getTreeInfo() {
        // 获取所有数据
        List<Menu> menuList = menuService.getAll();
        // 转换成 id:list 的 Map 集合
        Map<Integer,List<Menu>> menuMap = menuList.stream().collect(Collectors.toMap(Menu::getId, Menu::getChildren));
        Menu root = null;
        for (Menu menu : menuList) {
            Integer pid = menu.getPid();
            if (pid == null) {
                root = menu;
                continue;
            }
            menuMap.get(pid).add(menu);
        }
        return ResultEntity.success(root);
    }

    @RequestMapping("/save")
    public ResultEntity<Object> saveTreeNode(Menu menu) {
        menuService.saveTreeNode(menu);
        return ResultEntity.success();
    }

    @RequestMapping("/edit")
    public ResultEntity<Object> editMenuInfo(Menu menu) {
        menuService.editMenuInfo(menu);
        return ResultEntity.success();
    }

    @RequestMapping("/remove/{id}")
    public ResultEntity<String> removeById(@PathVariable("id") Integer id) {
        menuService.removeById(id);
        return ResultEntity.success();
    }

}
