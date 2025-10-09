package com.springboot.springbootproject.repository;

import java.util.List;
import java.util.Optional;

import com.springboot.springbootproject.entity.InvalidatedToken;

public interface InvalidatedTokenRepositoryCustom {

    boolean existsByTokenId(String id);

    Optional<InvalidatedToken> findByIdCustom(String id);

    List<InvalidatedToken> findAllCustom();
}
