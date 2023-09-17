package com.odde.atddv2.repo;

import com.odde.atddv2.entity.relations.School;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface SchoolRepo extends Repository<School, Long> {
    List<School> findAll();
}
