package ru.javarush.taskbook.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Сергей on 29.01.2015.
 */
/*
@Entity
@Table(name = "roles")
*/
public class UserRole {
/*
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer roleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 20)
    private Role role;

    @ManyToMany( mappedBy = "userRoles")
    private Set<User> users = new HashSet<>();

    public UserRole() {
    }

    public UserRole(Role role) {
        this.role = role;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return role.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRole userRole = (UserRole) o;

        if (role != userRole.role) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return role.hashCode();
    }
*/
}
