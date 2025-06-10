package sn.psl.chartservie.service;

import org.springframework.stereotype.Service;
import sn.psl.chartservie.dto.DimPlatformDto;
import sn.psl.chartservie.repository.PlatformRepository;
import java.util.List;

@Service
public class PlatformService {
    private  final PlatformRepository platformRepository;
    public PlatformService(PlatformRepository platformRepository) {
        this.platformRepository = platformRepository;
    }

    public List<DimPlatformDto> getAllPlatforms() {
        return this.platformRepository.findAllPlatform();
    }
}
