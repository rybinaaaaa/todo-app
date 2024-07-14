package com.rybina.TodoApplication.controller.jsp;

import com.rybina.TodoApplication.dto.userDto.UserCreateDto;
import com.rybina.TodoApplication.dto.userDto.UserDtoMapper;
import com.rybina.TodoApplication.dto.userDto.UserCredentials;
import com.rybina.TodoApplication.dto.userDto.UserReadDto;
import com.rybina.TodoApplication.exception.repositoryException.NotSavedException;
import com.rybina.TodoApplication.model.User;
import com.rybina.TodoApplication.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@SessionAttributes({"user"})
@RequiredArgsConstructor
@RequestMapping("users")
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserDtoMapper userDtoMapper;

    @GetMapping("/register")
    public String register(@ModelAttribute("userCreate") UserCreateDto userCreateDto) {
        return "register";
    }

    @PostMapping("/register")
    public String register(@Validated @ModelAttribute("userCreate") UserCreateDto userCreateDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        try {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("userCreate", userCreateDto);
                return "register";
            }

            User user = userDtoMapper.fromCreateDto(userCreateDto);
            user = userService.save(user);
            UserReadDto userReadDto = userDtoMapper.toReadDto(user);

            model.addAttribute("user", userReadDto);

            return "redirect:/todos";
        } catch (NotSavedException e) {
            return "redirect:/error";
        }
    }

    @GetMapping("/login")
    public String getLogin(@ModelAttribute("userCredentials") UserCredentials userCredentials) {
        return "login";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("userCredentials") UserCredentials userCredentials, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userCredentials", userCredentials);
            return "login";
        }

        return userService.findByUsernameAndPassword(userCredentials.getUsername(), userCredentials.getPassword()).map(user -> {
            UserReadDto userReadDto = userDtoMapper.toReadDto(user);
            model.addAttribute("user", userReadDto);

            log.info("{} has been logged in", userReadDto.getUsername());

            return "redirect:/todos";
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("userCredentials", userCredentials);
            redirectAttributes.addFlashAttribute("loginError", true);
            return "redirect:/users/login";
        });
    }

    @GetMapping("/exit")
    public String exit(Model model) {
        model.addAttribute("user", null);
        return "redirect:/users/login";
    }

    @GetMapping
    public String get(@SessionAttribute(value = "user", required = false) UserReadDto userReadDto, Model model) {
        return Optional.ofNullable(userReadDto).map(user -> {
            model.addAttribute("user", user);
            return "user";
        }).orElse("redirect:users/login");
    }

    @GetMapping({"/{id}/edit"})
    public String getEditForm(@SessionAttribute(value = "user", required = false) UserReadDto userReadDto, Model model) {
        return Optional.ofNullable(userReadDto).map(user -> {
            model.addAttribute("user", user);
            return "edit_user";
        }).orElse("redirect:users/login");
    }

    @PutMapping("{id}")
    public String edit(@PathVariable Integer id, @ModelAttribute("user") UserReadDto userReadDto, Model model) {
        User user = userService.findById(id).orElseThrow(IllegalArgumentException::new);
        user = userDtoMapper.copyFromReadEditDto(userReadDto, user);

        try {
            userService.save(user);
            return "redirect:/users";
        } catch (NotSavedException e) {
            return "redirect:/users/" + id + "/edit";
        }
    }
}
