package nhom8.example.quizz.dto;

import lombok.Data;

@Data
public class AssignStudentRequest {
    private Integer teacherId;
    private Integer studentId;
}
