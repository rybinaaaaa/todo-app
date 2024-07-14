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
import org.springframework.web.bind.annotation.*;

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
    public String register(@ModelAttribute("userCreate") UserCreateDto userCreateDto, Model model) {
        try {
            User user = userDtoMapper.fromCreateDto(userCreateDto);
            user = userService.save(user);
            UserReadDto userReadDto = userDtoMapper.toReadDto(user);

            model.addAttribute("user", userReadDto);

            return "redirect:/todos";
        } catch (NotSavedException e) {
            return "register";
        }
    }

    @GetMapping("/login")
    public String getLogin(@ModelAttribute("userCredentials") UserCredentials userCredentials) {
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("userCredentials") UserCredentials userCredentials, Model model) {
        try {
            User user = userService.findByUsernameAndPassword(userCredentials.getUsername(), userCredentials.getPassword());
            UserReadDto userReadDto = userDtoMapper.toReadDto(user);
            model.addAttribute("user", userReadDto);

            log.info("{} has been logged", userReadDto);
            return "redirect:/todos";
        } catch (IllegalArgumentException e) {
            log.error("Error due logging user");
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
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
