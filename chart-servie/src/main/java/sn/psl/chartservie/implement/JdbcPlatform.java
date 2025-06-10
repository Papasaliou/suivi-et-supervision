package sn.psl.chartservie.implement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import sn.psl.chartservie.dto.DimPlatformDto;
import sn.psl.chartservie.repository.PlatformRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Repository
public class JdbcPlatform implements PlatformRepository {
    private static final Logger logger = LoggerFactory.getLogger(PlatformRepository.class);

    private final JdbcTemplate jdbcTemplate;

    public JdbcPlatform(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<DimPlatformDto> findAllPlatform() {
        try {
            return jdbcTemplate.query(
                    "SELECT platform_id, platform_name, platform_url, administrator_email, status,  " +
                    "FROM DimPlatform",
                    this::mapRowToDimPlatformFromDim
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
