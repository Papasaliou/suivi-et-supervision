package sn.psl.data_processing_service.service;

import org.springframework.stereotype.Service;
import sn.psl.data_processing_service.dto.DimCourseDto;
import sn.psl.data_processing_service.dto.DimUserDto;
import sn.psl.data_processing_service.model.Courses;
import sn.psl.data_processing_service.model.Users;
import sn.psl.data_processing_service.repository.DimCourseDtoRepository;

import java.sql.Date;
import java.util.List;

@Service
public class DimCourseDtoService {
    private final DimCourseDtoRepository dimCourseDtoRepository;
    public DimCourseDtoService(DimCourseDtoRepository dimCourseDtoRepository) {
        this.dimCourseDtoRepository = dimCourseDtoRepository;
    }

    public List<Courses> loadAllCourses() {
        List<Courses> coursesList = this.dimCourseDtoRepository.loadCoursesFromSource();
        this.dimCourseDtoRepository.insertCoursesIntoDimension(coursesList);
        return coursesList;
    }

    public List<DimCourseDto> getDimCourseDtoByRole(int category) {
        return this.dimCourseDtoRepository.findCoursesByCategory(category);
    }

    public List<DimCourseDto> getDimUserDtoDate(Date date) {
        return this.dimCourseDtoRepository.findActiveCoursesAtDate(date);
    }

    public List<DimCourseDto> getAllDimCourseDto() {
        return this.dimCourseDtoRepository.findAllCourses();
    }

}
