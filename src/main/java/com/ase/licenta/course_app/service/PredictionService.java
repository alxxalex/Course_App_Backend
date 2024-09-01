package com.ase.licenta.course_app.service;

import com.ase.licenta.course_app.model.Course;
import com.ase.licenta.course_app.model.Enrollment;
import com.ase.licenta.course_app.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Service
public class PredictionService {

    @Autowired
    private CourseService courseService;
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public void exportEnrollmentsToCSV(String fileName) throws IOException {
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        String filePath = Paths.get("C:\\Users\\uSER\\Desktop\\Documents\\Licenta\\backend\\output", fileName).toString();
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Enrollment enrollment : enrollments) {
                for (String courseId : enrollment.getCourses()) {
                    writer.append(escapeCsvField(enrollment.getEmail())).append(",")
                            .append(escapeCsvField(courseId)).append("\n");
                }
            }
            writer.flush();
        }
    }

    public void exportCoursesToCSV(String fileName) throws IOException {
        List<Course> courses = courseService.getCourses();
        String filePath = Paths.get("C:\\Users\\uSER\\Desktop\\Documents\\Licenta\\backend\\output", fileName).toString();
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Course course : courses) {
                writer.append(escapeCsvField(course.getId())).append(",")
                        .append(escapeCsvField(course.getTitle())).append(",")
                        .append(escapeCsvField(String.join(";", course.getJobTitle()))).append("\n");
            }
            writer.flush();
        }
    }

    private String escapeCsvField(String field) {
        if (field.contains(",") || field.contains("\n") || field.contains("\"")) {
            field = field.replace("\"", "\"\"");
            return "\"" + field + "\"";
        }
        return field;
    }
}
