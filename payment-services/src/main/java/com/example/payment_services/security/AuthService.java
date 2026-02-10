package com.example.payment_services.security;

import com.example.payment_services.dto.LoginRequestDto;
import com.example.payment_services.dto.LoginResponseDto;
import com.example.payment_services.dto.SignUpResponseDto;
import com.example.payment_services.entity.User;
import com.example.payment_services.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthUtils authUtils;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUserName(), loginRequestDto.getPassword()));

        User user = (User) authentication.getPrincipal();

        String token = authUtils.generateJwtToken(user);

        return new LoginResponseDto(user.getId(), token);


    }

    public SignUpResponseDto signin(LoginRequestDto loginRequestDto) {

        User user = userRepository.findByUserName(loginRequestDto.getUserName()).orElse(null);

        if (user != null) {
            throw new IllegalArgumentException();
        }

        User user1 = new User();
        user1.setUserName(loginRequestDto.getUserName());
        user1.setPassword(passwordEncoder.encode(loginRequestDto.getPassword()));

        userRepository.save(user1);

        String name = "user " + loginRequestDto.getUserName() + " has been created ";


        return new SignUpResponseDto(name, "success");


    }
}
