package nhom8.example.quizz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nhom8.example.quizz.entity.User;

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


    public static UserResponseDTO fromEntity(User user) {
        return UserResponseDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name()) // Chuyển Enum sang String
                .build();
    }
}