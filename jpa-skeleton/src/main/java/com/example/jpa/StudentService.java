package com.example.jpa;

import com.example.jpa.entity.Instructor;
import com.example.jpa.entity.Student;
import com.example.jpa.repo.InstructorRepository;
import com.example.jpa.repo.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;

    public void create(
            String name,
            Integer age,
            String phone,
            String email,
            // 지도교수의 PK를 받아온다.
            Long advisorId
    ) {
        // 주어진 정보로 새로운 Student 객체를 만든다.
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setPhone(phone);
        student.setEmail(email);
        // 지도교수님을 찾는다.
        Optional<Instructor> optionalInstructor
          = instructorRepository.findById(advisorId);
        // 학생에 지도교수를 할당한다.
        student.setAdvisor(optionalInstructor.orElse(null));
        // repository의 save 메서드를 호출한다.
        studentRepository.save(student);
    }

    public Student readStudent(Long id) {
        Optional<Student> optionalStudent
          = studentRepository.findById(id);

        // 실제 데이터가 있으면 해당 데이터를,
        return optionalStudent
          // 없으면 null을 반환한다.
          .orElse(null);
    }

    public List<Student> readStudentAll() {
        // 값이 잘 나오는지 확인
/*        List<Student> students = studentRepository.findAll();
        for (Student student: students) {
            System.out.println(student.toString());
        }*/
        return studentRepository.findAll();
    }

    public void update(
            // 수정할 데이터의 PK가 무엇인지
            Long id,
            // 나머지 4개의 데이터는 수정할 데이터를 의미
            String name,
            Integer age,
            String phone,
            String email
    ) {
        // 1. 업데이트 할 대상 데이터를 찾고, (read)
        Student target = readStudent(id);
        // 2. 데이터의 내용을 전달받은 내용으로 갱신하고, (set)
        target.setName(name);
        target.setAge(age);
        target.setPhone(phone);
        target.setEmail(email);
        // 3. repository를 이용해 저장한다. (create)
        studentRepository.save(target);
    }

    public void delete(Long id) {
        studentRepository.deleteById(id);
    }

}
