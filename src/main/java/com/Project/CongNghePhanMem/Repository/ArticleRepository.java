package com.Project.CongNghePhanMem.Repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Project.CongNghePhanMem.Entity.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
	 Page<Article> findAll(Pageable pageable);
}
