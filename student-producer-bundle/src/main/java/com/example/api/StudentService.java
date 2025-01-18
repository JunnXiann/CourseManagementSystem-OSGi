package main.java.com.example.api;

public interface StudentService {
    void browseCourses();
    void enrollCourse(String courseId);
    void viewProgress();
    void searchCourses(String query);
}
