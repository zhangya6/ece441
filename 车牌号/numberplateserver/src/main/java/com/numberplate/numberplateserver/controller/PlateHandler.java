package com.numberplate.numberplateserver.controller;

import com.numberplate.numberplateserver.entity.Plate;
import com.numberplate.numberplateserver.repository.PlateRepository;
import com.numberplate.numberplateserver.service.IMailService;
import com.numberplate.numberplateserver.service.IMailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/plate")
public class PlateHandler {
    @Autowired
    private PlateRepository plateRepository;

    @Autowired
    private IMailServiceImpl iMailService;

    private final static Logger logger = LoggerFactory.getLogger(PlateHandler.class);

    @GetMapping("/findAll/{page}/{size}")
    public Page<Plate> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable = PageRequest.of(page-1,size);
        return plateRepository.findAll(pageable);
    }

    @PostMapping("/save")
    public String save(@RequestBody Plate plate){
//        Plate result = plateRepository.save(plate);
        List<Plate> result = plateRepository.findByNumber(plate.getNumber());
        if(result.size() !=0) {
            Plate up_plate = result.get(0);
            int newCount = up_plate.getCount() + 1;
            up_plate.setCount(newCount);
            Plate res = plateRepository.save(up_plate);
            if (result != null) {
                return "success";
            }else{
                return "error";
            }
        } else {
            plate.setCount(1);
            Plate res = plateRepository.save(plate);
            if (result != null) {
                return "success";
            }else{
                return "error";
            }
        }
    }

    @PutMapping("/update")
    public String update(@RequestBody Plate plate){
        Plate result = plateRepository.save(plate);
        if(result !=null){
            return "success";
        }else{
            return "error";
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id") Integer id){
        plateRepository.deleteById(id);
    }

    @PostMapping("/email")
    public String sendSimpleMailMessage(@RequestBody Map<String, String> email){
        String TO = email.get("email");
        String SUBJECT = email.get("subject");
        String CONTENT = email.get("message");
        iMailService.sendSimpleMail(TO, SUBJECT, CONTENT);
        return "success";
    }
}
