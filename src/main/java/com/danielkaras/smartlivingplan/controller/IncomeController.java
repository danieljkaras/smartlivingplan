package com.danielkaras.smartlivingplan.controller;

import com.danielkaras.smartlivingplan.exception.ResourceNotFoundException;
import com.danielkaras.smartlivingplan.model.Income;
import com.danielkaras.smartlivingplan.repository.IncomeRepository;
import com.danielkaras.smartlivingplan.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class IncomeController {

    private final static Logger logger = LoggerFactory.getLogger(IncomeController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IncomeRepository incomeRepository;

    @PostMapping("/income/{userId}")
    public Income createNewIncomeForUser(@Valid @RequestBody Income income, @PathVariable Long userId) {
        logger.info("adding new income for user {}", userId);
        checkIfUserExist(userId);

        return userRepository.findById(userId)
                .map(user -> {
                    income.setUser(user);
                    return incomeRepository.save(income);
                }).orElseThrow(() -> new ResourceNotFoundException("User with id: " + userId + " is not found!!!"));
    }

    private void checkIfUserExist(@PathVariable Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("sorry. user with id {} is not exist in our system. please check correct info");
        }
    }
}
