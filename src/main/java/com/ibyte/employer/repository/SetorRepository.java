package com.ibyte.employer.repository;

import java.util.Optional;

import com.ibyte.employer.entity.Setor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SetorRepository extends JpaRepository<Setor, Long> {
    // try to find a employee by name
    Optional<Setor> findByName(@Param("name") String name);
}