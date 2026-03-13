package nhom8.example.quizz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "QuanLyGiaoVienHS") // Khớp chính xác tên bảng PascalCase
public class QuanLyGiaoVienHS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false) // Khóa ngoại teacher_id (NOT NULL)
    private GiaoVien giaoVien;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false) // Khóa ngoại student_id (NOT NULL)
    private SinhVien sinhVien;

    @Column(name = "ngay_bat_dau_hoc") // Map chính xác với cột DATE trong SQL
    private LocalDate ngayBatDauHoc;
}