package com.qlish.qlish_api.enums.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.qlish.qlish_api.enums.auth.Permissions.*;

@Getter
@RequiredArgsConstructor
public enum Role {

    USER(Set.of(
            USER_DEFAULT
    )
    ),
    ADMIN_VIEW(
            Set.of(
                    ADMIN_READ
            )
    ),
    ADMIN_FULL(
            Set.of(
                    ADMIN_READ,
                    ADMIN_CREATE,
                    ADMIN_DELETE,
                    ADMIN_UPDATE
            )
    ),
    DEV(
            Set.of(
                    ADMIN_READ,
                    ADMIN_CREATE,
                    ADMIN_DELETE,
                    ADMIN_UPDATE,
                    DEV_DEFAULT,
                    USER_DEFAULT
            )
    );
    private final Set<Permissions> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + name()));
        return authorities;

    }

}
