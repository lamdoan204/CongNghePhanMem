package com.Project.CongNghePhanMem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Project.CongNghePhanMem.Entity.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
 
}
