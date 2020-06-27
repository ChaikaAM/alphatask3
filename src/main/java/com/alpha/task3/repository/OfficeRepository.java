package com.alpha.task3.repository;

import com.alpha.task3.domain.Branch;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
public interface OfficeRepository extends CrudRepository<Branch, Integer> {
}
