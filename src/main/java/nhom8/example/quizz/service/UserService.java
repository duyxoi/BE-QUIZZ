package nhom8.example.quizz.service;

import nhom8.example.quizz.dto.PasswordChangeRequest;
import nhom8.example.quizz.dto.ProfileDTO;
import nhom8.example.quizz.dto.UserResponseDTO;
import nhom8.example.quizz.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    // Lấy danh sách tất cả người dùng
    List<User> getAllUsers();

    // Tìm người dùng theo ID
    Optional<User> getUserById(Integer id);

    // Tìm theo username (Cực kỳ quan trọng để xử lý Đăng nhập)
    Optional<User> getUserByUsername(String username);

    // Tìm theo email
    Optional<User> getUserByEmail(String email);

    // Tạo tài khoản mới hoặc cập nhật thông tin
    User saveUser(User user);

    // Xóa tài khoản
    void deleteUser(Integer id);

    // Kiểm tra sự tồn tại của username
    boolean existsByUsername(String username);

    ProfileDTO  getUserProfile(Integer id);

    void changePassword(Integer userId, PasswordChangeRequest request);

    Page<UserResponseDTO> getAllUsersForAdmin(Pageable pageable);

}