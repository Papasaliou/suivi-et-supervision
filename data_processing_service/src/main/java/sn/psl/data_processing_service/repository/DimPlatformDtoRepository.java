package sn.psl.data_processing_service.repository;

import sn.psl.data_processing_service.dto.DimPlatformDto;
import sn.psl.data_processing_service.model.MoodlePlatform;

import java.util.List;

public interface DimPlatformDtoRepository {

    List<MoodlePlatform> loadPlatformsFromSource();

    List<DimPlatformDto> findPlatformsByStatus(String status);

    void insertPlatformsIntoDimension(List<MoodlePlatform> platforms);
}
