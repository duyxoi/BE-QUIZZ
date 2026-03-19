package nhom8.example.quizz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer"; // Thường mặc định là Bearer

    // Thời gian hết hạn (tính bằng giây) để Frontend biết khi nào cần refresh
    private Long expiresIn;

    // Trả về luôn Role để Frontend điều hướng trang (Admin/User) cho nhanh
    private String role;

    private String username;
}