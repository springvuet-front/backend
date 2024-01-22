package prologbackend.service;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import prologbackend.domain.user.User;
import prologbackend.domain.user.UserRepository;
import prologbackend.domain.user.UserRole;
import prologbackend.dto.user.JoinDto;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    public UserServiceImpl(UserRepository userRepository, @Qualifier("passwordEncoder") BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    //boolean으로 return해서 결과 확인 -> t/f 결과에 따라서 작업하기 -> controller에서 t/f 에 따라 가입여부 처리
    @Override
    public boolean checkEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User signUp(JoinDto joinDto) {
        User newUser =  User.builder()
                .email(joinDto.getEmail())
                .nickname(joinDto.getNickname())
                .userPw(encoder.encode(joinDto.getUserPw()))
                .role(UserRole.USER) // 기본 권한은 USER로 설정
                .build();
        return userRepository.save(newUser);
    }




}
