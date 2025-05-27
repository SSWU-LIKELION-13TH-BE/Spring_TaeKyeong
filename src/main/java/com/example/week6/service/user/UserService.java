package com.example.week6.service.user;

import com.example.week6.dto.user.UserInfoResponseDTO;
import com.example.week6.dto.user.UserLoginResponseDTO;
import com.example.week6.dto.user.UserSignupRequestDTO;
import com.example.week6.dto.user.UserLoginRequestDTO;
import com.example.week6.entity.user.User;
import com.example.week6.repository.User.UserRepository;
import com.example.week6.security.JwtTokenProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    //회원가입(비밀번호 암호화 후 저장)
    public void signup(UserSignupRequestDTO requestDTO) {
        User user = new User();
        user.setUserId(requestDTO.getUserId());
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        user.setName(requestDTO.getName());
        user.setProfileImage(requestDTO.getProfileImage());
        userRepository.save(user);}

        //로그인(ID/PW 검증 후 JWT 발급)
        public UserLoginResponseDTO login (UserLoginRequestDTO requestDTO){
            User user = userRepository.findByUserId(requestDTO.getUserId()).orElseThrow(() -> new UsernameNotFoundException("User not found with ID " + requestDTO.getUserId()));
            if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
                throw new IllegalArgumentException("Invalid password");
            }
            String token = jwtTokenProvider.createToken(user.getUserId());
            return new UserLoginResponseDTO(user.getUserId(), token);
        }
        @Override
        public UserDetails loadUserByUsername (String userId) throws UsernameNotFoundException {
            User user = userRepository.findByUserId(userId)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with ID " + userId));

            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getUserId())
                    .password(user.getPassword())
                    .authorities("ROLE_USER")
                    .build();

        }
    public UserInfoResponseDTO getMyInfo(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new UsernameNotFoundException("User not found : " + userId));
        return new UserInfoResponseDTO(user.getUserId(), user.getName(), user.getProfileImage());
    }
}