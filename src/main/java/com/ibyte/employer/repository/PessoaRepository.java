package com.ibyte.employer.repository;

import java.util.List;

import com.ibyte.employer.entity.Pessoa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    // find all employees in a department
    List<Pessoa> findByDepartmentNavigation_Name(@Param("name") String name);
}