package it.beachill.model.entities.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_CREATE_TOURNAMENT("admin:create"),
    ADMIN_DELETE_TOURNAMENT("admin:delete"),
    ADMIN_UPDATE_TOURNAMENT("admin:update"),
    ADMIN_READ_TOURNAMENT("admin:read"),
    SUPERADMIN_CREATE_TOURNAMENT("superadmin:create"),
    SUPERADMIN_DELETE_TOURNAMENT("superadmin:delete"),
    SUPERADMIN_UPDATE_TOURNAMENT("superadmin:update"),
    SUPERADMIN_READ_TOURNAMENT("superadmin:read");

    @Getter
    private final String permission;

}
