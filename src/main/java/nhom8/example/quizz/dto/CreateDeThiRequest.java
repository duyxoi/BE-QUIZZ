package nhom8.example.quizz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDeThiRequest {
    private String tenDe;
    private Integer thoiGianPhut;
    private Integer teacherId;
    private Integer monhocId;
    private List<CauHoiDTO> questions;

    @Data
    public static class CauHoiDTO {
        private String noi_dung;
        private String phuong_an_a;
        private String phuong_an_b;
        private String phuong_an_c;
        private String phuong_an_d;
        private String dap_an_dung;
        private Float diem_cau_hoi;
        private String loai_cau_hoi; // "trac_nghiem" hoặc "tu_luan"
    }
}