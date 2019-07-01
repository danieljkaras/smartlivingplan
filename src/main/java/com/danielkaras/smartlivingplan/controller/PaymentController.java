package com.danielkaras.smartlivingplan.controller;

import com.danielkaras.smartlivingplan.exception.ResourceNotFoundException;
import com.danielkaras.smartlivingplan.model.Category;
import com.danielkaras.smartlivingplan.model.Payment;
import com.danielkaras.smartlivingplan.repository.CategoryRepository;
import com.danielkaras.smartlivingplan.repository.PaymentRepository;
import com.danielkaras.smartlivingplan.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class PaymentController {

    private final static Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/payment/{userId}")
    public Payment createNewExpenseForUser(@Valid @RequestBody Payment payment, @PathVariable Long userId) {
        checkIfUserExist(userId);
        logger.info("adding new payment for user with id {}", userId);

        Optional<Category> first = userRepository.findByIdEquals(userId)
                .getCategories()
                .stream()
                .filter(category -> category.getId().equals(payment.getCategory().getId()))
                .findFirst();

        payment.setCategory(first.orElse(null));
        payment.setUsers(Collections.singletonList(userRepository.findByIdEquals(userId)));

        logger.info("payment data saved to database: {}", payment.toString());
        return paymentRepository.save(payment);
    }

    @RequestMapping(value = "/payment/{userId}",
            produces = "application/json",
            method = {RequestMethod.PUT})
    public Payment editPaymentForUser(@Valid @RequestBody Payment payment, @PathVariable Long userId) {
        logger.info("updating entry of expense for user {} and idPayment {}", userId, payment.getId());
        checkIfUserExist(userId);

        return paymentRepository.findById(payment.getId())
                .map(existingPayment -> {
                    return paymentRepository.save(updatePaymentRecord(existingPayment, payment));
                }).orElseThrow(() -> new ResourceNotFoundException("Expense with id: " + payment.getId() + " is not found!!!"));
    }

    @GetMapping("/payment/{userId}/all")
    public List<Payment> findAllPaymentsForUser(@PathVariable Long userId) {
        List<Payment> payments = userRepository.findByIdEquals(userId).getPayments();

        logger.info("found {} payments for user with id: {}", payments.size(), userId);
        return payments;
    }

    private Payment updatePaymentRecord(Payment existingPayment, Payment updatePayment) {
        Optional<Category> updateCategory = categoryRepository.findById(updatePayment.getCategory().getId());

        existingPayment.setPaymentDate(updatePayment.getPaymentDate());
        existingPayment.setCategory(updateCategory.isPresent() ? updateCategory.get() : existingPayment.getCategory());
        existingPayment.setDescription(updatePayment.getDescription());
        existingPayment.setValue(updatePayment.getValue());
        return existingPayment;
    }

    private void checkIfUserExist(@PathVariable Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("sorry. user with id {} is not exist in our system. please check correct info");
        }
    }
}
