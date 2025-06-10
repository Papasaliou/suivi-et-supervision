package sn.psl.chartservie.repository;

import sn.psl.chartservie.dto.DimCourseDto;

import java.util.Date;
import java.util.List;

public interface CourseRepository {
    List<DimCourseDto> getAllCourseByPlatformId(String platformId);
    DimCourseDto getCourseByPlatformAndCourseId(String platformId, int courseId);

    List<DimCourseDto> findAllCourses();

    List<DimCourseDto> findActiveCoursesAtDate(Date date);
}
