package sn.psl.chartservie.controller;

import org.springframework.web.bind.annotation.*;
import sn.psl.chartservie.service.EnrollmentService;

@CrossOrigin(origins = "*")
@RequestMapping("/enrollment")
@RestController
public class EnrollmentController {
    private final EnrollmentService enrollmentService;
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/totalEnrolled")
    public int totalEnrolled(@RequestParam(name="platformId") String platformId, @RequestParam(name="courseId") int courseId) {
        return  this.enrollmentService.totalEnrolled(platformId, courseId);
    }
    @GetMapping("/completedStudentCount")
    public int completedStudentCount( @RequestParam(name="platformId") String platformId, @RequestParam(name="courseId") int courseId) {
        return this.enrollmentService.getCompletedStudentCount(platformId, courseId);
    }


}
