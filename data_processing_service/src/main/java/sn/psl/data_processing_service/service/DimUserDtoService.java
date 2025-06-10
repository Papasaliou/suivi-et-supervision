package sn.psl.data_processing_service.service;

import org.springframework.stereotype.Service;
import sn.psl.data_processing_service.dto.DimUserDto;
import sn.psl.data_processing_service.model.Users;
import sn.psl.data_processing_service.repository.DimPlatformDtoRepository;
import sn.psl.data_processing_service.repository.DimUserDtoRepository;

import java.util.List;

@Service
public class DimUserDtoService {
    private final DimUserDtoRepository dimUserDtoRepository;
    private final DimPlatformDtoRepository dimPlatformDtoRepository;

    public DimUserDtoService(DimUserDtoRepository dimUserDtoRepository,DimPlatformDtoRepository dimPlatformDtoRepository){
        this.dimUserDtoRepository = dimUserDtoRepository;
        this.dimPlatformDtoRepository = dimPlatformDtoRepository;
    }

    public List<Users> loadAllUsers() {
        List<Users> usersList = this.dimUserDtoRepository.loadUsersFromSource();
        this.dimUserDtoRepository.insertUsersIntoDimension(usersList);
        return usersList;
    }

    public List<DimUserDto> getDimUserDtoByRole(String status) {
        return this.dimUserDtoRepository.findUsersByRole(status);
    }

    public List<DimUserDto> getAllDimUserDto() {
        return this.dimUserDtoRepository.findAll();
    }
}
