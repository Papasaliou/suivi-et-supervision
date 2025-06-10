package sn.psl.authentificationservice.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.psl.authentificationservice.model.Admin;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String platformId;
    private String role;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public static AdminDTO fromEntity(Admin admin, boolean includePassword) {
        AdminDTO dto = new AdminDTO();
        dto.setFirstname(admin.getFirstname());
        dto.setLastname(admin.getLastname());
        dto.setEmail(admin.getEmail());
        dto.setPlatformId(admin.getPlatformId());
        dto.setRole(admin.getRole());

        if (includePassword) {
            dto.setPassword(admin.getPassword());
        }
        return dto;
    }
}