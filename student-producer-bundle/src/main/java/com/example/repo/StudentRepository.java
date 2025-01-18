package main.java.com.example.repo;

import com.example.model.Student;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

    private final MongoDatabase database;
    private final MongoCollection<Document> studentsCollection;
    private final MongoCollection<Document> coursesCollection;
    private final MongoCollection<Document> enrollmentsCollection;

    public StudentRepository() {
        this.database = DBUtil.getDatabase();
        this.studentsCollection = database.getCollection("students");
        this.coursesCollection = database.getCollection("courses");
        this.enrollmentsCollection = database.getCollection("enrollments");
    }

    // Browse available courses
    public List<Course> browseCourses() {
        List<Course> courses = new ArrayList<>();
        for (Document doc : coursesCollection.find(Filters.eq("isAvailable", true))) {
            courses.add(mapCourse(doc));
        }
        return courses;
    }

    // Enroll a student in a course
    public void enrollCourse(int studentId, int courseId) {
        Document enrollment = new Document("student_id", studentId)
                .append("course_id", courseId)
                .append("enrollment_date", System.currentTimeMillis());
        enrollmentsCollection.insertOne(enrollment);
        System.out.println("Student enrolled in course successfully!");
    }

    // View progress for a course
    public String viewProgress(int studentId, int courseId) {
        Document enrollment = enrollmentsCollection.find(Filters.and(
                Filters.eq("student_id", studentId),
                Filters.eq("course_id", courseId)
        )).first();

        if (enrollment != null) {
            return "Progress for course ID: " + courseId + " is 50%"; // Example logic
        } else {
            return "Student not enrolled in course.";
        }
    }

    // Search for courses by title or description
    public List<Course> searchCourses(String keyword) {
        Bson filter = Filters.or(
                Filters.regex("title", keyword, "i"),
                Filters.regex("description", keyword, "i")
        );
        List<Course> courses = new ArrayList<>();
        for (Document doc : coursesCollection.find(filter)) {
            courses.add(mapCourse(doc));
        }
        return courses;
    }

    // Helper method to map a Document to a Course object
    private Course mapCourse(Document doc) {
        Course course = new Course(doc.getInteger("course_id"));
        course.setTitle(doc.getString("title"));
        course.setDescription(doc.getString("description"));
        course.setAvailable(doc.getBoolean("isAvailable"));
        return course;
    }

    // Helper method to map a Document to a Student object (if needed)
    private Student mapStudent(Document doc) {
        Student student = new Student(doc.getInteger("student_id"));
        student.setName(doc.getString("name"));
        student.setEmail(doc.getString("email"));
        student.setEnrolledCourses((List<Integer>) doc.get("enrolled_courses"));
        return student;
    }
}
