package org.rest_chat.rest_chat_server.control;

import org.rest_chat.model.Person;
import org.rest_chat.rest_chat_server.store.UserStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/server/users")
public class UserRestController {

    private UserStore users;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public UserRestController(UserStore users, BCryptPasswordEncoder encoder) {
        this.users = users;
        this.encoder = encoder;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        users.save(person);
    }

    @GetMapping("/all")
    public List<Person> findAll() {
        return users.findAll();
    }
}
