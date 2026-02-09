package com.swapna.resumematcher.repository;

import com.swapna.resumematcher.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
}
