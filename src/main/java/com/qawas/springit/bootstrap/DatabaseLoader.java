package com.qawas.springit.bootstrap;

import com.qawas.springit.domain.Comment;
import com.qawas.springit.domain.Link;
import com.qawas.springit.domain.Role;
import com.qawas.springit.domain.User;
import com.qawas.springit.repository.CommentRepository;
import com.qawas.springit.repository.LinkRepository;
import com.qawas.springit.repository.RoleRepository;
import com.qawas.springit.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final LinkRepository linkRepository;
    private final CommentRepository commentRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    private Map<String, User> users = new HashMap<>();

    public DatabaseLoader(LinkRepository linkRepository, CommentRepository commentRepository, RoleRepository roleRepository, UserRepository userRepository) {
        this.linkRepository = linkRepository;
        this.commentRepository = commentRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args){

        // add users and roles
        addUsersAndRoles();

        Map<String, String> links = new HashMap<>();
        links.put("Spring Boot Tutorial","https://www.youtube.com/watch?v=9SGDpanrc8U");
        links.put("Backend Tutorial","https://www.youtube.com/watch?v=9SGDpanrc8U");
        links.put("Frontend Tutorial","https://www.youtube.com/watch?v=9SGDpanrc8U");
        links.put("DevOps Tutorial","https://www.youtube.com/watch?v=9SGDpanrc8U");
        links.put("JAVA Tutorial","https://www.youtube.com/watch?v=9SGDpanrc8U");
        links.put("Python Tutorial","https://www.youtube.com/watch?v=9SGDpanrc8U");
        links.put("Postman Tutorial","https://www.youtube.com/watch?v=9SGDpanrc8U");

        links.forEach((k,v) -> {
            User u1 = users.get("qawas@gmail.com");
            User u2 = users.get("admin@gmail.com");
            Link link = new Link(k, v);
            if(k.startsWith("Spring")) {
                link.setUser(u1);
            } else {
                link.setUser(u2);
            }

            linkRepository.save(link);

            Comment spring = new Comment("Thank you for this link related to Spring Boot. I love it, great post!",link);
            Comment security = new Comment("I love that you're talking about Spring Security",link);
            Comment pwa = new Comment("What is this Progressive Web App thing all about? PWAs sound really cool.",link);
            Comment[] comments = {spring,security,pwa};
            for(Comment comment : comments) {
                commentRepository.save(comment);
                link.addComment(comment);
            }
        });

        long linkCount = linkRepository.count();
        System.out.println("Number of links in the database: " + linkCount);
    }

    private void addUsersAndRoles() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String secret = "{bcrypt}" + encoder.encode("password");

        Role userRole = new Role("ROLE_USER");
        roleRepository.save(userRole);
        User user = new User("qawas@gmail.com", secret, true, "Abdallah", "Qawas","Koka");
        user.addRole(userRole);
        userRepository.save(user);
        users.put("qawas@gmail.com",user);

        Role adminRole = new Role("ROLE_ADMIN");
        roleRepository.save(adminRole);
        User admin = new User("admin@gmail.com", secret, true, "admin", "admin", "admin");
        admin.addRole(adminRole);
        userRepository.save(admin);
        users.put("admin@gmail.com",admin);

        User master = new User("master@gmail.com", secret,  true, "master", "master","master");
        master.addRoles(new HashSet<>(Arrays.asList(userRole, adminRole)));
        userRepository.save(master);
        users.put("master@gmail.com", master);

        Long numberOfUsers = userRepository.count();
        System.out.println(numberOfUsers + " users have just inserted in the system");
    }

}
