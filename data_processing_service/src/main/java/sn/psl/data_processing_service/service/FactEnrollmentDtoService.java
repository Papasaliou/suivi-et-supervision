package sn.psl.data_processing_service.service;

import org.springframework.stereotype.Service;
import sn.psl.data_processing_service.dto.FactEnrollmentDto;
import sn.psl.data_processing_service.repository.FactEnrollementDtoRepository;

import java.util.List;

@Service
public class FactEnrollmentDtoService {


    private final FactEnrollementDtoRepository factEnrollementDtoRepository;

    public FactEnrollmentDtoService(FactEnrollementDtoRepository factEnrollementDtoRepository) {
        this.factEnrollementDtoRepository = factEnrollementDtoRepository;
    }

    public List<FactEnrollmentDto> loadAllFactEnrollmentDto() {
        List<FactEnrollmentDto> factEnrollmentDtoList = this.factEnrollementDtoRepository.loadFactEnrollmentFromSource();
        this.factEnrollementDtoRepository.insertEnrollmentsByUserId(factEnrollmentDtoList);
        return factEnrollmentDtoList;
    }

    public List<FactEnrollmentDto> getDimFactEnrollmentDtoByPlatformId(String platformId) {
        return this.factEnrollementDtoRepository.findAllEnrollmentsByPlatformId(platformId);
    }

    public List<FactEnrollmentDto> getAllDimFactEnrollmentDto() {
        return this.factEnrollementDtoRepository.findAllEnrollments();
    }
}
