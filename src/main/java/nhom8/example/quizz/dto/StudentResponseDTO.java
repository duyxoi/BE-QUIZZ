package nhom8.example.quizz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDTO {
    private Integer studentId;
    private String fullName;
    private String email;
    private String username;
    private String ngayBatDauHoc;
}

