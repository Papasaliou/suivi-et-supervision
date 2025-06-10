package sn.psl.data_processing_service.implement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import sn.psl.data_processing_service.dto.DimUserDto;
import sn.psl.data_processing_service.model.Users;
import sn.psl.data_processing_service.repository.DimUserDtoRepository;
import sn.psl.data_processing_service.utils.TimestampConverter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcDimUserDto implements DimUserDtoRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcDimUserDto.class);

    private final JdbcTemplate jdbcTemplate;

    public JdbcDimUserDto(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    // =============== CHARGEMENT DimUser ===============

    @Override
    public List<Users> loadUsersFromSource() {
        try {
            return jdbcTemplate.query(
                    "SELECT id, username,firstname,lastname,fullname, email,firstaccess,lastaccess FROM users",
                    this::mapRowToDimUser
            );
        } catch (Exception e) {
            logger.error("Erreur lors du chargement des utilisateurs: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public void insertUsersIntoDimension(List<Users> users) {

        try {
            for (Users user : users) {
                // Convertir les timestamps Unix en dates
                Date firstAccess = TimestampConverter.timestampToSqlDate(user.getFirstaccess());
                Date lastAccess = TimestampConverter.timestampToSqlDate(user.getLastaccess());

                jdbcTemplate.update(
                        "INSERT INTO DimUser (user_id, fullname, email, first_access, last_access) " +
                                "VALUES (?, ?, ?, ?, ?)",
                        String.valueOf(user.getId()),
                        user.getFullname() != null ? user.getFullname() : "",
                        user.getEmail() != null ? user.getEmail() : "",
                        firstAccess,    // Date convertie depuis timestamp Unix
                        lastAccess      // Timestamp converti depuis timestamp Unix
                );
            }
            logger.info("Insertion de {} utilisateurs avec timestamps Unix convertis", users.size());
        } catch (Exception e) {
            logger.error("Erreur lors de l'insertion des utilisateurs: {}", e.getMessage());
            throw new RuntimeException("Échec de l'insertion des utilisateurs", e);
        }
    }



    @Override
    public List<DimUserDto> findAll() {
        try {
            return jdbcTemplate.query(
                    "SELECT user_id, fullname, email,first_access, last_access " +
                            "FROM DimUser",
                    this::mapRowToDimUserFromDim
            );
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des utilisateurs", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<DimUserDto> findUsersByRole(String role) {
        try {
            return jdbcTemplate.query(
                    "SELECT user_id, username, email, user_role, registration_date, last_access " +
                            "FROM DimUser WHERE user_role = ?",
                    this::mapRowToDimUserFromDim,
                    role
            );
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des utilisateurs par rôle: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    private Users mapRowToDimUser(ResultSet rs, int rowNum) throws SQLException {
        Users user = new Users();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setFirstname(rs.getString("firstname"));
        user.setLastname(rs.getString("lastname"));
        user.setFullname(rs.getString("fullname"));
        user.setEmail(rs.getString("email"));
        user.setFirstaccess(rs.getString("firstaccess"));
        user.setLastaccess(rs.getString("lastaccess"));
        return user;
    }

    private DimUserDto mapRowToDimUserFromDim(ResultSet rs, int rowNum) throws SQLException {
        DimUserDto user = new DimUserDto();
        user.setUserId(rs.getInt("user_id"));
        user.setFullname(rs.getString("fullname"));
        user.setEmail(rs.getString("email"));
        user.setFirstAccess(rs.getDate("first_access"));
        user.setLastAccess(rs.getDate("last_access"));
        return user;
    }

}
