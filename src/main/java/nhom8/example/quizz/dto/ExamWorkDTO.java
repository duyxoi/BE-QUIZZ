package nhom8.example.quizz.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExamWorkDTO {
    private Integer ketQuaId; // ID của lượt thi mới tạo
    private String tenDe;
    private Integer thoiGianPhut;
    private List<QuestionWorkDTO> listCauHoi;
}

