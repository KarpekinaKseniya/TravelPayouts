package com.self.education.travelpayouts.mapper;

import org.mapstruct.Mapper;
import com.self.education.travelpayouts.api.ProgramResponse;
import com.self.education.travelpayouts.domain.PartnershipPrograms;

@Mapper(componentModel = "spring")
public interface PartnershipProgramsMapper {

    ProgramResponse transform(PartnershipPrograms program);
}
