package com.swapna.resumematcher.controller;

import com.swapna.resumematcher.dto.MatchResponse;
import com.swapna.resumematcher.entity.Resume;
import com.swapna.resumematcher.repository.ResumeRepository;
import com.swapna.resumematcher.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private ResumeRepository resumeRepository;

    @PostMapping("/upload")
    public MatchResponse uploadResume(
            @RequestParam("resume") MultipartFile resumeFile,
            @RequestParam("jobDescription") String jobDescription) {

        try {

            // ==========================
            // 1️⃣ Save file for preview
            // ==========================
            Path uploadDir = Paths.get("uploads");
            Files.createDirectories(uploadDir);

            String fileName = UUID.randomUUID() + "_" + resumeFile.getOriginalFilename();
            Path filePath = uploadDir.resolve(fileName);

            Files.copy(resumeFile.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING);


            // ==========================
            // 2️⃣ Extract text
            // ==========================
            String resumeText = resumeService.extractText(resumeFile);

            if (resumeText == null || resumeText.trim().length() < 30) {

                return new MatchResponse(
                        0,
                        0,
                        0,
                        0,
                        Set.of(),
                        Set.of("Resume text could not be read. Try text-based PDF or DOCX."),
                        true,
                        "Analysis failed"
                );
            }


            // ==========================
            // 3️⃣ Skill extraction
            // ==========================
            Set<String> resumeSkills = resumeService.extractSkills(resumeText);
            Set<String> jdSkills = resumeService.extractSkills(jobDescription);

            Set<String> matchedSkills = new HashSet<>(resumeSkills);
            matchedSkills.retainAll(jdSkills);

            Set<String> missingSkills = new HashSet<>(jdSkills);
            missingSkills.removeAll(resumeSkills);


            // ==========================
            // 4️⃣ Section scoring
            // ==========================
            int skillScore = resumeService.calculateSkillScore(resumeSkills, jdSkills);
            int educationScore = resumeService.calculateEducationScore(resumeText);
            int experienceScore = resumeService.calculateExperienceScore(resumeText);

            int overallScore = (skillScore + educationScore + experienceScore) / 3;


            // ==========================
            // 5️⃣ Save to DB
            // ==========================
            resumeRepository.save(
                    new Resume(
                            null,
                            fileName,
                            resumeText,
                            jobDescription,
                            overallScore
                    )
            );


            // ==========================
            // 6️⃣ Return Response
            // ==========================
            return new MatchResponse(
                    overallScore,
                    skillScore,
                    educationScore,
                    experienceScore,
                    matchedSkills,
                    missingSkills,
                    false,
                    "Analysis completed successfully"
            );

        } catch (Exception e) {

            e.printStackTrace();

            return new MatchResponse(
                    0,
                    0,
                    0,
                    0,
                    Set.of(),
                    Set.of("Unexpected error occurred"),
                    true,
                    "Server error"
            );
        }
    }
}