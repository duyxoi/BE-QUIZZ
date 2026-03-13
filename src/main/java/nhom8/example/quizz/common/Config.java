package nhom8.example.quizz.common;

import nhom8.example.quizz.entity.CauHoiChiTiet;
import nhom8.example.quizz.entity.DeThi;
import nhom8.example.quizz.entity.GiaoVien;
import nhom8.example.quizz.entity.SinhVien;
import nhom8.example.quizz.service.CauHoiChiTietService;
import nhom8.example.quizz.service.DeThiService;
import nhom8.example.quizz.service.GiaoVienService;
import nhom8.example.quizz.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class Config {

    @Autowired
    private SinhVienService sinhVienService;

    @Autowired
    private DeThiService deThiService;

    @Autowired
    private CauHoiChiTietService cauHoiChiTietService;

    @Autowired
    private GiaoVienService giaoVienService;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args ->  {
            System.out.println("Hello World");

            GiaoVien gv = giaoVienService.getGiaoVienById(1).orElseThrow();

            DeThi deThi = new DeThi("GDCD",gv, DeThi.TrangThai.hidden);

            deThi.getListCauHoiChiTiet().add(new CauHoiChiTiet(deThi,"Tuoi lon la gi", CauHoiChiTiet.LoaiCauHoi.trac_nghiem,"12","13","14","15","A",(float)1));
            deThi.setThoiGianPhut(90);
            deThiService.createDeThi(deThi);

        };
    }
}
