package sn.psl.chartservie.implement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import sn.psl.chartservie.repository.EnrollmentRepository;

@Repository
public class JdbcEnrollment implements EnrollmentRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcEnrollment.class);

    private final JdbcTemplate jdbcTemplate;

    public JdbcEnrollment(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
//    @Override
//    public int totalEnrolled(String platformId, int courseId) {
//        try {
//            return jdbcTemplate.query(
//                    "SELECT COUNT(DISTINCT user_id) as totalEnrolled, SUM(completed) as totalCompleted "+
//                        "FROM FactEnrollment "+
//                        "WHERE course_id = ? AND platform_id = ?",
//                    platformId,courseId
//            );
//        } catch (Exception e) {
//            logger.error("Erreur lors de la récupération des cours", e.getMessage());
//            return 0;
//        }
//    }

    @Override
    public int getCompletedStudentCount(String platformId, long courseId) {
        try {
            Integer count = jdbcTemplate.queryForObject(
                    "SELECT COUNT(DISTINCT user_id) AS completed_students " +
                            "FROM FactEnrollment " +
                            "WHERE platform_id = ? AND course_id = ? AND completed = 1",
                    new Object[]{platformId, courseId},
                    Integer.class
            );
            return count != null ? count : 0;
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération du nombre d'étudiants ayant terminé le cours : {}", e.getMessage(), e);
            return 0;
        }
    }

    @Override
    public int totalEnrolled(String platformId, int courseId) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT COUNT(DISTINCT user_id) as totalEnrolled " +
                            "FROM FactEnrollment " +
                            "WHERE course_id = ? AND platform_id = ?",
                    Integer.class,
                    courseId, platformId
            );
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération du nombre total d'inscrits: {}", e.getMessage());
            return 0;
        }
    }


//    @Override
//    public List<DimCourseDto> findAllCourses() {
//
//    }


}
