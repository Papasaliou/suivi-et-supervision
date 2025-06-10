package sn.psl.chartservie.service;

import org.springframework.stereotype.Service;
import sn.psl.chartservie.dto.DimCourseDto;
import sn.psl.chartservie.repository.CourseRepository;
import  java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<DimCourseDto> getAllCourseByPlatformId( String platformId){
        return this.courseRepository.getAllCourseByPlatformId(platformId);
    }
    public DimCourseDto getCourseByPlatformAndCourseId( String platformId, int courseId){
        return this.courseRepository.getCourseByPlatformAndCourseId(platformId,courseId);
    }
}
