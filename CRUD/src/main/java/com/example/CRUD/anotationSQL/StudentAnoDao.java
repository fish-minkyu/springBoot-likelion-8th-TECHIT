package com.example.CRUD.anotationSQL;

import com.example.CRUD.model.StudentDto;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

// 데이터 통신을 담당하는 클래스임을 Spring에 알려줌
@Repository
public class StudentAnoDao {
  // MyBatis와 데이터베이스를 연결해주는 객체
  private final SqlSessionFactory sessionFactory; // sessionFactory은 sqlsession을 만들기 위한 공장이다.

  // 생성자 DI
  // Spring Boot안에 만들어진 SqlSessionFactory Bean이 자동으로 주입된다.
  public StudentAnoDao(SqlSessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  // 데이터베이스에서 학생 데이터를 전부 불러오는 메서드
  public List<StudentDto> readStudentAll() {
    // SqlSession은 MyBatis와 데이터베이스가 연결되었다는 것을 상징하는 객체
    try(SqlSession session = sessionFactory.openSession()) {
      // Mapper 인터페이스를 가져온다.
      StudentMapper mapper = session.getMapper(StudentMapper.class); // .class가 해당 클래스의 정의를 가져오는 것이다.
      return mapper.selectStudentAll();
    }
  }

  // StudentDto를 받아 학생을 데이터베이스에 추가하는 메서드
  public void createStudent(StudentDto dto) {
    try(SqlSession session = sessionFactory.openSession()) {
      StudentMapper mapper = session.getMapper(StudentMapper.class);
      mapper.insertStudent(dto);
    }
  }

  // id를 Long으로 받아 데이터베이스에서 id가 같은 줄을 반환하는 메서드
  public StudentDto readStudent(Long id) {
    try(SqlSession session = sessionFactory.openSession()) {
      StudentMapper mapper = session.getMapper(StudentMapper.class);
      return mapper.selectStudent(id);
    }
  }

  // TODO StudentMapper를 사용해 update하는 메서드
  public void updateStudent(StudentDto dto) {
    try(SqlSession session = sessionFactory.openSession()) {
      StudentMapper mapper = session.getMapper(StudentMapper.class);
      mapper.updateStudent(dto);
    }
  }

  // TODO StudentMapper를 사용해 delete하는 메서드
  public void deleteStudent(Long id) {
    try(SqlSession session = sessionFactory.openSession()) {
      StudentMapper mapper = session.getMapper(StudentMapper.class);
      mapper.deleteStudent(id);
    }
  }
}
