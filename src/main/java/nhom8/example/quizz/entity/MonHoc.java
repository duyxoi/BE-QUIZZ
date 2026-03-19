package nhom8.example.quizz.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MonHoc")
public class MonHoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "monhoc_id")
    private Integer monhocId;

    @Column(name = "ten_mon", nullable = false, columnDefinition = "NVARCHAR(100)")
    private String tenMon;

    @Column(name = "mo_ta", columnDefinition = "TEXT")
    private String moTa;

    // Quan hệ 1-N: Một môn học có nhiều đề thi
    @OneToMany(mappedBy = "monHoc", fetch = FetchType.LAZY)
    @JsonIgnore // Jackson sẽ bỏ qua trường này, không bao giờ bị lặp
    private List<DeThi> deThiList;
}