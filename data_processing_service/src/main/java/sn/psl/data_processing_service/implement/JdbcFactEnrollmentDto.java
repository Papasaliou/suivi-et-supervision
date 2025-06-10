package sn.psl.data_processing_service.implement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import sn.psl.data_processing_service.dto.FactEnrollmentDto;
import sn.psl.data_processing_service.repository.FactEnrollementDtoRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Repository
public class JdbcFactEnrollmentDto implements FactEnrollementDtoRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcFactEnrollmentDto.class);

    private final JdbcTemplate jdbcTemplate;

    public JdbcFactEnrollmentDto(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<FactEnrollmentDto> findAllEnrollmentsByPlatformId(String platformId) {
        try {
            return jdbcTemplate.query(
                    "SELECT user_id,course_id,platform_id,completed " +
                            "FROM FactEnrollment WHERE platform_id = ? ",
                    this::mapRowToDimEnrollmentDto,
                    platformId
            );
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des inscriptions", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<FactEnrollmentDto> findAllEnrollments() {

        try {
            return jdbcTemplate.query(
                    "SELECT user_id, course_id, platform_id, completed "+
                            "FROM FactEnrollment",
                    this::mapRowToDimEnrollmentDto
            );
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des inscriptions", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<FactEnrollmentDto> findEnrollmentsByUserId(int userId) {
        try {
            return jdbcTemplate.query(
                    "SELECT user_id,course_id,platform_id,completed " +
                            "FROM FactEnrollment WHERE user_id = ? ",
                    this::mapRowToDimEnrollmentDto,
                    userId
            );
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des inscriptions", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public void insertEnrollmentsByUserId(List<FactEnrollmentDto> enrollmentDtoList) {
        try {
            for (FactEnrollmentDto enrollmentDto : enrollmentDtoList) {
                jdbcTemplate.update(  // update() PAS batchUpdate() !
                    "INSERT INTO FactEnrollment (user_id,course_id,platform_id,completed) " +
                            "VALUES (?, ?, ?, ?)",
                    enrollmentDto.getUserId(),
                    enrollmentDto.getCourseId(),
                    enrollmentDto.getPlatformId(),
                    enrollmentDto.getCompleted()
                );
            }
            logger.info("Insertion de {} inscription dans DimFactEnrollmentDto réussie", enrollmentDtoList.size());
        } catch (Exception e) {
            logger.error("Erreur lors de l'insertion des inscriptions: {}", e.getMessage());
            throw new RuntimeException("Échec de l'insertion des inscriptions", e);
        }
    }

    @Override
    public List<FactEnrollmentDto> loadFactEnrollmentFromSource() {
        try {
            return jdbcTemplate.query(
                    "SELECT cc.course_id, cc.user_id, cc.platformId, cc.completed " +
                            "FROM course_completions cc " +
                            "JOIN enrolled_course_user eu " +
                            "ON cc.course_id = eu.course_id " +
                            "AND cc.user_id = eu.user_id " +
                            "AND cc.platformId = eu.platformId",
                    this::mapRowToDimEnrollmentDto
            );
        } catch (Exception e) {
            logger.error("Erreur: {}", e.getMessage());
            return Collections.emptyList();
        }
    }


    private FactEnrollmentDto mapRowToDimEnrollmentDto(ResultSet rs, int rowNum) throws SQLException {
        FactEnrollmentDto factEnrollmentDto = new FactEnrollmentDto();
        factEnrollmentDto.setUserId(rs.getInt("user_id"));
        factEnrollmentDto.setCourseId(rs.getInt("course_id"));
        factEnrollmentDto.setPlatformId(rs.getString("platformId"));
        factEnrollmentDto.setCompleted(rs.getBoolean("completed"));

        return factEnrollmentDto;
    }
}
