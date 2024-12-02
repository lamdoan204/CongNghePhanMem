package com.Project.CongNghePhanMem.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Project.CongNghePhanMem.Entity.Brand;


@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    public Brand findByBrandId(Integer brandId);
    public Brand findByBrand(String brand);
    @Query("SELECT b FROM Brand b WHERE b NOT IN (SELECT d.brand FROM Department d)")
    List<Brand> findBrandsWithoutDepartment();
}
