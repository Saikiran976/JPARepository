package com.example.student.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.student.Exception.ResourceNotFoundException;
import com.example.student.model.Student;
import com.example.student.repository.StudentRepository;

@RestController
@RequestMapping("/student")
public class StudentController {
	@Autowired
	private StudentRepository repository;
	
	@GetMapping("/save")
    public List <Student> getAllStudents() {
        return repository.findAll();
    }

    @GetMapping("/student/{id}")
    public ResponseEntity <Student> getStudentById(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException
    {
        Student student = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Student not found for this id :: " + id));
        return ResponseEntity.ok().body(student);
    }

    @PostMapping("/students")
    public Student createStudent(@Valid @RequestBody Student student) {
        return repository.save(student);
    }

    @PutMapping("/students/{id}")
    public ResponseEntity < Student > updateStudent(@PathVariable(value = "id") Integer id,
        @Valid @RequestBody Student studentDetails) throws ResourceNotFoundException {
        Student student = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));

        student.setEmail(studentDetails.getEmail());
        student.setLastName(studentDetails.getLastName());
        student.setFirstName(studentDetails.getFirstName());
        final Student updatedstudent = repository.save(student);
        return ResponseEntity.ok(updatedstudent);
    }

    @DeleteMapping("/student-delete/{id}")
    public Map < String, Boolean > deleteStudent(@PathVariable(value = "id") Integer id)
    throws ResourceNotFoundException {
        Student student =repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));

        repository.delete(student);
        Map < String, Boolean > response = new HashMap < > ();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
	

}
