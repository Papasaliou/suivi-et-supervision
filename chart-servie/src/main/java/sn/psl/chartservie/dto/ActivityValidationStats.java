package sn.psl.chartservie.dto;

import lombok.Data;

@Data
public class ActivityValidationStats {
    private int activityId;
    private int validatedStudents;
    private int totalStudents;
    private float validationRate;


    public int getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(int totalStudents) {
        this.totalStudents = totalStudents;
    }

    public float getValidationRate() {
        return validationRate;
    }

    public void setValidationRate(float validationRate) {
        this.validationRate = validationRate;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getValidatedStudents() {
        return validatedStudents;
    }

    public void setValidatedStudents(int validatedStudents) {
        this.validatedStudents = validatedStudents;
    }
}
