package com.swapna.resumematcher.service;

import com.swapna.resumematcher.util.SkillConstants;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

@Service
public class ResumeService {

    // ==============================
    // 🔹 MAIN TEXT EXTRACTION
    // ==============================
    public String extractText(MultipartFile file) {

        try {
            Tika tika = new Tika();
            String text = tika.parseToString(file.getInputStream());

            // If Tika extracted enough text → return it
            if (text != null && text.trim().length() > 100) {
                return text;
            }

            // If PDF and text too small → use OCR
            if (file.getOriginalFilename() != null &&
                    file.getOriginalFilename().toLowerCase().endsWith(".pdf")) {

                return extractUsingOCR(file);
            }

            return text;

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    // ==============================
    // 🔹 OCR FOR SCANNED PDF
    // ==============================
    private String extractUsingOCR(MultipartFile file) {

        try {
            ITesseract tesseract = new Tesseract();

            // Change if your installation path is different
            tesseract.setDatapath("C:/Program Files/Tesseract-OCR/tessdata");
            tesseract.setLanguage("eng");

            PDDocument document = PDDocument.load(file.getInputStream());
            PDFRenderer renderer = new PDFRenderer(document);

            StringBuilder result = new StringBuilder();

            for (int i = 0; i < document.getNumberOfPages(); i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 300);
                result.append(tesseract.doOCR(image));
            }

            document.close();
            return result.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    // ==============================
    // 🔹 SKILL EXTRACTION
    // ==============================
    public Set<String> extractSkills(String text) {

        Set<String> foundSkills = new HashSet<>();

        if (text == null) return foundSkills;

        String normalized = text.toLowerCase()
                .replaceAll("[^a-z ]", " ")
                .replaceAll("\\s+", " ");

        for (String skill : SkillConstants.SKILLS) {
            if (normalized.contains(skill.toLowerCase())) {
                foundSkills.add(skill);
            }
        }

        return foundSkills;
    }


    // ==============================
    // 🔹 SKILL SCORE (Corrected Logic)
    // ==============================
    public int calculateSkillScore(Set<String> resumeSkills, Set<String> jdSkills) {

        if (jdSkills == null || jdSkills.isEmpty()) return 0;

        // Find matched skills
        Set<String> matched = new HashSet<>(resumeSkills);
        matched.retainAll(jdSkills);

        // Calculate percentage
        return (int) ((matched.size() * 100.0) / jdSkills.size());
    }


    // ==============================
    // 🔹 EDUCATION SCORE (Improved)
    // ==============================
    public int calculateEducationScore(String text) {

        if (text == null) return 0;

        String lower = text.toLowerCase();

        if (lower.contains("b.tech") ||
                lower.contains("bachelor") ||
                lower.contains("mca") ||
                lower.contains("master") ||
                lower.contains("degree")) {

            return 100;
        }

        return 40;
    }


    // ==============================
    // 🔹 EXPERIENCE SCORE (Improved)
    // ==============================
    public int calculateExperienceScore(String text) {

        if (text == null) return 0;

        String lower = text.toLowerCase();

        if (lower.contains("experience") ||
                lower.contains("years") ||
                lower.contains("worked") ||
                lower.contains("project")) {

            return 80;
        }

        return 50;
    }
}