package com.danielkaras.smartlivingplan.controller;

import com.danielkaras.smartlivingplan.exception.ResourceNotFoundException;
import com.danielkaras.smartlivingplan.model.Category;
import com.danielkaras.smartlivingplan.repository.CategoryRepository;
import com.danielkaras.smartlivingplan.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CategoryControllerOld {

    private static final Logger logger = LoggerFactory.getLogger(CategoryControllerOld.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/category/{userId}")
    public Category createNewCategory(@Valid @RequestBody Category category, @PathVariable Long userId) {
        logger.info("adding new category: {} for user {}", category.getName(), userId);
        checkIfUserExist(userId);

        return userRepository.findById(userId)
                .map(user -> {
                    category.setUsers(Collections.singletonList(user));
                    category.setPayment(category.isPayment());
                    category.setIncome(category.isIncome());
                    logger.info("Category added to repository: {} for user: {}", category.toString(), category.getUsers());
                    return categoryRepository.save(category);
                }).orElseThrow(() -> new ResourceNotFoundException("User with id: " + userId + " is not found!!!"));
    }

    @GetMapping("/category/{userId}")
    @Transactional
    public List<Category> findCategoriesForUser(@PathVariable Long userId) {
        logger.info("searching categories for user with id: {}", userId);

        checkIfUserExist(userId);

        List<Category> categories = userRepository.findByIdEquals(userId).getCategories();
        logger.info("Categories found for user: {} and cateogries: {}", userId,
                categories.stream().map(Category::getName).collect(Collectors.toList()));

        return categories;
    }

    private void checkIfUserExist(Long userId) {
        if (!(userRepository.existsById(userId))) {
            throw new ResourceNotFoundException("Sorry. User with id: " + userId + " is not exist in our system. Please check correct info!!!" );
        }
    }
}
