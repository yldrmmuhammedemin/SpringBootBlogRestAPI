package com.yildirim.springbootrestapi.repository;

import com.yildirim.springbootrestapi.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
