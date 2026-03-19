package nhom8.example.quizz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor  // <--- THIẾU CÁI NÀY LÀJackson BÁO LỖI
@AllArgsConstructor // Nên có để hỗ trợ tạo object nhanh trong code
public class PasswordChangeRequest {
    private String currentPassword;
    private String newPassword;
}