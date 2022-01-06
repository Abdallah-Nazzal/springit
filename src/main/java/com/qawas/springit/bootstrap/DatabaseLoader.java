package com.qawas.springit.bootstrap;

import com.qawas.springit.domain.Link;
import com.qawas.springit.repository.CommentRepository;
import com.qawas.springit.repository.LinkRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final LinkRepository linkRepository;
    private final CommentRepository commentRepository;


    public DatabaseLoader(LinkRepository linkRepository, CommentRepository commentRepository) {
        this.linkRepository = linkRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void run(String... args){
        Map<String, String> links = new HashMap<>();
        links.put("Spring Boot Tutorial","https://www.youtube.com/watch?v=9SGDpanrc8U");
        links.put("Backend Tutorial","https://www.youtube.com/watch?v=9SGDpanrc8U");
        links.put("Frontend Tutorial","https://www.youtube.com/watch?v=9SGDpanrc8U");
        links.put("DevOps Tutorial","https://www.youtube.com/watch?v=9SGDpanrc8U");
        links.put("JAVA Tutorial","https://www.youtube.com/watch?v=9SGDpanrc8U");
        links.put("Python Tutorial","https://www.youtube.com/watch?v=9SGDpanrc8U");
        links.put("Postman Tutorial","https://www.youtube.com/watch?v=9SGDpanrc8U");

        links.forEach((k,v) -> {
            linkRepository.save(new Link(k,v));
        });

        long linkCount = linkRepository.count();
        System.out.println("Number of links in the database: " + linkCount);
    }

}
