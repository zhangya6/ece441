package com.numberplate.numberplateserver.repository;

import com.numberplate.numberplateserver.entity.Plate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlateRepository extends JpaRepository<Plate,Integer> {
    List<Plate> findByNumber(String number);
}
