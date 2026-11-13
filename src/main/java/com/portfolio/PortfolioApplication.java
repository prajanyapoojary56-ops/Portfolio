package com.portfolio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PortfolioApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortfolioApplication.class, args);
    }

    // Reloads your projects into the database every time the app starts.
    // Edit the list below and restart to update the site.
    //
    // imageUrl: put the screenshot in src/main/resources/static/images/ and write
    //           the path here, e.g. "images/anifind.png". Use null for no screenshot.
    // liveUrl / codeUrl: use null when there is no link yet.
    @Bean
    CommandLineRunner seed(ProjectRepository repo) {
        return args -> {
            repo.deleteAll();

            repo.save(new Project(
                    "College Event Management System", "Academic Project",
                    "PHP, MySQL, HTML, CSS, JavaScript",
                    "A database-backed web app for managing event registration, attendance and feedback, "
                            + "with role-based access for Admin, Coordinator, Staff and Student users.",
                    null, null, null)); // TODO: screenshot, live demo, GitHub link

            repo.save(new Project(
                    "ANI/FIND – Anime Search App", "Personal Project",
                    "HTML5, CSS3, JavaScript, REST API (Tenrai/MyAnimeList)",
                    "A browser-based anime search app that fetches live data from a public REST API. "
                            + "It has dynamic search, card-based results and a detail modal, and handles "
                            + "loading, empty and error states.",
                    null, null, null)); // TODO: screenshot, live demo, GitHub link

            repo.save(new Project(
                    "Personal Portfolio", "Personal Project",
                    "Spring Boot, Hibernate/JPA, H2, REST API, HTML, CSS, JavaScript",
                    "This website. Projects are served from a database through a REST API, "
                            + "and the contact form saves messages using Hibernate.",
                    null, null, null)); // TODO: screenshot, GitHub link
        };
    }
}
