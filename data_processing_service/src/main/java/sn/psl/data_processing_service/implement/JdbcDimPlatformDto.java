package sn.psl.data_processing_service.implement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import sn.psl.data_processing_service.dto.DimPlatformDto;
import sn.psl.data_processing_service.model.MoodlePlatform;
import sn.psl.data_processing_service.repository.DimPlatformDtoRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
@Repository
public class JdbcDimPlatformDto implements DimPlatformDtoRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcDimPlatformDto.class);

    private final JdbcTemplate jdbcTemplate;

    public JdbcDimPlatformDto(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // =============== CHARGEMENT DimPlatform ===============

    @Override
    public List<MoodlePlatform> loadPlatformsFromSource() {
        try {
            return jdbcTemplate.query(
                    "SELECT instance_id, site_url,site_name, admin_email, moodle_version,token,token_expiration FROM moodle_register",
                    this::mapRowToPlatform
            );
        } catch (Exception e) {
            logger.error("Erreur lors du chargement des plateformes: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<DimPlatformDto> findPlatformsByStatus(String status) {
        try {
            return jdbcTemplate.query(
                    "SELECT platform_id, platform_name, platform_url, administrator_email, status " +
                            "FROM DimPlatform WHERE status = ?",
                    this::mapRowToDimPlatformFromDim,
                    status
            );
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des plateformes par statut: {}", e.getMessage());
            return Collections.emptyList();
        }
    }



    @Override
    public void insertPlatformsIntoDimension(List<MoodlePlatform> platforms) {
        try {
            for (MoodlePlatform platform : platforms) {
                jdbcTemplate.update(
                        "INSERT INTO DimPlatform (platform_id, platform_name, platform_url, administrator_email, status) " +
                                "VALUES (?, ?, ?, ?, ?)",
                        platform.getInstance_id(),
                        platform.getSite_name(),
                        platform.getSite_url(),
                        platform.getAdmin_email(),
                        "UP"
                );
            }
            logger.info("Insertion de {} plateformes dans DimPlatform réussie", platforms.size());
        } catch (Exception e) {
            logger.error("Erreur lors de l'insertion des plateformes: {}", e.getMessage());
            throw new RuntimeException("Échec de l'insertion des plateformes", e);
        }
    }


    private MoodlePlatform mapRowToPlatform(ResultSet rs, int rowNum) throws SQLException {
        MoodlePlatform platform = new MoodlePlatform();
        platform.setInstance_id(rs.getString("instance_id"));
        platform.setSite_name(rs.getString("site_name"));
        platform.setSite_url(rs.getString("site_url"));
        platform.setAdmin_email(rs.getString("admin_email"));
        platform.setMoodle_version(rs.getString("moodle_version"));
        platform.setToken(rs.getString("token"));
        platform.setToken_expiration(rs.getString("token_expiration"));
        return platform;
    }

    private DimPlatformDto mapRowToDimPlatformFromDim(ResultSet rs, int rowNum) throws SQLException {
        DimPlatformDto platform = new DimPlatformDto();
        platform.setPlatformId(rs.getString("platform_id"));
        platform.setPlatformName(rs.getString("platform_name"));
        platform.setPlatformUrl(rs.getString("platform_url"));
        platform.setAdministratorEmail(rs.getString("administrator_email"));
        platform.setStatus(rs.getString("status"));
        return platform;
    }

}
