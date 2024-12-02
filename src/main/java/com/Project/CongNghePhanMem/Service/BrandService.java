package com.Project.CongNghePhanMem.Service;

import java.util.List;

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
    
    public List<Brand> getAllBrand(){
    	return brandRepository.findAll();
    }

    public void addBrand(String brand){
        Brand x = new Brand();
        x.setBrand(brand);
        brandRepository.save(x);
    }

    public void deleteBrand(int  brandId){
        Brand x =  brandRepository.findByBrandId(brandId);
        brandRepository.delete(x);
    }

    public List<Brand>  getListBrandWithouD(){
        return brandRepository.findBrandsWithoutDepartment();
    }

}
