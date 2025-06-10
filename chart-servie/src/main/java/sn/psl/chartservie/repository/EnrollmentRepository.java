package sn.psl.chartservie.repository;

public interface EnrollmentRepository {
    int getCompletedStudentCount(String platformId, long courseId);

    int totalEnrolled(String platformId, int courseId);
}
