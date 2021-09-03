package engine.controllers;

import engine.components.UsersComponent;
import engine.models.User;
import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
public class UsersController {
    private final UsersComponent usersComponent;

    @Autowired
    public UsersController(UsersComponent usersComponent) {
        this.usersComponent = usersComponent;
    }

    @PostMapping("/api/register")
    public void register(@Valid @RequestBody User user) {
        if (!usersComponent.tryAdd(user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        System.out.println(usersComponent.getAll());
    }
}
