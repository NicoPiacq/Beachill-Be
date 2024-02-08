package it.beachill.model.entities.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    MANAGER_CREATE_TOURNAMENT("manager:create"),
    MANAGER_DELETE_TOURNAMENT("manager:delete"),
    MANAGER_UPDATE_TOURNAMENT("manager:update"),
    MANAGER_READ_TOURNAMENT("manager:read"),
    ADMIN_CREATE_TOURNAMENT("admin:create"),
    ADMIN_DELETE_TOURNAMENT("admin:delete"),
    ADMIN_UPDATE_TOURNAMENT("admin:update"),
    ADMIN_READ_TOURNAMENT("admin:read");

    @Getter
    private final String permission;

}
