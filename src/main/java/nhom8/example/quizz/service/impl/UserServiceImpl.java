package nhom8.example.quizz.service.impl;

import lombok.RequiredArgsConstructor;
import nhom8.example.quizz.entity.User;
import nhom8.example.quizz.repository.UserRepository;
import nhom8.example.quizz.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

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
}