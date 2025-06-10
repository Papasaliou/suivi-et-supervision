package sn.psl.chartservie.implement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import sn.psl.chartservie.dto.ActivityStatement;
import sn.psl.chartservie.dto.ActivityValidationStats;
import sn.psl.chartservie.dto.DimCourseDto;
import sn.psl.chartservie.dto.FactActivityDto;
import sn.psl.chartservie.repository.ActivityRepository;

import java.util.Collections;
import java.util.List;

@Repository
public class JdbcActivity implements ActivityRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcActivity.class);

    private final JdbcTemplate jdbcTemplate;

    public JdbcActivity(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int totalActivities(String platformId, int courseId) {
        return 0;
    }

    @Override
    public ActivityStatement getActivityStatements(String platformId, int courseId) {
      try {
            return jdbcTemplate.queryForObject(
                    "SELECT " +
                            "platform_id, " +
                            "course_id, " +
                            "totalEnrolled, " +
                            "totalActivities, " +
                            "totalCompleted, " +
                            "completionRate " +
                            "FROM EnrollmentStatsDetailedView " +
                            "WHERE course_id = ? AND platform_id = ?",
                    new Object[]{courseId, platformId},
                    (rs, rowNum) -> {
                        ActivityStatement stats = new ActivityStatement();
                        stats.setPlatformId(rs.getString("platform_id"));
                        stats.setCourseId(rs.getInt("course_id"));
                        stats.setTotalEnrolled(rs.getInt("totalEnrolled"));
                        stats.setTotalActivities(rs.getInt("totalActivities"));
                        stats.setTotalCompleted(rs.getInt("totalCompleted"));
                        stats.setCompletionRate(rs.getFloat("completionRate"));
                        return stats;
                    }
            );
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des statistiques d'inscription: {}", e.getMessage());
            return new ActivityStatement(); // Retourne un objet vide plutôt que null
        }
    }

    @Override
    public List<ActivityStatement> getAllActivityStatements() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM EnrollmentStatsDetailedView",
                    (rs, rowNum) -> {
                        ActivityStatement stats = new ActivityStatement();
                        stats.setPlatformId(rs.getString("platform_id"));
                        stats.setCourseId(rs.getInt("course_id"));
                        stats.setTotalEnrolled(rs.getInt("totalEnrolled"));
                        stats.setTotalActivities(rs.getInt("totalActivities"));
                        stats.setTotalCompleted(rs.getInt("totalCompleted"));
                        stats.setCompletionRate(rs.getFloat("completionRate"));
                        return stats;
                    }
            );
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des statistiques d'inscription: {}", e.getMessage());
            return Collections.emptyList(); // Retourne une liste vide plutôt qu'un objet unique
        }
    }

    @Override
    public List<ActivityValidationStats> getAllActivityValidationStats(String platformId, int courseId) {
        try {
            return jdbcTemplate.query(
                    "SELECT " +
                            "    activity_id, " +
                            "    COUNT(DISTINCT user_id) AS total_students, " +
                            "    COUNT(DISTINCT CASE WHEN score = 2 THEN fa.user_id END) AS validated_students, " +
                            "    ROUND(" +
                            "        COUNT(DISTINCT CASE WHEN score = 2 THEN fa.user_id END) * 100.0 / " +
                            "        NULLIF(COUNT(DISTINCT fa.user_id), 0), " +
                            "        2" +
                            "    ) AS validation_rate " +
                            "FROM FactActivity fa  JOIN FactEnrollment fe ON fa.course_id = fe.course_id AND fa.user_id = fe.user_id " +
                            "WHERE fa.platform_id = ? AND fa.course_id = ? " +
                            "GROUP BY activity_id " +
                            "ORDER BY activity_id",
                    new Object[]{platformId, courseId},
                    (rs, rowNum) -> {
                        ActivityValidationStats stats = new ActivityValidationStats();
                        stats.setActivityId(rs.getInt("activity_id"));
                        stats.setTotalStudents(rs.getInt("total_students"));
                        stats.setValidatedStudents(rs.getInt("validated_students"));
                        stats.setValidationRate(rs.getFloat("validation_rate"));
                        return stats;
                    }
            );
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des statistiques de validation des activités: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<FactActivityDto> getAllActivity(String platformId, int courseId) {
        return List.of();
    }



}
