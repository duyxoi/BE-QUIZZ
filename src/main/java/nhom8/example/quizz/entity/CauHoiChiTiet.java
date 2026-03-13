package nhom8.example.quizz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CauHoiChiTiet") // Khớp với tên bảng trong SQL
public class CauHoiChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cauhoi_id") // Ánh xạ đúng tên cột trong SQL
    private Integer cauhoiId;

    @ManyToOne
    @JoinColumn(name = "dethi_id", nullable = false) // Khóa ngoại dethi_id
    private DeThi deThi;

    @Column(name = "noi_dung", columnDefinition = "TEXT", nullable = false)
    private String noiDung;

    @Column(name = "hinh_anh_url", length = 255)
    private String hinhAnhUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "loai_cau_hoi")
    private LoaiCauHoi loaiCauHoi;

    @Column(name = "phuong_an_a", columnDefinition = "TEXT")
    private String phuongAnA;

    @Column(name = "phuong_an_b", columnDefinition = "TEXT")
    private String phuongAnB;

    @Column(name = "phuong_an_c", columnDefinition = "TEXT")
    private String phuongAnC;

    @Column(name = "phuong_an_d", columnDefinition = "TEXT")
    private String phuongAnD;

    @Column(name = "dap_an_dung", length = 1)
    private String dapAnDung;

    @Column(name = "diem_cau_hoi")
    private Float diemCauHoi;

    public enum LoaiCauHoi {
        trac_nghiem,
        tu_luan
    }

    public CauHoiChiTiet(DeThi deThi, String noiDung, LoaiCauHoi loaiCauHoi, String phuongAnA, String phuongAnB, String phuongAnC, String phuongAnD, String dapAnDung, Float diemCauHoi) {
        this.deThi = deThi;
        this.noiDung = noiDung;
        this.loaiCauHoi = loaiCauHoi;
        this.phuongAnA = phuongAnA;
        this.phuongAnB = phuongAnB;
        this.phuongAnC = phuongAnC;
        this.phuongAnD = phuongAnD;
        this.dapAnDung = dapAnDung;
        this.diemCauHoi = diemCauHoi;
    }
}