package com.mikolajjanik.hospital_catering_admin.dao;

import com.mikolajjanik.hospital_catering_admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findAdminByEmail(String email);

    @Query(value = "SELECT zdjęcie FROM admin WHERE email = :email", nativeQuery = true)
    byte[] findProfilePictureByEmail(@Param("email") String email);
}
