# 🚀 ATS Resume Analyzer & Matcher

A full-stack Spring Boot application that analyzes resumes against a job description and generates an ATS (Applicant Tracking System) compatibility score.

This project simulates how real ATS systems evaluate resumes using keyword matching, section-based scoring, and OCR for scanned PDFs.

---

## 📌 Project Overview

The ATS Resume Analyzer allows users to:

- Upload a Resume (PDF/DOCX)
- Paste a Job Description
- Extract text using Apache Tika
- Perform OCR for scanned resumes using Tesseract
- Match skills between Resume & Job Description
- Generate section-wise scores
- Display animated dashboard results
- Preview uploaded resumes

---

## 🛠 Tech Stack

### 🔹 Backend
- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- Apache Tika (Text Extraction)
- Tess4J (Tesseract OCR)
- Apache PDFBox
- MySQL / H2 Database

### 🔹 Frontend
- HTML5
- CSS3
- JavaScript
- Animated SVG Score Ring
- Responsive Dashboard UI

---

## 🧠 Features

### ✅ Resume Text Extraction
- Uses Apache Tika for text-based PDF/DOCX files
- Automatically falls back to Tesseract OCR for scanned PDFs

### ✅ Skill Matching Engine
- Extracts predefined technical skills
- Matches resume skills against job description
- Calculates skill match percentage

### ✅ Section-Based Scoring
- Skill Score
- Education Score
- Experience Score
- Overall ATS Score (Average of sections)

### ✅ Visual Dashboard
- Animated circular score ring
- Skill breakdown cards
- Matched vs Missing skills
- Clean modern UI

### ✅ Resume Preview API
- Uploads resume to local storage
- Inline preview support via REST endpoint

---

## 📊 Scoring Logic
```
Overall Score =  
(Skill Score + Education Score + Experience Score) / 3
```
```
Skill Score =  
(Matched Skills / Total JD Skills) * 100
```

---

## 📂 Project Structure


```
resumematcher/
│
├── controller/
│ ├── ResumeController.java
│ ├── PreviewController.java
│
├── service/
│ ├── ResumeService.java
│
├── dto/
│ ├── MatchResponse.java
│
├── util/
│ ├── SkillConstants.java
│
├── repository/
│ ├── ResumeRepository.java
│
├── templates/
│ ├── upload.html
│
├── static/
│ ├── css/style.css
│ ├── js/uploadjs.js

```
---

## ⚙️ Installation & Setup

### 1️⃣ Clone Repository

git clone https://github.com/yourusername/ats-resume-analyzer.git

2️⃣ Configure Database

Update application.properties
```
spring.datasource.url=jdbc:mysql://localhost:3306/resumematcher
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```
3️⃣ Install Tesseract OCR

Download from:
https://github.com/UB-Mannheim/tesseract/wiki

Set datapath inside:

tesseract.setDatapath("C:/Program Files/Tesseract-OCR/tessdata");

4️⃣ Run Application
```
mvn spring-boot:run
```

Access:
```
http://localhost:8080/upload.html
```
## 📸 Screenshots

<img width="1913" height="899" alt="image" src="https://github.com/user-attachments/assets/84c44771-00fe-4e48-b9e5-71d3cf15fe4c" />


## 🔍 Example Output

Overall Score: 93/100

Matched Skills: Java, Spring Boot, ReactJS

Missing Skills: Docker, AWS

## 🚀 Future Enhancements

AI-based resume feedback

NLP keyword weighting

Cloud deployment

Role-based scoring system

Resume improvement suggestions

Dark mode UI

## 🎯 Learning Outcomes

This project helped me strengthen:

Spring Boot architecture

File handling & OCR integration

REST API development

ATS keyword-based logic

Frontend dashboard design

Full-stack integration
