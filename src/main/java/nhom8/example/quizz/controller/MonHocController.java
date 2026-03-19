package nhom8.example.quizz.controller;

import nhom8.example.quizz.entity.DeThi;
import nhom8.example.quizz.entity.MonHoc;
import nhom8.example.quizz.service.MonHocService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/subject")
public class MonHocController {
    private final MonHocService monHocService;
    @Autowired
    public MonHocController(MonHocService monHocService) {
        this.monHocService = monHocService;
    }

    @GetMapping("/all")
    public List<MonHoc> getAllMonHoc() {
        return monHocService.getAllMonHoc();
    }

    @GetMapping("/exam/{id}")
    public List<DeThi> getDeThi(@PathVariable int id) {
        MonHoc mh = monHocService.getMonHocById(id).orElseThrow(() -> new RuntimeException("MonHoc id " + id + " not found"));
        return mh.getDeThiList();
    }




}
