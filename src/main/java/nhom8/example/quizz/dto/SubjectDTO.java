package nhom8.example.quizz.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubjectDTO {
    private int id;
    private String name;
}
