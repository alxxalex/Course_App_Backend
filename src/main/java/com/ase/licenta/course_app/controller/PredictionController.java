package com.ase.licenta.course_app.controller;

import com.ase.licenta.course_app.service.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

@RestController
@RequestMapping("/api/predictions")
public class PredictionController {

    @Autowired
    private PredictionService predictionService;

    @GetMapping("/exportEnrollments")
    public String exportEnrollments() {
        try {
            predictionService.exportEnrollmentsToCSV("enrollments.csv");
            return "Enrollments exported successfully.";
        } catch (IOException e) {
            return "Failed to export enrollments.";
        }
    }

    @GetMapping("/exportCourses")
    public String exportCourses() {
        try {
            predictionService.exportCoursesToCSV("courses.csv");
            return "Courses exported successfully.";
        } catch (IOException e) {
            return "Failed to export courses.";
        }
    }

    @PostMapping("/recommend")
    public String recommendCourses(@RequestBody Map<String, String> recommendationData) {
        try {
            // Ensure CSV files are up-to-date
            predictionService.exportEnrollmentsToCSV("enrollments.csv");
            predictionService.exportCoursesToCSV("courses.csv");

            // Execute Python script for recommendation
            Process recommendProcess = getProcess(recommendationData);

            BufferedReader reader = new BufferedReader(new InputStreamReader(recommendProcess.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = recommendProcess.waitFor();
            if (exitCode == 0) {
                return output.toString().trim();
            } else {
                return "Failed to get recommendations. Exit code: " + exitCode;
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Failed to get recommendations.";
        }
    }

    private Process getProcess(Map<String, String> recommendationData) throws IOException, InterruptedException {
        String pythonScriptPath = "C:\\Users\\uSER\\Desktop\\Documents\\Licenta\\backend\\output\\recommendation.py";

        String[] retrainCmd = new String[]{"python", pythonScriptPath, "retrain"};
        ProcessBuilder retrainPb = new ProcessBuilder(retrainCmd);
        Process retrainProcess = retrainPb.start();
        retrainProcess.waitFor();

        String email = recommendationData.get("email");
        String[] recommendCmd = new String[]{"python", pythonScriptPath, "recommend", email};
        ProcessBuilder recommendPb = new ProcessBuilder(recommendCmd);
        return recommendPb.start();
    }
}
