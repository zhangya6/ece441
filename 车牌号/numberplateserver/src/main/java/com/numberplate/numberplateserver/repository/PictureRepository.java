package com.numberplate.numberplateserver.repository;

import com.numberplate.numberplateserver.entity.Picture;
import com.numberplate.numberplateserver.entity.Plate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PictureRepository extends JpaRepository<Picture,Integer> {
    List<Picture> findByUrl(String url);
}
