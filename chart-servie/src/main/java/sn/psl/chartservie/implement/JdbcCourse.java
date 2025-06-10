package sn.psl.chartservie.implement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import sn.psl.chartservie.dto.DimCourseDto;
import sn.psl.chartservie.repository.CourseRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcCourse implements CourseRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCourse.class);

    private final JdbcTemplate jdbcTemplate;

    public JdbcCourse(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<DimCourseDto> getAllCourseByPlatformId(String platformId) {
        try {
            return jdbcTemplate.query(
                    "SELECT course_id, fullname, summary, category, start_date, end_date " +
                            "FROM DimCourse ",
                    this::mapRowToDimCourseFromDim
            );
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des cours pour la plateforme {} {}",platformId, e.getMessage());
            return Collections.emptyList();
        }
    }
    @Override
    public DimCourseDto getCourseByPlatformAndCourseId(String platformId, int courseId) {
        try {
            List<DimCourseDto> courses = jdbcTemplate.query(
                    "SELECT course_id, fullname, summary, category, start_date, end_date " +
                            "FROM DimCourse dc JOIN FactEnrollment fe "+
                            "ON dc.course_id = fe.course_id WHERE "+
                            "fe.platform_id = ? AND fe.course_id = ?",
                    this::mapRowToDimCourseFromDim,
                    platformId,
                    courseId
            );

            // Retourne le premier cours s'il existe, sinon null
            return courses.isEmpty() ? null : courses.get(0);
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des cours", e);
            return null; // ou gérer l'erreur différemment selon vos besoins
        }
    }


    @Override
    public List<DimCourseDto> findAllCourses() {
        try {
            return jdbcTemplate.query(
                "SELECT course_id, fullname, summary, category, start_date, end_date " +
                        "FROM DimCourse ",
                this::mapRowToDimCourseFromDim
            );
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des cours", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<DimCourseDto> findActiveCoursesAtDate(Date date) {
        try {
            return jdbcTemplate.query(
                    "SELECT course_id, fullname, summary, category, start_date, end_date " +
                            "FROM DimCourse WHERE start_date <= ? AND end_date >= ?",
                    this::mapRowToDimCourseFromDim,
                    date, date
            );
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des cours actifs: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
    private DimCourseDto mapRowToDimCourseFromDim(ResultSet rs, int rowNum) throws SQLException {
        DimCourseDto course = new DimCourseDto();
        course.setCourseId(rs.getInt("course_id"));
        course.setFullName(rs.getString("fullname"));
        course.setSummary(rs.getString("summary"));
        course.setCategory(rs.getInt("category"));
        course.setStartDate(rs.getDate("start_date"));
        course.setEndDate(rs.getDate("end_date"));
        return course;
    }
}
