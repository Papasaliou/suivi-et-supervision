package sn.psl.data_processing_service.model;

import lombok.Data;

@Data
public class MoodlePlatform {
    private String instance_id;
    private String site_url;
    private String site_name;
    private String admin_email;
    private String moodle_version;
    private String token;
    private String token_expiration;

    public String getInstance_id() {
        return instance_id;
    }

    public void setInstance_id(String instance_id) {
        this.instance_id = instance_id;
    }

    public String getSite_url() {
        return site_url;
    }

    public void setSite_url(String site_url) {
        this.site_url = site_url;
    }

    public String getSite_name() {
        return site_name;
    }

    public void setSite_name(String site_name) {
        this.site_name = site_name;
    }

    public String getAdmin_email() {
        return admin_email;
    }

    public void setAdmin_email(String admin_email) {
        this.admin_email = admin_email;
    }

    public String getMoodle_version() {
        return moodle_version;
    }

    public void setMoodle_version(String moodle_version) {
        this.moodle_version = moodle_version;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken_expiration() {
        return token_expiration;
    }

    public void setToken_expiration(String token_expiration) {
        this.token_expiration = token_expiration;
    }
}
