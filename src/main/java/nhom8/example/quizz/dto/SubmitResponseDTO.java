package nhom8.example.quizz.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubmitResponseDTO {
    private Integer ketQuaId;
    private Float tongDiem;
    private Integer soCauDung;
    private Integer tongSoCau;
    private String thoiGianNop;
}