package sn.psl.data_processing_service.repository;

import sn.psl.data_processing_service.dto.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface LoadAllDataRepository {
//    List<DimUserDto> loadUsersFromSource();
//
//    List<DimUserDto> findUsersByRole(String role);

    void insertUsersIntoDimension(List<DimUserDto> users);

    List<DimCourseDto> loadCoursesFromSource();

    List<DimCourseDto> findCoursesByCategory(String category);

    List<DimCourseDto> findActiveCoursesAtDate(LocalDate date);

    List<DimCourseDto> findActiveCoursesAtDate(Date date);

    void insertCoursesIntoDimension(List<DimCourseDto> courses);

    List<DimPlatformDto> loadPlatformsFromSource();

    List<DimPlatformDto> findPlatformsByStatus(String status);

    void insertPlatformsIntoDimension(List<DimPlatformDto> platforms);

//    List<DimTimeDto> generateTimeRange(LocalDate startDate, LocalDate endDate);
//
//    List<DimTimeDto> generateTimeRange(LocalDate startDate, LocalDate endDate);

    List<DimTimeDto> generateTimeRange(Date startDate, Date endDate);

    List<DimTimeDto> findTimeByYear(Integer year);

    void insertTimeIntoDimension(List<DimTimeDto> timeRecords);

    List<FactActivityDto> loadActivitiesFromSource();

    List<FactActivityDto> findActivitiesByUserId(Long userId);

    List<FactActivityDto> findActivitiesByCourseId(Long courseId);

    List<FactEnrollmentDto> loadEnrollmentsFromSource();

    List<FactEnrollmentDto> findEnrollmentsByUserId(Long userId);
}
