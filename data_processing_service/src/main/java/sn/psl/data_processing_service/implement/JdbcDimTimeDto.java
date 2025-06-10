package sn.psl.data_processing_service.implement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcDimTimeDto {
    private static final Logger logger = LoggerFactory.getLogger(JdbcDimTimeDto.class);

    private final JdbcTemplate jdbcTemplate;

    public JdbcDimTimeDto(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
