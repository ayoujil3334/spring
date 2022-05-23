package com.ayoujil.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayoujil.project.model.Logiciel;

public interface LogicielRepository extends JpaRepository <Logiciel, Long> {

}
