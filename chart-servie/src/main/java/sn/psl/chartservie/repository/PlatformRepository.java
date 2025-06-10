package sn.psl.chartservie.repository;

import sn.psl.chartservie.dto.DimPlatformDto;

import java.util.List;

public interface PlatformRepository {
    public abstract List<DimPlatformDto> findAllPlatform();

    public abstract List<DimPlatformDto> findPlatformsByStatus(String status);
}
