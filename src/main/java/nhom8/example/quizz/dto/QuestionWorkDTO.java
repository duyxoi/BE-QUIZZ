package nhom8.example.quizz.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionWorkDTO {
    private Integer cauHoiId;
    private String noiDung;
    private String hinhAnhUrl;
    private String phuongAnA;
    private String phuongAnB;
    private String phuongAnC;
    private String phuongAnD;
    private String loaiCauHoi;
}
