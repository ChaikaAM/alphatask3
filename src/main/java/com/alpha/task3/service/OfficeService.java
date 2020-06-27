package com.alpha.task3.service;

import com.alpha.task3.mapper.BranchToOfficeDescriptionMapper;
import com.alpha.task3.model.OfficeDescription;
import com.alpha.task3.repository.OfficeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OfficeService {

    private final OfficeRepository officeRepository;

    private final BranchToOfficeDescriptionMapper branchToOfficeDescriptionMapper;

    public Optional<OfficeDescription> findById(Integer id) {
        return officeRepository.findById(id).map(branchToOfficeDescriptionMapper::map);
    }
}
