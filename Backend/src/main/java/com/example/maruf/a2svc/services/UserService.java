package com.example.maruf.a2svc.services;

import com.example.maruf.a2svc.models.User;
import com.example.maruf.a2svc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
@Service
public class UserService {
    private  final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers(){
        return (List<User>) userRepository.findAll();
    }
//    public User addUser(User user){
//        Optional<User> userByName = userRepository.findByName(user.getName());
//        if (userByName.isPresent()){
//            throw new IllegalStateException("User by the name already exists");
//        }
//        return userRepository.save(user);
//    }
    public User updateTitle(long id, User user) {
        User userFromDb = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "No user by the id provided"));

        userFromDb.setTitle(user.getTitle());
        return userRepository.save(userFromDb);
    }
}
