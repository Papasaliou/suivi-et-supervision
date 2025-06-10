package sn.psl.data_processing_service.implement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import sn.psl.data_processing_service.dto.FactActivityDto;
import sn.psl.data_processing_service.model.ActivityStatus;
import sn.psl.data_processing_service.repository.FactActivityDtoRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Repository
public class JdbcFactActivityDto implements FactActivityDtoRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcFactActivityDto.class);

    private final JdbcTemplate jdbcTemplate;

    public JdbcFactActivityDto(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<ActivityStatus> loadActivitiesFromSource() {
        try {
            return jdbcTemplate.query(
                    "SELECT cmid, user_id, course_id, platformId, modname, instance, state, timecompleted, tracking,"+
                            "valueused, hascompletion, isautomatic, istrackeduser, uservisible " +
                            "FROM activity_statuses",
                    this::mapRowToActivityStatus
            );
        } catch (Exception e) {
            logger.error("Erreur lors du chargement des activités: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public void insertActivityIntFactActivity(List<ActivityStatus> activities) {
       for (ActivityStatus activityStatus : activities) {
           try {
               jdbcTemplate.update(
                       "INSERT INTO FactActivity (activity_id, user_id, course_id, platform_id, duration, score, completion_status )" +
                               "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                       activityStatus.getCmid(),
                       activityStatus.getUserId(),
                       activityStatus.getCourseId(),
                       activityStatus.getPlatformId(),
                       activityStatus.getTimecompleted(),
                       activityStatus.getState(),
                       0
               );
           } catch (Exception e) {
               logger.error("Erreur lors de la sauvegarde des activités: {}", e.getMessage());
           }
       }
    }
    @Override
    public void insertActivitystatement() {
        try {
            jdbcTemplate.execute("INSERT INTO EnrollmentStatsDetailedView " +
                "SELECT " +
                "platform_id, " +
                "course_id, " +
                "COUNT(DISTINCT user_id) AS totalEnrolled, " +
                "COUNT(DISTINCT activity_id) AS totalActivities, " +
                "COUNT(DISTINCT activity_id) FILTER (WHERE score = 2) AS totalCompleted, " +
                "ROUND( " +
                "   COUNT(DISTINCT activity_id) FILTER (WHERE score = 2) * 100.0 / " +
                "   NULLIF(COUNT(DISTINCT activity_id), 0), " +
                "   2 " +
                ") AS completionRate " +
                "FROM FactActivity " +
                "GROUP BY platform_id, course_id");
        } catch (Exception e) {
            logger.error("Erreur lors de la sauvegarde des activités: {}", e.getMessage());
        }

    }

    @Override
    public List<FactActivityDto> findAllFactActivityDto() {
        try {
            return jdbcTemplate.query(
                    "SELECT activity_id, user_id, course_id, platform_id, date_id, duration, score, completion_status " +
                            "FROM FactActivity ",
                    this::mapRowToDimfactActivityDtoFromDim
            );
        } catch (Exception e) {
            logger.error("Erreur lors de la recuperation de toutes les activités : {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<FactActivityDto> findActivitiesByUserId(int userId) {
        try {
            return jdbcTemplate.query(
                    "SELECT activity_id, user_id, course_id, platform_id, date_id, duration, score, completion_status " +
                            " FROM FactActivity "+
                            " WHERE user_id = ? ",
                    this::mapRowToDimfactActivityDtoFromDim,
                    userId
            );
        } catch (Exception e) {
            logger.error("Erreur lors de la recuperation des activités pour l'utilisateur {} : {}",userId, e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<FactActivityDto> findActivitiesByCourseId(int courseId) {
        try {
            return jdbcTemplate.query(
                    "SELECT activity_id, user_id, course_id, platform_id, date_id, duration, score, completion_status " +
                            "FROM FactActivity "+
                            " WHERE course_id = ? ",
                    this::mapRowToDimfactActivityDtoFromDim,
                    courseId
            );
        } catch (Exception e) {
            logger.error("Erreur lors de la recuperation des activités pour le cours {} : {}",courseId, e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<FactActivityDto> findActivitiesByPlatformId(String platformId) {
        try {
            return jdbcTemplate.query(
                    "SELECT activity_id, user_id, course_id, platform_id, date_id, duration, score, completion_status " +
                            " FROM FactActivity"+
                            " WHERE platform_id = ? ",
                    this::mapRowToDimfactActivityDtoFromDim,
                    platformId
            );
        } catch (Exception e) {
            logger.error("Erreur lors de la recuperation des activités pour la platforme {} : {}",platformId, e.getMessage());
            return Collections.emptyList();
        }
    }


    private ActivityStatus mapRowToActivityStatus(ResultSet rs, int rowNum) throws SQLException {
        ActivityStatus activityStatus = new ActivityStatus();
        activityStatus.setCmid(rs.getInt("cmid"));
        activityStatus.setUserId(rs.getInt("user_id"));
        activityStatus.setCourseId(rs.getInt("course_id"));
        activityStatus.setPlatformId(rs.getString("platformId"));
        activityStatus.setModname(rs.getString("modname"));
        activityStatus.setInstance(rs.getInt("instance"));
        activityStatus.setState(rs.getInt("state"));
        activityStatus.setTimecompleted(rs.getLong("timecompleted"));
        activityStatus.setTracking(rs.getInt("tracking"));
        activityStatus.setValueused(rs.getBoolean("valueused"));
        activityStatus.setHascompletion(rs.getBoolean("hascompletion"));
        activityStatus.setIsautomatic(rs.getBoolean("isautomatic"));
        activityStatus.setIstrackeduser(rs.getBoolean("istrackeduser"));
        activityStatus.setUservisible(rs.getBoolean("uservisible"));
        return activityStatus;
    }

    private FactActivityDto mapRowToDimfactActivityDtoFromDim(ResultSet rs, int rowNum) throws SQLException {
        FactActivityDto factActivityDto = new FactActivityDto();
        factActivityDto.setActivityId(rs.getInt("activity_id"));
        factActivityDto.setUserId(rs.getInt("user_id"));
        factActivityDto.setCourseId(rs.getInt("course_id"));
        factActivityDto.setPlatformId(rs.getString("platform_id"));
        factActivityDto.setDateId(rs.getDate("date_id"));
        factActivityDto.setDuration(rs.getInt("duration"));
        factActivityDto.setScore(rs.getInt("score"));
        factActivityDto.setCompletionStatus(rs.getInt("completion_status"));
        return factActivityDto;
    }

}
