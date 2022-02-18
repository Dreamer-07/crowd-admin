package pers.prover07.crowd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.prover.crowd.util.ResultEntity;
import pers.prover07.crowd.entity.Auth;
import pers.prover07.crowd.service.AuthService;

import java.util.List;

/**
 * @author by Prover07
 * @classname AuthController
 * @description TODO
 * @date 2022/2/18 15:06
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @ResponseBody
    @RequestMapping("/getAll")
    public ResultEntity<List<Auth>> getAllAuth() {
        List<Auth> roleList = authService.getAllAuth();
        return ResultEntity.success(roleList);
    }

}
