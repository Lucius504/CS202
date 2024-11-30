package com.cs203.enums;

public enum Roles {
    ADMIN("Admin"),
    WORKER("Worker"),
    GUEST("Guest");

    private final String roleName;

    Roles(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    @Override
    public String toString() {
        return getRoleName();
    }

    public static Roles fromString(String roleName) {
        for (Roles role : Roles.values()) {
            if (role.getRoleName().equalsIgnoreCase(roleName)) {
                return role;
            }
        }
        throw new IllegalArgumentException("No enum constant with role name " + roleName);
    }
}
