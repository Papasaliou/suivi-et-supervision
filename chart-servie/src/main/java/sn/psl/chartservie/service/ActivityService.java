package sn.psl.chartservie.service;

import org.springframework.stereotype.Service;
import sn.psl.chartservie.dto.ActivityStatement;
import sn.psl.chartservie.dto.ActivityValidationStats;
import sn.psl.chartservie.repository.ActivityRepository;

import java.awt.*;
import  java.util.List;
@Service
public class ActivityService {
    private final ActivityRepository activityRepository;
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public ActivityStatement getActivityStatement(String platformId,int courseId) {
        return this.activityRepository.getActivityStatements(platformId, courseId);
    }

    public List<ActivityStatement> getALLActivityStatements() {
        return this.activityRepository.getAllActivityStatements();
    }

    public List<ActivityValidationStats> getAllActivityValidationStats(String platformId,int courseId ) {
        return this.activityRepository.getAllActivityValidationStats( platformId, courseId);
    }
}
