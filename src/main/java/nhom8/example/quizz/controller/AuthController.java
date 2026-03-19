package nhom8.example.quizz.controller;

import lombok.RequiredArgsConstructor;
import nhom8.example.quizz.common.JwtTokenProvider;
import nhom8.example.quizz.dto.LoginRequest;
import nhom8.example.quizz.dto.TokenResponse;
import nhom8.example.quizz.dto.UserResponseDTO;
import nhom8.example.quizz.entity.User;
import nhom8.example.quizz.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // 1. Xác thực tài khoản / mật khẩu
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // 2. [QUAN TRỌNG] Nạp vào hệ thống để tránh lỗi NullPointerException gây ra 403 ảo
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Tạo Token
        String jwt = tokenProvider.generateAccessToken(authentication);

        // 4. In Log kiểm tra (Lấy trực tiếp từ authentication cho an toàn)
        System.out.println(">>> Quyền hiện tại lúc Login: " + authentication.getAuthorities());

        return ResponseEntity.ok(TokenResponse.builder()
                .accessToken(jwt)
                .username(request.getUsername())
                .role(authentication.getAuthorities().iterator().next().getAuthority())
                .build());
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(UserResponseDTO.fromEntity(user));
    }
}