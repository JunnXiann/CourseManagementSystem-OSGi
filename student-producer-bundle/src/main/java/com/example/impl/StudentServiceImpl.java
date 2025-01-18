package main.java.com.example.impl;

import main.java.com.example.api.StudentService;
import main.java.com.example.model.Student;
import main.java.com.example.repo.StudentRepository;

public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void browseCourses() {
        System.out.println("Browsing courses");
        // Use the repository to fetch courses
        studentRepository.browseCourses(); 
        
    }

    @Override
    public void enrollCourse(String courseId) {
        System.out.println("Enrolling in course " + courseId);
    }

    @Override
    public void viewProgress() {
        System.out.println("Viewing progress");
    }

    @Override
    public void searchCourses(String query) {
        System.out.println("Searching for courses with query " + query);
    }
    
}
