package com.portfolio;

import jakarta.persistence.*;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String category;   // e.g. "Academic Project"
    private String stack;      // technologies used

    @Column(length = 500)
    private String description;

    private String imageUrl;   // screenshot, e.g. "images/anifind.png"
    private String liveUrl;    // leave null if there is no live demo
    private String codeUrl;    // GitHub link

    protected Project() {} // required by Hibernate

    public Project(String title, String category, String stack, String description,
                   String imageUrl, String liveUrl, String codeUrl) {
        this.title = title;
        this.category = category;
        this.stack = stack;
        this.description = description;
        this.imageUrl = imageUrl;
        this.liveUrl = liveUrl;
        this.codeUrl = codeUrl;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public String getStack() { return stack; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public String getLiveUrl() { return liveUrl; }
    public String getCodeUrl() { return codeUrl; }
}
