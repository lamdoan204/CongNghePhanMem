package com.Project.CongNghePhanMem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Project.CongNghePhanMem.Entity.Brand;


@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    public Brand findByBrandId(Integer brandId);
    public Brand findByBrand(String brand);
}