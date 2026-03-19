package nhom8.example.quizz.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExamResponseDTO {
    private int id;
    private String title;
    private SubjectDTO subject;
    private String description;
    private Integer durationMinutes;
    private String status;
    private Integer questionCount;
}

