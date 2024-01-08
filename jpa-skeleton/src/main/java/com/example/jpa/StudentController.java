package com.example.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;
    private final InstructorService instructorService;


    @GetMapping("create-view")
    public String createView(Model model) {
      // instructorService.readInstructorAll()을 model에 넣은 이유
      // 학생을 만들때 지도교수를 선택하기 위해 instructor 리스트를 전달하기 위함
        model.addAttribute("instructors", instructorService.readInstructorAll());
        return "student/create";
    }

    @PostMapping("create")
    public String create(
            @RequestParam("name") String name,
            @RequestParam("age") Integer age,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            @RequestParam("advisor-id") Long advisorId // RequestParam의 인자는 name속성의 값
    ) {
        studentService.create(name, age, phone, email, advisorId);
        return "redirect:/student";
    }

    @GetMapping
    public String readAll(Model model) {
        model.addAttribute("students", studentService.readStudentAll());
        return "student/home";
    }

    @GetMapping("{id}")
    public String readOne(
            @PathVariable("id") Long id,
            Model model
    ) {
        model.addAttribute("student", studentService.readStudent(id));
        return "student/read";
    }

    @GetMapping("{id}/update-view")
    public String updateView(
            @PathVariable("id")
            Long id,
            Model model
    ) {
        model.addAttribute("student", studentService.readStudent(id));
        return "student/update";
    }

    @PostMapping("{id}/update")
    public String update(
            @PathVariable("id")
            Long id,
            @RequestParam("name")
            String name,
            @RequestParam("age")
            Integer age,
            @RequestParam("phone")
            String phone,
            @RequestParam("email")
            String email
    ) {
        studentService.update(
                id, name, age, phone, email);
        return String.format("redirect:/student/%d", id);
    }

    @PostMapping("{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        studentService.delete(id);
        return "redirect:/student";
    }}
