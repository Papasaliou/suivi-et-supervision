package sn.psl.data_processing_service.controller;

import org.springframework.web.bind.annotation.*;
import sn.psl.data_processing_service.dto.DimPlatformDto;
import sn.psl.data_processing_service.model.MoodlePlatform;
import sn.psl.data_processing_service.service.DimPlatformDtoService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/platform")
public class DimPlatformDtoController {
    private final DimPlatformDtoService dimPlatformDtoService;

    public DimPlatformDtoController(DimPlatformDtoService dimPlatformDtoService) {
        this.dimPlatformDtoService = dimPlatformDtoService;
    }


    @GetMapping("/loadAll")
    public List<MoodlePlatform> loadAll() {
        return this.dimPlatformDtoService.loadAllPlatforms();
    }


    @GetMapping("/status/{status}")
    public List<DimPlatformDto> getDimPlatformDtoByStatus(@PathVariable String status) {
        return this.dimPlatformDtoService.getDimPlatformDtoByStatus(status);
    }
}
