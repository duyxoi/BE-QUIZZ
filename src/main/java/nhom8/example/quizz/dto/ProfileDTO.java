package nhom8.example.quizz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {
    private String id; // Map từ userId (đổi format thành String nếu muốn giống usr_01)
    private String fullName;
    private String email;
    private String phone;
    private String school;
    private String avatarUrl;
}