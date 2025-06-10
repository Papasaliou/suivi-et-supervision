package sn.psl.data_processing_service.controller;

import org.springframework.web.bind.annotation.*;
import sn.psl.data_processing_service.dto.FactEnrollmentDto;
import sn.psl.data_processing_service.service.FactEnrollmentDtoService;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/enrollement")
public class FactEnrollmentDtoController {
    private final FactEnrollmentDtoService factEnrollmentDtoService;

    public FactEnrollmentDtoController(FactEnrollmentDtoService factEnrollmentDtoService) {
        this.factEnrollmentDtoService = factEnrollmentDtoService;
    }


    @GetMapping("/loadAll")
    public List<FactEnrollmentDto> loadAll() {
        return this.factEnrollmentDtoService.loadAllFactEnrollmentDto();
    }


    @GetMapping("/platformId/{id}")
    public List<FactEnrollmentDto> getDimPlatformDtoByStatus(@PathVariable String id) {
        return this.factEnrollmentDtoService.getDimFactEnrollmentDtoByPlatformId(id);
    }

    @GetMapping("/all")
    public List<FactEnrollmentDto> getDimPlatformDtoByStatus() {
        return this.factEnrollmentDtoService.getAllDimFactEnrollmentDto();
    }
}
