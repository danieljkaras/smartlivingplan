package com.danielkaras.smartlivingplan.controller;

import com.danielkaras.smartlivingplan.model.User;
import com.danielkaras.smartlivingplan.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView showLoginForm() {
        logger.info("creating login form, method: {}", RequestMethod.GET);
        ModelAndView login = new ModelAndView("login");
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView loginActiveUserAndRedirectToHomePage(User user) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", user);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User authUser = userService.findUserByEmail(auth.getName());
        logger.info(authUser.getEmail());

        modelAndView.setViewName("redirect:/home");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView showRegistrationForm() {
        logger.info("creating registration form. method: {}", RequestMethod.GET);
        ModelAndView registration = new ModelAndView("registration");
        registration.addObject("user", new User());
        return registration;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        logger.info("seraching if user with email: {} exist", user.getEmail());
        User existUser = userService.findUserByEmail(user.getEmail());

        if (existUser != null) {
            bindingResult.rejectValue("email", "error.user", "There is already a user with the provided email");
        }

        if (bindingResult.hasErrors()) {
            logger.warn("some errors occur during processing form data. redirect to: registration.html");
            return new ModelAndView("registration");
        } else {
            ModelAndView registerSuccess = new ModelAndView("registration");
            logger.info("data is processed with success. persist user with id: {} to database", user.getId());
            userService.saveUser(user);
            registerSuccess.addObject("successMessage", "User has been registered successfully");
            return registerSuccess;
        }
    }
}
