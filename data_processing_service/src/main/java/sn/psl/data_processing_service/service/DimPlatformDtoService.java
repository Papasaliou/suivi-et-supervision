package sn.psl.data_processing_service.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sn.psl.data_processing_service.dto.DimPlatformDto;
import sn.psl.data_processing_service.model.MoodlePlatform;
import sn.psl.data_processing_service.repository.DimPlatformDtoRepository;

import java.util.List;

@Service
public class DimPlatformDtoService {
    private final DimPlatformDtoRepository dimPlatformDtoRepository;

    public DimPlatformDtoService(DimPlatformDtoRepository dimPlatformDtoRepository) {
        this.dimPlatformDtoRepository = dimPlatformDtoRepository;
    }


    public List<MoodlePlatform> loadAllPlatforms() {
        List<MoodlePlatform> platformList = this.dimPlatformDtoRepository.loadPlatformsFromSource();
        this.dimPlatformDtoRepository.insertPlatformsIntoDimension(platformList);
        return platformList;
    }

    public List<DimPlatformDto> getDimPlatformDtoByStatus(String status) {
        return this.dimPlatformDtoRepository.findPlatformsByStatus(status);
    }

}
