package nhom8.example.quizz.service.impl;

import lombok.RequiredArgsConstructor;
import nhom8.example.quizz.dto.PasswordChangeRequest;
import nhom8.example.quizz.dto.ProfileDTO;
import nhom8.example.quizz.dto.UserResponseDTO;
import nhom8.example.quizz.entity.User;
import nhom8.example.quizz.repository.UserRepository;
import nhom8.example.quizz.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Sẽ cấu hình ở bước 3

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        // Cần khai báo findByEmail trong UserRepository
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        // Ở đây bạn có thể thêm logic mã hóa mật khẩu (BCrypt)
        // trước khi lưu vào database nếu có Spring Security
        // Mã hóa mật khẩu
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public ProfileDTO getUserProfile(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Khong tim duoc user "+id));
        return ProfileDTO.builder()
                .phone(null)
                .school("Hoc Vien Cong Nghe Buu Chinh Vien Thong")
                .fullName(user.getFullName())
                .email(user.getEmail())
                .id(String.valueOf(id))
                .build();
    }

    @Override
    public void changePassword(Integer userId, PasswordChangeRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Khong tim duoc user "+userId));

        if(!user.getPassword().equals(request.getCurrentPassword())) {
            throw new RuntimeException("Mat khau hien tai khong chinh xac");
        }

        if(user.getPassword().equals(request.getNewPassword())) {
            throw new RuntimeException("Mat khau moi khong duoc trung voi mat khau cu");
        }

        user.setPassword(request.getNewPassword());
        userRepository.save(user);
    }


    @Override
    public Page<UserResponseDTO> getAllUsersForAdmin(Pageable pageable) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        return userRepository.findAll(pageable).map(user -> UserResponseDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .createdAt(user.getCreatedAt() != null ? user.getCreatedAt().format(formatter) : "N/A")
                .build());
    }
}