package sn.psl.authentificationservice.dto;
import lombok.Data;

@Data
public class LoginResponseDTO {
    private  AdminDTO user;
    private  String token;

    public LoginResponseDTO(AdminDTO user, String token) {
        this.user = user;
        this.token = token;
    }

    public LoginResponseDTO() {
    }

    public AdminDTO getUser() {
        return user;
    }

    public void setUser(AdminDTO user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}