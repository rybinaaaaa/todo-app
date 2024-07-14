package com.rybina.TodoApplication.controller.jsp;

import com.rybina.TodoApplication.dto.pagedResponse.PagedRead;
import com.rybina.TodoApplication.dto.pagedResponse.PagedDtoMapper;
import com.rybina.TodoApplication.dto.todoDto.TodoCreateDto;
import com.rybina.TodoApplication.dto.todoDto.TodoDtoMapper;
import com.rybina.TodoApplication.dto.todoDto.TodoReadEditDto;
import com.rybina.TodoApplication.dto.userDto.UserReadDto;
import com.rybina.TodoApplication.exception.repositoryException.NotSavedException;
import com.rybina.TodoApplication.model.Todo;
import com.rybina.TodoApplication.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@SessionAttributes("user")
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;
    private final PagedDtoMapper<Todo, Todo> todoToListResponseMapper;
    private final TodoDtoMapper todoDtoMapper;

    @ModelAttribute("user")
    public UserReadDto getDefaultUser() {
        return null;
    }

    @GetMapping
    public String getTodos(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @SessionAttribute(value = "user", required = false) UserReadDto user, Model model) {

        return Optional.ofNullable(user).map((usr) -> {
                    PageRequest pageable = PageRequest.of(page, size);
                    Page<Todo> todos = todoService.findByUserId(usr.getId(), pageable);

                    PagedRead<Todo> todoDto = todoToListResponseMapper.mapToPagedRead(todos);

                    model.addAttribute("todos", todoDto);

                    return "todos";
                })
                .orElse("redirect:users/login");
    }

    @GetMapping("/{id}")
    public String getTodoById(@PathVariable("id") Integer id, Model model) {

        return todoService.findById(id)
                .map(todo -> {
                    TodoReadEditDto todoReadEditDto = todoDtoMapper.mapToReadDto(todo);
                    model.addAttribute("todoRead", todoReadEditDto);
                    return "todo";
                })
                .orElse("redirect:users/login");
    }

    @GetMapping("/add")
    public String addTodoForm(@ModelAttribute("todoCreate") TodoCreateDto todoDto) {
        return "create_todo";
    }

    @GetMapping("{id}/edit")
    public String editTodoForm(@PathVariable Integer id, Model model) {
        return todoService.findById(id).map(todo -> {
            TodoReadEditDto todoReadEditDto = todoDtoMapper.mapToReadDto(todo);
            model.addAttribute("todoRead", todoReadEditDto);

            return "edit_todo";
        }).orElse("redirect:/error");
    }

    @PutMapping("/{id}")
    public String editTodoForm(@PathVariable("id") Integer id, @ModelAttribute("todoRead") TodoReadEditDto todoDto) throws NotSavedException {

        Todo todo = todoService.findById(id).orElseThrow(IllegalArgumentException::new);
        todo = todoDtoMapper.copyFromReadEditDto(todoDto, todo);

        try {
            todoService.save(todo);
            return "redirect:/todos/" + id;
        } catch (NotSavedException e) {
            return "redirect:/error";
        }
    }

    @PostMapping
    public String addTodo(@ModelAttribute("user") UserReadDto userReadDto,@ModelAttribute("todoCreate") TodoCreateDto todoDto, Model model) {

        Todo todo = todoDtoMapper.mapFromCreateDto(todoDto, userReadDto.getId());

        try {
            Todo savedTodo = todoService.save(todo);
            return "redirect:/todos/" + savedTodo.getId();
        } catch (NotSavedException e) {
            model.addAttribute("error", "Task could not be saved. Please try again.");
            return "create_todo";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteTodo(@PathVariable Integer id) {

        todoService.deleteById(id);

        return "redirect:/todos";
    }
}
