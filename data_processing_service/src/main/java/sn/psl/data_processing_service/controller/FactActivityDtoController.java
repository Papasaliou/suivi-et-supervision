package sn.psl.data_processing_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.psl.data_processing_service.dto.FactActivityDto;
import sn.psl.data_processing_service.model.ActivityStatus;
import sn.psl.data_processing_service.service.FactActivityDtoService;

import java.util.List;

@RestController
@RequestMapping("/activities")
public class FactActivityDtoController {

    private final FactActivityDtoService factActivityDtoService;

    public FactActivityDtoController(FactActivityDtoService factActivityDtoService) {
        this.factActivityDtoService = factActivityDtoService;
    }

    @GetMapping("/loadAll")
    public List<ActivityStatus> loadAll() {
        return this.factActivityDtoService.loadAllFactActivityDto();
    }


    @GetMapping("/platform/{platformId}")
    public List<FactActivityDto> getFactActivityDtoByPlatformId(@PathVariable String platformId) {
        return this.factActivityDtoService.getFactActivityDtoByPlatformId(platformId);
    }

    @GetMapping("users/{userId}")
    public List<FactActivityDto> getFactActivityDtoByUserId(@PathVariable int userId) {
        return this.factActivityDtoService.getFactActivityDtoByUserId(userId);
    }

    @GetMapping("course/{courseId}")
    public List<FactActivityDto> getFactActivityDtoByCourseId(@PathVariable int courseId) {
        return this.factActivityDtoService.getAllFactEnrollmentDtoByCourseId(courseId);
    }

    @GetMapping("/all")
    public List<FactActivityDto> getDimPlatformDtoByStatus() {
        return this.factActivityDtoService.getAllFactActivityDto();
    }

}
