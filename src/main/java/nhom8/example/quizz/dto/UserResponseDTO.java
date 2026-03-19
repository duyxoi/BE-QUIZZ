package nhom8.example.quizz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Integer userId;
    private String username;
    private String fullName;
    private String email;
    private String role;
    private String createdAt; // Format lại thành chuỗi DD/MM/YYYY cho dễ đọc
}