package nhom8.example.quizz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SinhVien") // Khớp chính xác tên bảng PascalCase
public class SinhVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id") // Ánh xạ đúng student_id
    private Integer studentId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false) // Khóa ngoại user_id (NOT NULL)
    private User user;

    // Sử dụng mappedBy = "sinhVien" để trỏ tới biến sinhVien trong class KetQuaThi
    @OneToMany(mappedBy = "sinhVien", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<KetQuaThi> listKetQuaThi;

    public String toString(){
        return studentId +" "+user.getFullName()+" "+user.getEmail();
    }

}