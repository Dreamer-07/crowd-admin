package pers.prover07.crowd.principal;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import pers.prover07.crowd.entity.Admin;

import java.util.Collection;
import java.util.List;

/**
 * @author by Prover07
 * @classname AdminPrincipal
 * @description TODO
 * @date 2022/2/21 12:29
 */
public class AdminPrincipal extends User {

    private final Admin admin;
    private final List<? extends GrantedAuthority> authorities;

    public AdminPrincipal(Admin admin, List<SimpleGrantedAuthority> authorities) {
        super(admin.getLoginAcct(), admin.getUserPswd(), authorities);
        this.admin = admin;
        this.authorities = authorities;
    }

    public Admin getAdmin() {
        return admin;
    }

}
