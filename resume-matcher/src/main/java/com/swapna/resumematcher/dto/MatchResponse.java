package com.swapna.resumematcher.dto;

import java.util.Set;

public class MatchResponse {

    private int overallScore;
    private int skillScore;
    private int educationScore;
    private int experienceScore;

    private Set<String> matchedSkills;
    private Set<String> missingSkills;

    private boolean estimated;
    private String message;

    public MatchResponse(int overallScore,
                         int skillScore,
                         int educationScore,
                         int experienceScore,
                         Set<String> matchedSkills,
                         Set<String> missingSkills,
                         boolean estimated,
                         String message) {

        this.overallScore = overallScore;
        this.skillScore = skillScore;
        this.educationScore = educationScore;
        this.experienceScore = experienceScore;
        this.matchedSkills = matchedSkills;
        this.missingSkills = missingSkills;
        this.estimated = estimated;
        this.message = message;
    }

    public int getOverallScore() { return overallScore; }
    public int getSkillScore() { return skillScore; }
    public int getEducationScore() { return educationScore; }
    public int getExperienceScore() { return experienceScore; }
    public Set<String> getMatchedSkills() { return matchedSkills; }
    public Set<String> getMissingSkills() { return missingSkills; }
    public boolean isEstimated() { return estimated; }
    public String getMessage() { return message; }
}