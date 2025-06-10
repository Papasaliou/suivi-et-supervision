package sn.psl.data_processing_service.repository;

import sn.psl.data_processing_service.dto.FactEnrollmentDto;

import java.util.List;

public interface FactEnrollementDtoRepository {

    List<FactEnrollmentDto> findAllEnrollmentsByPlatformId(String platformId);

    List<FactEnrollmentDto> findAllEnrollments();

    List<FactEnrollmentDto> findEnrollmentsByUserId(int userId);

    void insertEnrollmentsByUserId(List<FactEnrollmentDto> enrollmentDtoList);

    List<FactEnrollmentDto> loadFactEnrollmentFromSource();
}
