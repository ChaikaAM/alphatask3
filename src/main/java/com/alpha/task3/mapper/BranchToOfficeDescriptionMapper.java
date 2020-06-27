package com.alpha.task3.mapper;

import com.alpha.task3.domain.Branch;
import com.alpha.task3.model.OfficeDescription;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface BranchToOfficeDescriptionMapper {

    OfficeDescription map(Branch branch);

}
