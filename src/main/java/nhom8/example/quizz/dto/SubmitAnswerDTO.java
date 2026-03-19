package nhom8.example.quizz.dto;
import lombok.Data;

@Data
public class SubmitAnswerDTO {
    private Integer ketQuaId;
    private Integer cauHoiId;
    private String dapAnChon; // "A", "B", "C", "D" hoặc nội dung tự luận
}