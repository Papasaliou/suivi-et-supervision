package sn.psl.chartservie.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.psl.chartservie.dto.ApiResponse;
import sn.psl.chartservie.dto.DimPlatformDto;
import sn.psl.chartservie.service.PlatformService;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/platform")
public class PlatformController {

    private final PlatformService platformService;
    public PlatformController(PlatformService platformService) {
        this.platformService = platformService;
    }

    @GetMapping("/allPlatform")
    public ResponseEntity<ApiResponse<List<DimPlatformDto>>> getAllPlatform() {
        try {
            List<DimPlatformDto> platformDtoList = platformService.getAllPlatforms();

            ApiResponse<List<DimPlatformDto>> response = ApiResponse.success(
                    platformDtoList,
                    "successful"
            );

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            ApiResponse<List<DimPlatformDto>> errorResponse = ApiResponse.error(
                    e.getMessage(),
                    Arrays.asList("failed")
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);

        } catch (Exception e) {
            ApiResponse<List<DimPlatformDto>> errorResponse = ApiResponse.error(
                    "Internal server error",
                    Arrays.asList("An unexpected error occurred")
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

//    @GetMapping("/allPlatform")
//    public List<DimPlatformDto> getAllPlatform() {
//        return platformService.getAllPlatforms();
//    }
}
