package sn.psl.data_processing_service.service;

import org.springframework.stereotype.Service;
import sn.psl.data_processing_service.dto.FactActivityDto;
import sn.psl.data_processing_service.model.ActivityStatement;
import sn.psl.data_processing_service.model.ActivityStatus;
import sn.psl.data_processing_service.repository.FactActivityDtoRepository;

import java.util.List;

@Service
public class FactActivityDtoService {
    private final FactActivityDtoRepository factActivityDtoRepository;

    public FactActivityDtoService(FactActivityDtoRepository factActivityDtoRepository) {
        this.factActivityDtoRepository = factActivityDtoRepository;
    }

    public List<ActivityStatus> loadAllFactActivityDto() {
        List<ActivityStatus> activityStatusList = this.factActivityDtoRepository.loadActivitiesFromSource();
        this.factActivityDtoRepository.insertActivityIntFactActivity(activityStatusList);
        this.factActivityDtoRepository.insertActivitystatement();
        return activityStatusList;
    }

//    public void loadAllFactActivityStatement() {
//         this.factActivityDtoRepository.insertActivitystatement();
//    }

    public List<FactActivityDto> getFactActivityDtoByPlatformId(String platformId) {
        return this.factActivityDtoRepository.findActivitiesByPlatformId(platformId);
    }

    public List<FactActivityDto> getAllFactEnrollmentDtoByCourseId(int courseId) {
        return this.factActivityDtoRepository.findActivitiesByCourseId(courseId);
    }

    public List<FactActivityDto> getAllFactActivityDto() {
        return this.factActivityDtoRepository.findAllFactActivityDto();
    }

    public List<FactActivityDto> getFactActivityDtoByUserId(int userId) {
        return this.factActivityDtoRepository.findActivitiesByUserId(userId);
    }



}
