package sn.psl.data_processing_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.psl.data_processing_service.dto.DimPlatformDto;
import sn.psl.data_processing_service.dto.DimUserDto;
import sn.psl.data_processing_service.model.MoodlePlatform;
import sn.psl.data_processing_service.model.Users;
import sn.psl.data_processing_service.service.DimUserDtoService;

import java.util.List;
@RestController
@RequestMapping("/users")
public class DimUserDtoController {

    private final DimUserDtoService dimUserDtoService;

    public DimUserDtoController(DimUserDtoService dimUserDtoService) {
        this.dimUserDtoService = dimUserDtoService;
    }

    @GetMapping("/loadAll")
    public List<Users> loadAll() {
        return this.dimUserDtoService.loadAllUsers();
    }


    @GetMapping("/role/{role}")
    public List<DimUserDto> getDimPlatformDtoByStatus(@PathVariable String role) {
        return this.dimUserDtoService.getDimUserDtoByRole(role);
    }

    @GetMapping("/all")
    public List<DimUserDto> getDimPlatformDtoByStatus() {
        return this.dimUserDtoService.getAllDimUserDto();
    }
}
