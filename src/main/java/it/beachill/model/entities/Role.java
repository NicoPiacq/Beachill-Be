package it.beachill.model.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static it.beachill.model.entities.Permission.*;

@RequiredArgsConstructor
public enum Role {
    USER(Collections.emptySet()),
    ADMIN(
            Set.of(
                    ADMIN_CREATE_TOURNAMENT,
                    ADMIN_DELETE_TOURNAMENT,
                    ADMIN_UPDATE_TOURNAMENT,
                    ADMIN_READ_TOURNAMENT
            )
            ),
    SUPERADMIN(
            Set.of(
                    ADMIN_CREATE_TOURNAMENT,
                    ADMIN_DELETE_TOURNAMENT,
                    ADMIN_UPDATE_TOURNAMENT,
                    ADMIN_READ_TOURNAMENT,
                    SUPERADMIN_CREATE_TOURNAMENT,
                    SUPERADMIN_DELETE_TOURNAMENT,
                    SUPERADMIN_UPDATE_TOURNAMENT,
                    SUPERADMIN_READ_TOURNAMENT
            )
            );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
