package sn.psl.chartservie.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.psl.chartservie.dto.ActivityStatement;
import sn.psl.chartservie.dto.ActivityValidationStats;
import sn.psl.chartservie.dto.ApiResponse;
import sn.psl.chartservie.dto.DimCourseDto;
import sn.psl.chartservie.service.ActivityService;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins ="*")
@RestController
@RequestMapping("/statement")
public class ActivityController {
    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping("/course")
    public ResponseEntity<ApiResponse<ActivityStatement>> getActivityStatement(@RequestParam(name="platformId") String platformId,@RequestParam(name="courseId") int courseId) {
        try {
            ActivityStatement activityStatement = activityService.getActivityStatement(platformId, courseId);

            ApiResponse<ActivityStatement> response = ApiResponse.success(
                    activityStatement,
                    "successful"
            );

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            ApiResponse<ActivityStatement> errorResponse = ApiResponse.error(
                    e.getMessage(),
                    Arrays.asList("failed")
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);

        } catch (Exception e) {
            ApiResponse<ActivityStatement> errorResponse = ApiResponse.error(
                    "Internal server error",
                    Arrays.asList("An unexpected error occurred")
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/course/all")
    public ResponseEntity<ApiResponse<List<ActivityStatement>>> getAllActivityStatement() {
        try {
            List<ActivityStatement> activityStatement = activityService.getALLActivityStatements();

            ApiResponse<List<ActivityStatement>> response = ApiResponse.success(
                    activityStatement,
                    "successful"
            );

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            ApiResponse<List<ActivityStatement>> errorResponse = ApiResponse.error(
                    e.getMessage(),
                    Arrays.asList("failed")
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);

        } catch (Exception e) {
            ApiResponse<List<ActivityStatement>> errorResponse = ApiResponse.error(
                    "Internal server error",
                    Arrays.asList("An unexpected error occurred")
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    @GetMapping("/course/activityValidationStats")
    public ResponseEntity<ApiResponse<List<ActivityValidationStats>>> getAllActivityValidationStats(@RequestParam(name="platformId") String platformId,@RequestParam(name="courseId") int courseId) {
        try {
            List<ActivityValidationStats> activityStatement = activityService.getAllActivityValidationStats(platformId, courseId);

            ApiResponse<List<ActivityValidationStats>> response = ApiResponse.success(
                    activityStatement,
                    "successful"
            );

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            ApiResponse<List<ActivityValidationStats>> errorResponse = ApiResponse.error(
                    e.getMessage(),
                    Arrays.asList("failed")
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);

        } catch (Exception e) {
            ApiResponse<List<ActivityValidationStats>> errorResponse = ApiResponse.error(
                    "Internal server error",
                    Arrays.asList("An unexpected error occurred")
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }



}
