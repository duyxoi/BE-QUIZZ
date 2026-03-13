package nhom8.example.quizz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ChiTietBaiLam") // Tên bảng trong SQL
public class ChiTietBaiLam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chitiet_id") // Ánh xạ đúng chitiet_id
    private Integer chitietId;

    @ManyToOne
    @JoinColumn(name = "ketqua_id", nullable = false) // Khóa ngoại kết nối bảng KetQuaThi
    private KetQuaThi ketQuaThi;

    @ManyToOne
    @JoinColumn(name = "cauhoi_id", nullable = false) // Khóa ngoại kết nối bảng CauHoiChiTiet
    private CauHoiChiTiet cauHoi;

    @Column(name = "dap_an_chon", length = 1)
    private String dapAnChon;

    @Column(name = "noi_dung_tra_loi", columnDefinition = "TEXT")
    private String noiDungTraLoi;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Column(name = "diem_dat_duoc")
    private Float diemDatDuoc;
}