package com.danielkaras.smartlivingplan.repository;

import com.danielkaras.smartlivingplan.model.Category;
import com.danielkaras.smartlivingplan.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
