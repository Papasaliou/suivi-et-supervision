package sn.psl.data_processing_service.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimestampConverter {
    private static final Logger logger = LoggerFactory.getLogger(TimestampConverter.class);

    /**
     * Convertit un timestamp Unix (string) vers LocalDate
     * @param timestampString Le timestamp Unix sous forme de string (ex: "1737302786")
     * @return LocalDate ou null si conversion impossible
     */
    public static LocalDate timestampToDate(String timestampString) {
        if (timestampString == null || timestampString.trim().isEmpty() || "0".equals(timestampString)) {
            return null;
        }

        try {
            long timestamp = Long.parseLong(timestampString.trim());

            // Convertir le timestamp Unix en LocalDateTime puis extraire la date
            LocalDateTime dateTime = Instant.ofEpochSecond(timestamp)
                    .atZone(ZoneId.systemDefault())  // Utilise le fuseau horaire du système
                    .toLocalDateTime();

            return dateTime.toLocalDate();

        } catch (NumberFormatException e) {
            logger.warn("Impossible de convertir le timestamp en nombre: {}", timestampString);
            return null;
        } catch (Exception e) {
            logger.warn("Erreur lors de la conversion du timestamp {}: {}", timestampString, e.getMessage());
            return null;
        }
    }

    /**
     * Convertit un timestamp Unix (string) vers LocalDateTime
     * @param timestampString Le timestamp Unix sous forme de string (ex: "1748549380")
     * @return LocalDateTime ou null si conversion impossible
     */
    public static LocalDateTime timestampToDateTime(String timestampString) {
        if (timestampString == null || timestampString.trim().isEmpty() || "0".equals(timestampString)) {
            return null;
        }

        try {
            long timestamp = Long.parseLong(timestampString.trim());

            // Convertir le timestamp Unix en LocalDateTime
            return Instant.ofEpochSecond(timestamp)
                    .atZone(ZoneId.systemDefault())  // Utilise le fuseau horaire du système
                    .toLocalDateTime();

        } catch (NumberFormatException e) {
            logger.warn("Impossible de convertir le timestamp en nombre: {}", timestampString);
            return null;
        } catch (Exception e) {
            logger.warn("Erreur lors de la conversion du timestamp {}: {}", timestampString, e.getMessage());
            return null;
        }
    }

    /**
     * Convertit un timestamp Unix (string) vers java.sql.Date pour ClickHouse
     */
    public static Date timestampToSqlDate(String timestampString) {
        LocalDate localDate = timestampToDate(timestampString);
        return localDate != null ? Date.valueOf(localDate) : null;
    }

    /**
     * Convertit un timestamp Unix (string) vers java.sql.Timestamp pour ClickHouse
     */
    public static Timestamp timestampToSqlTimestamp(String timestampString) {
        LocalDateTime localDateTime = timestampToDateTime(timestampString);
        return localDateTime != null ? Timestamp.valueOf(localDateTime) : null;
    }

}
