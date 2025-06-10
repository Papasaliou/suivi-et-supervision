package sn.psl.chartservie.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.psl.chartservie.dto.ApiResponse;
import sn.psl.chartservie.dto.DimCourseDto;
import sn.psl.chartservie.service.CourseService;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

//    @GetMapping("/courseByPlatform")
//    public List<DimCourseDto> getAllCourseByPlatformId(@RequestParam String platformId){
//        return courseService.getAllCourseByPlatformId(platformId);
//    }

//    @GetMapping("/courseByPlatformAndCourseId")
//    public DimCourseDto getAllCourseByPlatformId(@RequestParam(name="platformId") String platformId, @RequestParam(name= "courseId") int courseId ){
//        return courseService.getCourseByPlatformAndCourseId(platformId,courseId);
//    }

    @GetMapping("/courseByPlatformAndCourseId")
    public ResponseEntity<ApiResponse<DimCourseDto>> getAllCourseByPlatformId(@RequestParam(name="platformId") String platformId, @RequestParam(name= "courseId") int courseId ) {
        try {
            DimCourseDto dimCourseDtoList = courseService.getCourseByPlatformAndCourseId(platformId,courseId);

            ApiResponse<DimCourseDto> response = ApiResponse.success(
                    dimCourseDtoList,
                    "successful"
            );

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            ApiResponse<DimCourseDto> errorResponse = ApiResponse.error(
                    e.getMessage(),
                    Arrays.asList("failed")
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);

        } catch (Exception e) {
            ApiResponse<DimCourseDto> errorResponse = ApiResponse.error(
                    "Internal server error",
                    Arrays.asList("An unexpected error occurred")
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/courseByPlatform")
    public ResponseEntity<ApiResponse<List<DimCourseDto>>> getAllPlatform(@RequestParam(name="platformId") String platformId) {
        try {
            List<DimCourseDto> dimCourseDtoList = courseService.getAllCourseByPlatformId(platformId);

            ApiResponse<List<DimCourseDto>> response = ApiResponse.success(
                    dimCourseDtoList,
                    "successful"
            );

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            ApiResponse<List<DimCourseDto>> errorResponse = ApiResponse.error(
                    e.getMessage(),
                    Arrays.asList("failed")
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);

        } catch (Exception e) {
            ApiResponse<List<DimCourseDto>> errorResponse = ApiResponse.error(
                    "Internal server error",
                    Arrays.asList("An unexpected error occurred")
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
