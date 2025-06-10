package sn.psl.authentificationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import sn.psl.authentificationservice.dto.AdminDTO;
import sn.psl.authentificationservice.dto.ApiResponse;
import sn.psl.authentificationservice.dto.LoginDTO;
import sn.psl.authentificationservice.dto.LoginResponseDTO;
import sn.psl.authentificationservice.model.Admin;
import sn.psl.authentificationservice.service.AdminService;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@Validated @RequestBody AdminDTO adminDTO) {
        try {
            Admin registeredAdmin = adminService.register(adminDTO);
            return new ResponseEntity<>(registeredAdmin, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Une erreur s'est produite lors de l'inscription", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/admins")
    public ResponseEntity<?> getAllAdmins() {
        try {
            List<Admin> admins = adminService.findAllAdmins();
            return new ResponseEntity<>(admins, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Une erreur s'est produite lors de la récupération des administrateurs",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> loginAdmin(@RequestBody LoginDTO loginDTO) {
//        try {
//            AdminDTO adminDTO = adminService.login(loginDTO);
//            return new ResponseEntity<>(adminDTO, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Échec d'authentification: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
//        }
//    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@RequestBody LoginDTO loginDTO) {
        try {
            LoginResponseDTO loginResponse = adminService.login(loginDTO);

            ApiResponse<LoginResponseDTO> response = ApiResponse.success(
                    loginResponse,
                    "Login successful"
            );

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            ApiResponse<LoginResponseDTO> errorResponse = ApiResponse.error(
                    e.getMessage(),
                    Arrays.asList("Authentication failed")
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);

        } catch (Exception e) {
            ApiResponse<LoginResponseDTO> errorResponse = ApiResponse.error(
                    "Internal server error",
                    Arrays.asList("An unexpected error occurred")
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    @GetMapping("/profile")
    public ResponseEntity<?> getAdminProfile(@RequestParam String email) {
        try {
            return adminService.findByEmail(email)
                    .map(admin -> new ResponseEntity<>(admin, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>("Une erreur s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}