package com.danielkaras.smartlivingplan.controller;

import com.danielkaras.smartlivingplan.model.Category;
import com.danielkaras.smartlivingplan.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @RequestMapping(value = "/addCategory", method = RequestMethod.GET)
    public ModelAndView showAddCategoryForm() {
        ModelAndView category = new ModelAndView("addCategoryForm");
        category.addObject("category", new Category());
        return category;
    }

    @RequestMapping(value = "/addCategory", method = RequestMethod.POST)
    public ModelAndView createCategoryForUser(@Valid Category category,
                                              BindingResult bindingResult) {
        logger.info("Received form data. Category name: {}, isPaymentCategory: {}, isIncomeCategory: {}",
                category.getName(), category.isPayment(), category.isIncome());
        logger.info("Persist category data to DB.");
        categoryRepository.save(category);

        if (bindingResult.hasErrors()) {
            logger.error("There is an error with category data. Redirect to addCategory page !!!");
            return new ModelAndView("addCategoryForm");
        }
        logger.info("Adding category is finished with success. Redirect to: /showCategories");
        return new ModelAndView("redirect:/showCategories");
    }

    @RequestMapping(value = "/showCategories", method = RequestMethod.GET)
    public ModelAndView showAllMyCategories(HttpSession session) {
        ModelAndView allCategoriesView = new ModelAndView("showCategories");
        allCategoriesView.addObject("categories", categoryRepository.findAll());
        return allCategoriesView;
    }
}
