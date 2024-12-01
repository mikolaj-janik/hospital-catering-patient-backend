package com.mikolajjanik.hospital_catering_admin.service;

import com.mikolajjanik.hospital_catering_admin.dto.UserDTO;
import com.mikolajjanik.hospital_catering_admin.dto.LoginUserDTO;

public interface UserService {
    String verify(LoginUserDTO user);
    UserDTO getUserByLogin(String login);
}
