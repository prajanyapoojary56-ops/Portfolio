package com.portfolio;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Apicontroller {

    private final ProjectRepository projects;
    private final MessageRepository messages;

    public Apicontroller(ProjectRepository projects, MessageRepository messages) {
        this.projects = projects;
        this.messages = messages;
    }

    @GetMapping("/projects")
    public List<Project> getProjects() {
        return projects.findAll();
    }

    @PostMapping("/contact")
    public ResponseEntity<Void> saveMessage(@Valid @RequestBody Message message) {
        message.setId(null); // always insert a new row
        messages.save(message);
        return ResponseEntity.status(201).build();
    }
}

