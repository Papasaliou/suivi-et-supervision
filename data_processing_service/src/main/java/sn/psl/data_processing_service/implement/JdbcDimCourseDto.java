package sn.psl.data_processing_service.implement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import sn.psl.data_processing_service.dto.DimCourseDto;
import sn.psl.data_processing_service.model.Courses;
import sn.psl.data_processing_service.repository.DimCourseDtoRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcDimCourseDto implements DimCourseDtoRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcDimCourseDto.class);

    private final JdbcTemplate jdbcTemplate;

    public JdbcDimCourseDto(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // =============== CHARGEMENT DimCourse ===============

    @Override
    public List<Courses> loadCoursesFromSource() {
        try {
            return jdbcTemplate.query(

                    "SELECT id,shortname, fullname,displayname, summary, category,progress, completed, startdate, enddate,lastaccess FROM courses",
                    this::mapRowToCourse
            );
        } catch (Exception e) {
            logger.error("Erreur lors du chargement des cours: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<DimCourseDto> findCoursesByCategory(int category) {
        try {
            return jdbcTemplate.query(
                    "SELECT course_id, city, course_name, category, start_date, end_date " +
                            "FROM DimCourse WHERE category = ?",
                    this::mapRowToDimCourseFromDim,
                    category
            );
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des cours par catégorie: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<DimCourseDto> findActiveCoursesAtDate(LocalDate date) {
        return List.of();
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

    @Override
    public void insertCoursesIntoDimension(List<Courses> courses) {
        try {
            for (Courses course : courses) {
                jdbcTemplate.update(  // update() PAS batchUpdate() !
                        "INSERT INTO DimCourse (course_id, fullname, summary, category, start_date, end_date) " +
                                "VALUES (?, ?, ?, ?, ?, ?)",
                        String.valueOf(course.getId()),
                        course.getFullname() != null ? course.getFullname() : "",
                        course.getSummary() != null ? course.getSummary() : "",
                        course.getCategoryid(),
                        course.getStartdate() != null ? course.getStartdate() : "0",
                        course.getEnddate() != null ? course.getEnddate() : "0"
                );
            }
            logger.info("Insertion de {} cours dans DimCourse réussie", courses.size());
        } catch (Exception e) {
            logger.error("Erreur lors de l'insertion des cours: {}", e.getMessage());
            throw new RuntimeException("Échec de l'insertion des cours", e);
        }
    }


//    @Override
//    public void insertCoursesIntoDimension(List<Courses> courses) {
//        try {
//            for (Courses course : courses) {
//
//                jdbcTemplate.batchUpdate(
//                        "INSERT INTO DimCourse (course_id, fullname, summary, category, start_date, end_date) " +
//                                "VALUES (?, ?, ?, ?, ?, ?)",
//                        String.valueOf(course.getId()),
//                        course.getFullname(),
//                        course.getSummary(),
//                        course.getCategoryid(),
//                        course.getStartdate(),
//                        course.getEnddate());
//
//            }
//
//            logger.info("Insertion de {} cours dans DimCourse", courses.size());
//        }catch (Exception e) {
//            logger.error("Erreur lors de l'insertion des cours: {}", e.getMessage());
//        }
//    }


    private Courses mapRowToCourse(ResultSet rs, int rowNum) throws SQLException {
        Courses course = new Courses();
        course.setId(rs.getInt("id"));
        course.setShortname(rs.getString("shortname"));
        course.setFullname(rs.getString("fullname"));
        course.setDisplayname(rs.getString("displayname"));
        course.setSummary(rs.getString("summary"));
        course.setCategoryid(rs.getInt("category"));
        course.setProgress(rs.getInt("progress"));
        course.isCompleted();
        course.setStartdate(rs.getDate("startdate"));
        course.setEnddate(rs.getDate("enddate"));
        course.setLastaccess(rs.getDate("lastaccess"));

        return course;
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
