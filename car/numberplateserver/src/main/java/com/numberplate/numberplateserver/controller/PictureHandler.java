package com.numberplate.numberplateserver.controller;

import com.numberplate.numberplateserver.entity.Picture;
import com.numberplate.numberplateserver.entity.Plate;
import com.numberplate.numberplateserver.repository.PictureRepository;
import com.numberplate.numberplateserver.repository.PlateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/picture")
public class PictureHandler {
    @Autowired
    private PictureRepository pictureRepository;

    @GetMapping("/findAll/{page}/{size}")
    public Page<Picture> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable = PageRequest.of(page-1,size);
        return pictureRepository.findAll(pageable);
    }

    @PostMapping("/save")
    public String save(@RequestBody Picture picture){
//        List<Picture> result = pictureRepository.findByUrl(picture.getUrl());
//        if(result.size()!=0) return "图片地址已存在";
        Picture res = pictureRepository.save(picture);
        if (res != null) {
            return "success";
        }else{
            return "error";
        }
    }

    @PutMapping("/update")
    public String update(@RequestBody Picture picture){
        Picture result = pictureRepository.save(picture);
        if(result !=null){
            return "success";
        }else{
            return "error";
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id") Integer id){
        pictureRepository.deleteById(id);
    }
}
