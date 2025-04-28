package org.hrachov.com.filmproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class MainController {
    @GetMapping("/")
    public String index() {
        return "index"; // Рендерит templates/index.html
    }

    @GetMapping("/movies")
    public String moviesPage() {
        return "films/moviesPage"; // Рендерит templates/moviesPage.html
    }
    @GetMapping("/movie/{id}")
    public String moviePage(@PathVariable int id) {
        return "films/moviePage";
    }
    @GetMapping("/movies/search")
    public String movieSearchPage() {
        return "films/moviesSearchPage";
    }
    @GetMapping("/test")
    public String testPage() {
        return "test";
    }
    @GetMapping("/videoDirectories")
    public String videoDirectoriesPage() {
        return "videosAll";
    }
    @GetMapping("/videos/{id}")
    public String videoDirectoriesPage(@PathVariable int id) {
        return "videosInRepo";
    }
}
