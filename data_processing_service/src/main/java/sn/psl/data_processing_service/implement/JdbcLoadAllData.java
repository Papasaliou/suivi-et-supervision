//package sn.psl.data_processing_service.implement;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.jdbc.core.JdbcTemplate;
//import sn.psl.data_processing_service.dto.*;
//import sn.psl.data_processing_service.repository.LoadAllDataRepository;
//
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//
//
//public class JdbcLoadAllData implements LoadAllDataRepository {
//
//    private static final Logger logger = LoggerFactory.getLogger(JdbcLoadAllData.class);
//    private final JdbcTemplate jdbcTemplate;
//
//    public JdbcLoadAllData(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//    // =============== CHARGEMENT DimUser ===============
//
//
//
//    // =============== CHARGEMENT DimCourse ===============
//
//    @Override
//    public List<DimCourseDto> loadCoursesFromSource() {
//        try {
//            return jdbcTemplate.query(
//                    "SELECT id, location, title, category, start_date, end_date FROM courses",
//                    this::mapRowToDimCourse
//            );
//        } catch (Exception e) {
//            logger.error("Erreur lors du chargement des cours: {}", e.getMessage());
//            return Collections.emptyList();
//        }
//    }
//
//    @Override
//    public List<DimCourseDto> findCoursesByCategory(String category) {
//        try {
//            return jdbcTemplate.query(
//                    "SELECT course_id, city, course_name, category, start_date, end_date " +
//                            "FROM DimCourse WHERE category = ?",
//                    this::mapRowToDimCourseFromDim,
//                    category
//            );
//        } catch (Exception e) {
//            logger.error("Erreur lors de la récupération des cours par catégorie: {}", e.getMessage());
//            return Collections.emptyList();
//        }
//    }
//
//    @Override
//    public List<DimCourseDto> findActiveCoursesAtDate(LocalDate date) {
//        return List.of();
//    }
//
//    @Override
//    public List<DimCourseDto> findActiveCoursesAtDate(Date date) {
//        try {
//            return jdbcTemplate.query(
//                    "SELECT course_id, city, course_name, category, start_date, end_date " +
//                            "FROM DimCourse WHERE start_date <= ? AND end_date >= ?",
//                    this::mapRowToDimCourseFromDim,
//                    date, date
//            );
//        } catch (Exception e) {
//            logger.error("Erreur lors de la récupération des cours actifs: {}", e.getMessage());
//            return Collections.emptyList();
//        }
//    }
//
//    @Override
//    public void insertCoursesIntoDimension(List<DimCourseDto> courses) {
//        try {
//            jdbcTemplate.batchUpdate(
//                    "INSERT INTO DimCourse (course_id, city, course_name, category, start_date, end_date) " +
//                            "VALUES (?, ?, ?, ?, ?, ?)",
//                    courses,
//                    courses.size(),
//                    (ps, course) -> {
//                        ps.setLong(1, course.getCourseId());
//                        ps.setString(2, course.getCity());
//                        ps.setString(3, course.getCourseName());
//                        ps.setString(4, course.getCategory());
//                        ps.setObject(5, course.getStartDate());
//                        ps.setObject(6, course.getEndDate());
//                    }
//            );
//            logger.info("Insertion de {} cours dans DimCourse", courses.size());
//        } catch (Exception e) {
//            logger.error("Erreur lors de l'insertion des cours: {}", e.getMessage());
//        }
//    }
//
//    // =============== CHARGEMENT DimPlatform ===============
//
//    @Override
//    public List<DimPlatformDto> loadPlatformsFromSource() {
//        try {
//            return jdbcTemplate.query(
//                    "SELECT id, name, url, admin_email, status FROM platforms",
//                    this::mapRowToDimPlatform
//            );
//        } catch (Exception e) {
//            logger.error("Erreur lors du chargement des plateformes: {}", e.getMessage());
//            return Collections.emptyList();
//        }
//    }
//
//    @Override
//    public List<DimPlatformDto> findPlatformsByStatus(String status) {
//        try {
//            return jdbcTemplate.query(
//                    "SELECT platform_id, platform_name, platform_url, administrator_email, status " +
//                            "FROM DimPlatform WHERE status = ?",
//                    this::mapRowToDimPlatformFromDim,
//                    status
//            );
//        } catch (Exception e) {
//            logger.error("Erreur lors de la récupération des plateformes par statut: {}", e.getMessage());
//            return Collections.emptyList();
//        }
//    }
//
//    @Override
//    public void insertPlatformsIntoDimension(List<DimPlatformDto> platforms) {
//        try {
//            jdbcTemplate.batchUpdate(
//                    "INSERT INTO DimPlatform (platform_id, platform_name, platform_url, administrator_email, status) " +
//                            "VALUES (?, ?, ?, ?, ?)",
//                    platforms,
//                    platforms.size(),
//                    (ps, platform) -> {
//                        ps.setString(1, platform.getPlatformId());
//                        ps.setString(2, platform.getPlatformName());
//                        ps.setString(3, platform.getPlatformUrl());
//                        ps.setString(4, platform.getAdministratorEmail());
//                        ps.setString(5, platform.getStatus());
//                    }
//            );
//            logger.info("Insertion de {} plateformes dans DimPlatform", platforms.size());
//        } catch (Exception e) {
//            logger.error("Erreur lors de l'insertion des plateformes: {}", e.getMessage());
//        }
//    }
//
//    @Override
//    public List<DimTimeDto> generateTimeRange(LocalDate startDate, LocalDate endDate) {
//        return List.of();
//    }
//
//    // =============== CHARGEMENT DimTime ===============
//
//    @Override
//    public List<DimTimeDto> generateTimeRange(Date startDate, Date endDate) {
//        try {
//            return jdbcTemplate.query(
//                    "SELECT toUInt64(toYYYYMMDD(date_val)) as time_id, " +
//                            "date_val as date, " +
//                            "toDayOfWeek(date_val) as day_of_week, " +
//                            "toMonth(date_val) as month, " +
//                            "toYear(date_val) as year " +
//                            "FROM (SELECT addDays(toDate(?), number) as date_val " +
//                            "FROM numbers(dateDiff('day', toDate(?), toDate(?)) + 1)) " +
//                            "WHERE date_val >= toDate(?) AND date_val <= toDate(?)",
//                    this::mapRowToDimTime,
//                    startDate, startDate, endDate, startDate, endDate
//            );
//        } catch (Exception e) {
//            logger.error("Erreur lors de la génération des données de temps: {}", e.getMessage());
//            return Collections.emptyList();
//        }
//    }
//
//    @Override
//    public List<DimTimeDto> findTimeByYear(Integer year) {
//        try {
//            return jdbcTemplate.query(
//                    "SELECT time_id, date, day_of_week, month, year " +
//                            "FROM DimTime WHERE year = ?",
//                    this::mapRowToDimTimeFromDim,
//                    year
//            );
//        } catch (Exception e) {
//            logger.error("Erreur lors de la récupération des données de temps par année: {}", e.getMessage());
//            return Collections.emptyList();
//        }
//    }
//
//    @Override
//    public void insertTimeIntoDimension(List<DimTimeDto> timeRecords) {
//        try {
//            jdbcTemplate.batchUpdate(
//                    "INSERT INTO DimTime (time_id, date, day_of_week, month, year) " +
//                            "VALUES (?, ?, ?, ?, ?)",
//                    timeRecords,
//                    timeRecords.size(),
//                    (ps, time) -> {
//                        ps.setLong(1, time.getTimeId());
//                        ps.setObject(2, time.getDate());
//                        ps.setInt(3, time.getDayOfWeek());
//                        ps.setInt(4, time.getMonth());
//                        ps.setInt(5, time.getYear());
//                    }
//            );
//            logger.info("Insertion de {} enregistrements de temps dans DimTime", timeRecords.size());
//        } catch (Exception e) {
//            logger.error("Erreur lors de l'insertion des données de temps: {}", e.getMessage());
//        }
//    }
//
//    // =============== CHARGEMENT FactActivity ===============
//
//    @Override
//    public List<FactActivityDto> loadActivitiesFromSource() {
//        try {
//            return jdbcTemplate.query(
//                    "SELECT activity_id, user_id, course_id, platform_id, time_id, duration, score, completion_status " +
//                            "FROM activity_source_table",
//                    this::mapRowToFactActivity
//            );
//        } catch (Exception e) {
//            logger.error("Erreur lors du chargement des activités: {}", e.getMessage());
//            return Collections.emptyList();
//        }
//    }
//
//    @Override
//    public List<FactActivityDto> findActivitiesByUserId(Long userId) {
//        try {
//            return jdbcTemplate.query(
//                    "SELECT activity_id, user_id, course_id, platform_id, time_id, duration, score, completion_status " +
//                            "FROM FactActivity WHERE user_id = ?",
//                    this::mapRowToFactActivityFromFact,
//                    userId
//            );
//        } catch (Exception e) {
//            logger.error("Erreur lors de la récupération des activités par utilisateur: {}", e.getMessage());
//            return Collections.emptyList();
//        }
//    }
//
//    @Override
//    public List<FactActivityDto> findActivitiesByCourseId(Long courseId) {
//        try {
//            return jdbcTemplate.query(
//                    "SELECT activity_id, user_id, course_id, platform_id, time_id, duration, score, completion_status " +
//                            "FROM FactActivity WHERE course_id = ?",
//                    this::mapRowToFactActivityFromFact,
//                    courseId
//            );
//        } catch (Exception e) {
//            logger.error("Erreur lors de la récupération des activités par cours: {}", e.getMessage());
//            return Collections.emptyList();
//        }
//    }
//
//    // =============== CHARGEMENT FactEnrollment ===============
//
//    @Override
//    public List<FactEnrollmentDto> loadEnrollmentsFromSource() {
//        try {
//            return jdbcTemplate.query(
//                    "SELECT enrollment_id, user_id, course_id, platform_id, progress_percentage " +
//                            "FROM enrollment_source_table",
//                    this::mapRowToFactEnrollment
//            );
//        } catch (Exception e) {
//            logger.error("Erreur lors du chargement des inscriptions: {}", e.getMessage());
//            return Collections.emptyList();
//        }
//    }
//
//    @Override
//    public List<FactEnrollmentDto> findEnrollmentsByUserId(Long userId) {
//        try {
//            return jdbcTemplate.query(
//                    "SELECT enrollment_id, user_id, course_id, platform_id, progress_percentage " +
//                            "FROM FactEnrollment WHERE user_id = ?",
//                    this::mapRowToFactEnrollmentFromFact,
//                    userId
//            );
//        } catch (Exception e) {
//            logger.error("Erreur lors de la récupération des inscriptions par utilisateur: {}", e.getMessage());
//            return Collections.emptyList();
//        }
//    }
//
//    // =============== MAPPERS ===============
//
//    private DimUserDto mapRowToDimUser(ResultSet rs, int rowNum) throws SQLException {
//        DimUserDto user = new DimUserDto();
//        user.setUserId(rs.getInt("id"));
//        user.setUsername(rs.getString("username"));
//        user.setEmail(rs.getString("email"));
//        user.setUserRole(rs.getString("role"));
//        user.setRegistrationDate(rs.getObject("created_at", Date.class));
//        user.setLastAccess(rs.getObject("last_login_at", Date.class));
//        return user;
//    }
//
//    private DimUserDto mapRowToDimUserFromDim(ResultSet rs, int rowNum) throws SQLException {
//        DimUserDto user = new DimUserDto();
//        user.setUserId(rs.getInt("user_id"));
//        user.setUsername(rs.getString("username"));
//        user.setEmail(rs.getString("email"));
//        user.setUserRole(rs.getString("user_role"));
//        user.setRegistrationDate(rs.getObject("registration_date", Date.class));
//        user.setLastAccess(rs.getObject("last_access", Date.class));
//        return user;
//    }
//
//    private DimCourseDto mapRowToDimCourse(ResultSet rs, int rowNum) throws SQLException {
//        DimCourseDto course = new DimCourseDto();
//        course.setCourseId(rs.getInt("id"));
//        course.setCity(rs.getString("location"));
//        course.setCourseName(rs.getString("title"));
//        course.setCategory(rs.getString("category"));
//        course.setStartDate(rs.getObject("start_date", Date.class));
//        course.setEndDate(rs.getObject("end_date", Date.class));
//        return course;
//    }
//
//    private DimCourseDto mapRowToDimCourseFromDim(ResultSet rs, int rowNum) throws SQLException {
//        DimCourseDto course = new DimCourseDto();
//        course.setCourseId(rs.getInt("course_id"));
//        course.setCity(rs.getString("city"));
//        course.setCourseName(rs.getString("course_name"));
//        course.setCategory(rs.getString("category"));
//        course.setStartDate(rs.getObject("start_date", Date.class));
//        course.setEndDate(rs.getObject("end_date", Date.class));
//        return course;
//    }
//
//    private DimPlatformDto mapRowToDimPlatform(ResultSet rs, int rowNum) throws SQLException {
//        DimPlatformDto platform = new DimPlatformDto();
//        platform.setPlatformId(rs.getString("platform_id"));
//        platform.setPlatformName(rs.getString("name"));
//        platform.setPlatformUrl(rs.getString("url"));
//        platform.setAdministratorEmail(rs.getString("admin_email"));
//        platform.setStatus(rs.getString("status"));
//        return platform;
//    }
//
//    private DimPlatformDto mapRowToDimPlatformFromDim(ResultSet rs, int rowNum) throws SQLException {
//        DimPlatformDto platform = new DimPlatformDto();
//        platform.setPlatformId(rs.getString("platform_id"));
//        platform.setPlatformName(rs.getString("platform_name"));
//        platform.setPlatformUrl(rs.getString("platform_url"));
//        platform.setAdministratorEmail(rs.getString("administrator_email"));
//        platform.setStatus(rs.getString("status"));
//        return platform;
//    }
//
//    private DimTimeDto mapRowToDimTime(ResultSet rs, int rowNum) throws SQLException {
//        DimTimeDto time = new DimTimeDto();
//        time.setTimeId(rs.getInt("time_id"));
//        time.setDate(LocalDate.parse(rs.getString("date")));
//        time.setDayOfWeek(rs.getInt("day_of_week"));
//        time.setMonth(rs.getInt("month"));
//        time.setYear(rs.getInt("year"));
//        return time;
//    }
//
//    private DimTimeDto mapRowToDimTimeFromDim(ResultSet rs, int rowNum) throws SQLException {
//        DimTimeDto time = new DimTimeDto();
//        time.setTimeId(rs.getInt("time_id"));
//        time.setDate(LocalDate.parse(rs.getString("date")));
//        time.setDayOfWeek(rs.getInt("day_of_week"));
//        time.setMonth(rs.getInt("month"));
//        time.setYear(rs.getInt("year"));
//        return time;
//    }
//
//    private FactActivityDto mapRowToFactActivity(ResultSet rs, int rowNum) throws SQLException {
//        FactActivityDto activity = new FactActivityDto();
//        activity.setActivityId(rs.getInt("activity_id"));
//        activity.setUserId(rs.getInt("user_id"));
//        activity.setCourseId(rs.getInt("course_id"));
//        activity.setPlatformId(rs.getString("platform_id"));
//        activity.setTimeId(rs.getInt("time_id"));
//        activity.setDuration(rs.getObject("duration", Integer.class));
//        activity.setScore(rs.getInt("score"));
//        activity.setCompletionStatus(rs.getInt("completion_status"));
//        return activity;
//    }
//
//    private FactActivityDto mapRowToFactActivityFromFact(ResultSet rs, int rowNum) throws SQLException {
//        return mapRowToFactActivity(rs, rowNum); // Même structure
//    }
//
//    private FactEnrollmentDto mapRowToFactEnrollment(ResultSet rs, int rowNum) throws SQLException {
//        FactEnrollmentDto enrollment = new FactEnrollmentDto();
//        enrollment.setEnrollmentId(rs.getInt("enrollment_id"));
//        enrollment.setUserId(rs.getInt("user_id"));
//        enrollment.setCourseId(rs.getInt("course_id"));
//        enrollment.setPlatformId(rs.getString("platform_id"));
//        enrollment.setProgressPercentage(rs.getInt("progress_percentage"));
//        return enrollment;
//    }
//
//    private FactEnrollmentDto mapRowToFactEnrollmentFromFact(ResultSet rs, int rowNum) throws SQLException {
//        return mapRowToFactEnrollment(rs, rowNum); // Même structure
//    }
//
//}
