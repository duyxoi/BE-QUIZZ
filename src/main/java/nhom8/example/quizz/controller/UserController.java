package nhom8.example.quizz.controller;


import nhom8.example.quizz.dto.PasswordChangeRequest;
import nhom8.example.quizz.dto.ProfileDTO;
import nhom8.example.quizz.entity.User;
import nhom8.example.quizz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/profile/{id}")
    public ResponseEntity<?> getProfile(@PathVariable Integer id) {
        return  ResponseEntity.ok(userService.getUserProfile(id));
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable Integer id, @RequestBody ProfileDTO profileDTO) {
        User user = userService.getUserById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setEmail(profileDTO.getEmail());
        user.setFullName(profileDTO.getFullName());

        userService.saveUser(user);
        return ResponseEntity.ok("Da update thanh cong");
    }

    @PostMapping("/change-password/{id}")
    public ResponseEntity<String> changePassword(@PathVariable("id") Integer currentUserId,@RequestBody PasswordChangeRequest request) {
        try {
            // Giả sử lấy userId = 1 từ hệ thống (khi chưa có Security)

            userService.changePassword(currentUserId, request);
            return ResponseEntity.ok("Đổi mật khẩu thành công!");

        } catch (RuntimeException e) {
            // Trả về lỗi 400 kèm thông báo lỗi cụ thể
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
