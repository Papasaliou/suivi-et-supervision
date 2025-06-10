package sn.psl.data_processing_service.repository;

import sn.psl.data_processing_service.dto.DimUserDto;
import sn.psl.data_processing_service.model.Users;

import java.util.List;

public interface DimUserDtoRepository {

    List<Users> loadUsersFromSource();

    void insertUsersIntoDimension(List<Users> users);

    List<DimUserDto> findUsersByRole(String role);

    List<DimUserDto> findAll();


}
