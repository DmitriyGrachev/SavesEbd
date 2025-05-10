package org.hrachov.com.filmproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
//TODO

@Controller
public class MainController {
    @GetMapping("/")
    public String index() {
        return "index"; // Renders templates/index.html
    }

    @GetMapping("/movies")
    public String moviesPage() {
        return "films/moviesPage";
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
    //TODO
    @GetMapping("/authfront/login")
    public String login(){
        return "login";
    }
    @GetMapping("/authfront/logout")
    public String logout() {
        return "logout";
    }
    @GetMapping("/authfront/register")
    public String register() {
        return "register";
    }
}