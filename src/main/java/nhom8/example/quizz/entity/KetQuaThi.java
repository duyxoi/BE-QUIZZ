package nhom8.example.quizz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KetQuaThi") // Khớp chính xác tên bảng trong SQL
public class KetQuaThi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ketqua_id") // Ánh xạ đúng ketqua_id
    private Integer ketquaId;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false) // Khóa ngoại student_id
    private SinhVien sinhVien;

    @ManyToOne
    @JoinColumn(name = "dethi_id", nullable = false) // Khóa ngoại dethi_id
    private DeThi deThi;

    @Column(name = "tong_diem")
    private Float tongDiem;

    @Column(name = "thoi_gian_bat_dau")
    private LocalDateTime thoiGianBatDau;

    @Column(name = "thoi_gian_nop", insertable = false, updatable = false)
    private LocalDateTime thoiGianNop;

    @Enumerated(EnumType.STRING)
    @Column(name = "trang_thai")
    private TrangThai trangThai;

    @Column(name = "nhan_xet_chung", columnDefinition = "TEXT")
    private String nhanXetChung;

    // Sử dụng mappedBy để trỏ tới biến ketQuaThi trong class ChiTietBaiLam
    @OneToMany(mappedBy = "ketQuaThi", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ChiTietBaiLam> listChiTietBaiLam;

    public enum TrangThai {
        dang_lam,
        da_nop,
        da_cham
    }

    @PrePersist
    protected void onCreate() {
        if (trangThai == null) {
            trangThai = TrangThai.da_nop;
        }
    }
}