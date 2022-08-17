package com.self.education.travelpayouts.service;

import java.util.List;
import com.self.education.travelpayouts.api.ProgramResponse;
import com.self.education.travelpayouts.domain.PartnershipPrograms;

public interface PartnershipProgramsService {

    List<ProgramResponse> getAllPrograms();

    PartnershipPrograms findPartnershipProgramByTitle(String title);

    List<ProgramResponse> findProgramsByTermOrderByPopularityDesc(String title);
}
