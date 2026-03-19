package nhom8.example.quizz.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DeThi") // Khớp chính xác tên bảng trong SQL
public class DeThi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dethi_id") // Ánh xạ đúng dethi_id
    private Integer dethiId;

    @Column(name = "ten_de", nullable = false, columnDefinition = "NVARCHAR(200)")
    private String tenDe;

    @Column(name = "thoi_gian_phut", nullable = false)
    private Integer thoiGianPhut;

    @Column(name = "ngay_tao", updatable = false)
    private LocalDateTime ngayTao;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "teacher_id") // Khóa ngoại kết nối bảng GiaoVien
    private GiaoVien giaoVien;

    // Quan hệ 1-N với bảng CauHoiChiTiet
    @OneToMany(mappedBy = "deThi", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CauHoiChiTiet> listCauHoiChiTiet;

    @Enumerated(EnumType.STRING)
    @Column(name = "trang_thai")
    private TrangThai trangThai;

    public enum TrangThai {
        active,
        hidden
    }

    // Tự động gán thời gian tạo nếu không có
    @PrePersist
    protected void onCreate() {
        if (ngayTao == null) {
            ngayTao = LocalDateTime.now();
        }
        if (trangThai == null) {
            trangThai = TrangThai.active;
        }
    }

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "monhoc_id") // Tên cột trong Database
    private MonHoc monHoc;


    public DeThi(String tenDe, GiaoVien giaoVien, TrangThai trangThai) {
        this.tenDe = tenDe;
        this.giaoVien = giaoVien;
        this.trangThai = trangThai;
        this.listCauHoiChiTiet = new ArrayList<>();
    }

    public DeThi(String tenDe, GiaoVien giaoVien, List<CauHoiChiTiet> listCauHoiChiTiet, TrangThai trangThai) {
        this.tenDe = tenDe;
        this.giaoVien = giaoVien;
        this.listCauHoiChiTiet = listCauHoiChiTiet;
        this.trangThai = trangThai;
    }
}