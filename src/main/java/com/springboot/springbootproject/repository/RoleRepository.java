package com.springboot.springbootproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.springbootproject.entity.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    @Query(value = "SELECT * FROM role", nativeQuery = true)
    List<Role> findAll();

    @Query(value = "SELECT * FROM role r WHERE r.name = :name LIMIT 1", nativeQuery = true)
    Optional<Role> findByName(@Param("name") String name);

    @Query(value = "DELETE FROM role WHERE id = :id", nativeQuery = true)
    void deleteById(@Param("id") String id);
}
