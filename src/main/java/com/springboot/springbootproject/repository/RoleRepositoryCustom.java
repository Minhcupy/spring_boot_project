package com.springboot.springbootproject.repository;

import com.springboot.springbootproject.dto.response.RoleResponse;
import com.springboot.springbootproject.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepositoryCustom {

    List<RoleResponse> findAllCustom();

    boolean existsByName(String name);

    Optional<RoleResponse> findByName(String name);

    void deleteByIdCustom(String id);

    Optional<Role> findEntityByName(String name);

    List<Role> findEntitiesByIds(List<String> ids);
}
