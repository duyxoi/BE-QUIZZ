package nhom8.example.quizz.service;

import nhom8.example.quizz.entity.CauHoiChiTiet;
import nhom8.example.quizz.entity.DeThi;

import java.util.List;

public interface CauHoiChiTietService {
    List<CauHoiChiTiet> getAllCauHoiChiTietByDeThi(DeThi deThi);
    void deleteCauHoiChiTietById(CauHoiChiTiet cauHoiChiTiet);
    void createCauHoiChiTiet(CauHoiChiTiet cauHoiChiTiet);
    void updateCauHoiChiTiet(CauHoiChiTiet cauHoiChiTiet);
}
