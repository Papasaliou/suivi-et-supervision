package sn.psl.data_processing_service.repository;

import sn.psl.data_processing_service.dto.DimCourseDto;
import sn.psl.data_processing_service.model.Courses;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface DimCourseDtoRepository {

    List<Courses> loadCoursesFromSource();

    List<DimCourseDto> findCoursesByCategory(int category);

    List<DimCourseDto> findActiveCoursesAtDate(LocalDate date);

    List<DimCourseDto> findAllCourses();

    List<DimCourseDto> findActiveCoursesAtDate(Date date);

    void insertCoursesIntoDimension(List<Courses> courses);
}
