package com.self.education.travelpayouts.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.self.education.travelpayouts.api.ProgramResponse;
import com.self.education.travelpayouts.domain.PartnershipPrograms;
import com.self.education.travelpayouts.exception.EntityNotFoundException;
import com.self.education.travelpayouts.mapper.PartnershipProgramsMapper;
import com.self.education.travelpayouts.repository.PartnershipProgramsRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PartnershipProgramsServiceImpl implements PartnershipProgramsService {

    private final PartnershipProgramsRepository programsRepository;
    private final PartnershipProgramsMapper programsMapper;

    @Override
    public List<ProgramResponse> getAllPrograms() {
        return programsRepository.findAll().stream().map(programsMapper::transform).toList();
    }

    @Override
    public PartnershipPrograms findPartnershipProgramByTitle(final String title) {
        return programsRepository.findByTitle(title)
                .orElseThrow(() -> new EntityNotFoundException("Partnership Program", title));
    }

    @Override
    public List<ProgramResponse> findProgramsByTermOrderByPopularityDesc(final String title) {
        return programsRepository.findByTitleContainingIgnoreCaseOrderBySubscriberCountDesc(title).stream()
                .map(programsMapper::transform).toList();
    }
}
