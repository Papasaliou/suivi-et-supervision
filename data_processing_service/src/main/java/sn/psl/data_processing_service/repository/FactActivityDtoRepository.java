package sn.psl.data_processing_service.repository;

import sn.psl.data_processing_service.dto.FactActivityDto;
import sn.psl.data_processing_service.model.ActivityStatus;

import java.util.List;

public interface FactActivityDtoRepository {

    List<ActivityStatus> loadActivitiesFromSource();

    void insertActivityIntFactActivity(List<ActivityStatus> activityStatusList);

    void insertActivitystatement();

    List<FactActivityDto> findAllFactActivityDto();

    List<FactActivityDto> findActivitiesByUserId(int userId);

    List<FactActivityDto> findActivitiesByCourseId(int courseId);

    List<FactActivityDto> findActivitiesByPlatformId(String platformId);
}
