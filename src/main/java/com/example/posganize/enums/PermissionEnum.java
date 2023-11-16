package com.example.posganize.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum PermissionEnum {

    //Admin permissions
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),


    //Manager permissions
    MANAGER_READ("management:read"),
    MANAGER_UPDATE("management:update"),
    MANAGER_CREATE("management:create"),
    MANAGER_DELETE("management:delete"),

    //Users permissions

    USER_READ("users:read"),
    USER_UPDATE("users:update"),
    USER_CREATE("users:create"),
    USER_DELETE("users:delete")

    ;

    @Getter
    private final String permission;


}
