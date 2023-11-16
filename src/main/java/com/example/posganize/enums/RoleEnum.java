package com.example.posganize.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.posganize.enums.PermissionEnum.*;


@RequiredArgsConstructor
public enum RoleEnum {

    USER(
            Set.of(
                    USER_READ,
                    USER_CREATE,
                    USER_UPDATE,
                    USER_DELETE
            )
    ),
    ADMIN(
            Set.of(

                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_CREATE,
                    MANAGER_DELETE,
                    USER_READ,
                    USER_CREATE,
                    USER_UPDATE,
                    USER_DELETE
    )),

    MANAGER(
            Set.of(

                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_CREATE,
                    MANAGER_DELETE,
                    USER_READ,
                    USER_CREATE,
                    USER_UPDATE,
                    USER_DELETE
    ))

    ;

    @Getter
    private final Set<PermissionEnum> permissions;



    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }

}
