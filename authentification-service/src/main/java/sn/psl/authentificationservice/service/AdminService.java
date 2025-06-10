package sn.psl.authentificationservice.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.psl.authentificationservice.dto.AdminDTO;
import sn.psl.authentificationservice.dto.LoginDTO;
import sn.psl.authentificationservice.dto.LoginResponseDTO;
import sn.psl.authentificationservice.model.Admin;
import sn.psl.authentificationservice.repository.AdminRepository;

import java.util.Optional;
import java.util.List;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin register(AdminDTO adminDTO) {
        // Check if email already exists
        if (adminRepository.existsByEmail(adminDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        // Create new admin from DTO
        Admin admin = new Admin();
        admin.setFirstname(adminDTO.getFirstname());
        admin.setLastname(adminDTO.getLastname());
        admin.setEmail(adminDTO.getEmail());
        admin.setPassword(adminDTO.getPassword());
        admin.setPlatformId(adminDTO.getPlatformId());
        admin.setRole("user");

        // Save admin to database
        return adminRepository.save(admin);
    }


//    public AdminDTO login(LoginDTO loginDTO) {
//
//        Admin admin = adminRepository.findByEmail(loginDTO.getEmail()).get();
//
//            AdminDTO adminDTO = new AdminDTO();
//            adminDTO.setFirstname(admin.getFirstname());
//            adminDTO.setLastname(admin.getLastname());
//            adminDTO.setEmail(admin.getEmail());
//            adminDTO.setPassword(admin.getPassword());
//            return adminDTO;
//    }

//    public AdminDTO login(LoginDTO loginDTO) {
//        Admin admin = adminRepository.findByEmail(loginDTO.getEmail()).get();
//        if ( admin != null && loginDTO.getPassword().equals(admin.getPassword())) {
//
//            AdminDTO adminDTO = new AdminDTO();
//            adminDTO.setFirstname(admin.getFirstname());
//            adminDTO.setLastname(admin.getLastname());
//            adminDTO.setEmail(admin.getEmail());
//            adminDTO.setPassword(admin.getPassword());
//            adminDTO.setPlatformId(admin.getPlatformId());
//            return adminDTO;
//
//        } else {
//            throw new IllegalArgumentException("Invalid email or password");
//        }
//    }

    public LoginResponseDTO login(LoginDTO loginDTO) {
        // Rechercher l'admin par email
        Optional<Admin> adminOpt = adminRepository.findByEmail(loginDTO.getEmail());

        if (adminOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        Admin admin = adminOpt.get();

        // Vérifier le mot de passe
        if (!loginDTO.getPassword().equals(admin.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        // Créer le DTO utilisateur (sans mot de passe pour la sécurité)
        AdminDTO userDTO = AdminDTO.fromEntity(admin, false);

        // Générer un token (ici simple UUID, en production utiliser JWT)
//        String token = generateToken(admin);
        String token = "";


        // Retourner la réponse avec user et token
        return new LoginResponseDTO(userDTO, token);
    }

//    private String generateToken(Admin admin) {
//        // Version simple avec UUID
//        // En production, utiliser JWT avec une vraie logique de token
//        return UUID.randomUUID().toString();
//    }

    public List<Admin> findAllAdmins() {
        return adminRepository.findAll();
    }

    public Optional<Admin> findById(Long id) {
        return adminRepository.findById(id);
    }

    public Optional<Admin> findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }


}