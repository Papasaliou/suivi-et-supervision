package sn.psl.chartservie.service;

import org.springframework.stereotype.Service;
import sn.psl.chartservie.repository.EnrollmentRepository;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public int getCompletedStudentCount(String platformId, long courseId){
        return enrollmentRepository.getCompletedStudentCount(platformId, courseId);
    }

    public  int totalEnrolled(String platformId, int courseId){
        return enrollmentRepository.totalEnrolled(platformId, courseId);
    }

}
