package com.swapna.resumematcher.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/resume")
public class PreviewController {

    @GetMapping("/preview/{fileName}")
    public ResponseEntity<Resource> preview(@PathVariable String fileName) throws Exception {
        Path file = Paths.get("uploads").resolve(fileName);
        Resource resource = new UrlResource(file.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                .body(resource);
    }
}