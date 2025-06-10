package sn.psl.data_processing_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityStatus {
    private int cmid;
    private int userId;
    private int courseId;
    private String platformId;
    private String modname;
    private int instance;
    private int state;
    private Long  timecompleted;
    private int tracking;
    private boolean valueused;
    private boolean hascompletion;
    private boolean isautomatic;
    private boolean istrackeduser;
    private boolean uservisible;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public int getCmid() {
        return cmid;
    }

    public void setCmid(int cmid) {
        this.cmid = cmid;
    }

    public String getModname() {
        return modname;
    }

    public void setModname(String modname) {
        this.modname = modname;
    }

    public int getInstance() {
        return instance;
    }

    public void setInstance(int instance) {
        this.instance = instance;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Long  getTimecompleted() {
        return timecompleted;
    }

    public void setTimecompleted(Long  timecompleted) {
        this.timecompleted = timecompleted;
    }

    public int getTracking() {
        return tracking;
    }

    public void setTracking(int tracking) {
        this.tracking = tracking;
    }

    public boolean isValueused() {
        return valueused;
    }

    public void setValueused(boolean valueused) {
        this.valueused = valueused;
    }

    public boolean isHascompletion() {
        return hascompletion;
    }

    public void setHascompletion(boolean hascompletion) {
        this.hascompletion = hascompletion;
    }

    public boolean isIsautomatic() {
        return isautomatic;
    }

    public void setIsautomatic(boolean isautomatic) {
        this.isautomatic = isautomatic;
    }

    public boolean isIstrackeduser() {
        return istrackeduser;
    }

    public void setIstrackeduser(boolean istrackeduser) {
        this.istrackeduser = istrackeduser;
    }

    public boolean isUservisible() {
        return uservisible;
    }

    public void setUservisible(boolean uservisible) {
        this.uservisible = uservisible;
    }

    }