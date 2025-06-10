package sn.psl.chartservie.repository;

import sn.psl.chartservie.dto.ActivityStatement;
import sn.psl.chartservie.dto.ActivityValidationStats;
import sn.psl.chartservie.dto.FactActivityDto;
import java.util.List;

public interface ActivityRepository {
    List<FactActivityDto> getAllActivity(String platformId, int courseId);
    int totalActivities(String platformId, int courseId);

    ActivityStatement getActivityStatements(String platformId, int courseId);
    List<ActivityStatement> getAllActivityStatements();

    List<ActivityValidationStats> getAllActivityValidationStats(String platformId, int courseId);

}

