package com.alpha.task3.repository;

import com.alpha.task3.domain.Queue;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface QueuesRepository extends CrudRepository<Queue, Integer> {

    Collection<Queue> findAllByBranchesId(Integer branchesId);
}
