package sn.psl.data_processing_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.psl.data_processing_service.dto.DimCourseDto;
import sn.psl.data_processing_service.model.Courses;
import sn.psl.data_processing_service.service.DimCourseDtoService;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class DimCourseDtoController {

    private final DimCourseDtoService dimCourseDtoService;
    public DimCourseDtoController(DimCourseDtoService dimCourseDtoService) {
        this.dimCourseDtoService = dimCourseDtoService;
    }

    @GetMapping("/loadAll")
    public List<Courses> loadAll() {
        return this.dimCourseDtoService.loadAllCourses();
    }

    @GetMapping("/date/{date}")
    public List<DimCourseDto> getDimPlatformDtoByStatus(@PathVariable Date date) {
        return this.dimCourseDtoService.getDimUserDtoDate(date);
    }

    @GetMapping("/all")
    public List<DimCourseDto> getDimPlatformDtoByStatus() {
        return this.dimCourseDtoService.getAllDimCourseDto();
    }
}
