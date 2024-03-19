package io.github.jubadeveloper;

import jakarta.servlet.http.HttpServlet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
public class Handler {
    @RequestMapping("/")
    public String hello () {
        return "hello";
    }

    @ModelAttribute("hello")
    public List<String> helloModel () {
        List<String> list = Arrays.asList("Hello", "Ana", "Mathes", "Carvalho", null);
        Collections.shuffle(list);
        return list;
    }
}
