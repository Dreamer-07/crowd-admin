package pers.prover07.crowd.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pers.prover07.crowd.entity.Admin;
import pers.prover07.crowd.entity.Role;
import pers.prover07.crowd.principal.AdminPrincipal;
import pers.prover07.crowd.service.AdminService;
import pers.prover07.crowd.service.AuthService;
import pers.prover07.crowd.service.RoleService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author by Prover07
 * @classname AdminDetailsService
 * @description TODO
 * @date 2022/2/21 12:45
 */
@Service
public class AdminPrincipalDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String loginAcct) throws UsernameNotFoundException {
        Admin admin = adminService.getAdminByLoginAcct(loginAcct);

        // 根据 admin 查询出相关角色
        List<Role> roles = roleService.getAssignedRole(admin.getId());
        // 通过 stream 获取角色 ID 集合
        List<Integer> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());
        List<String> roleNames = roles.stream().map(role -> "ROLE_" + role.getName()).collect(Collectors.toList());

        // 查询相关的权限名称
        List<String> authNames = authService.getRoleAssignedByRoleIds(roleIds);

        // 通过 Stream 将 Name 转换成 SimpleGrantedAuthority 对象并收集成集合
        List<SimpleGrantedAuthority> authorities =
                Stream.of(roleNames, authNames)
                        .flatMap(names -> names.stream().map(SimpleGrantedAuthority::new))
                        .collect(Collectors.toList());

        return new AdminPrincipal(admin, authorities);
    }
}
