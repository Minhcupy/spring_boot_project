package com.springboot.springbootproject.repository;

import com.springboot.springbootproject.entity.InvalidatedToken;
import java.util.List;
import java.util.Optional;

public interface InvalidatedTokenRepositoryCustom {

    boolean existsByTokenId(String id);

    Optional<InvalidatedToken> findByIdCustom(String id);

    List<InvalidatedToken> findAllCustom();
}
