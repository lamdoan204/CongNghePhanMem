package com.Project.CongNghePhanMem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.CongNghePhanMem.Entity.Brand;
import com.Project.CongNghePhanMem.Repository.BrandRepository;

@Service
public class BrandService {

    @Autowired
    BrandRepository brandRepository;

    public Brand getBrandByBrandId(int id){
        return brandRepository.findByBrandId(id);
    }

    public Brand getBrandbyBrand(String brand){
        return brandRepository.findByBrand(brand);
    }
}
