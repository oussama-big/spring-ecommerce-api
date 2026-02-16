package com.codewithmosh.store.users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.validation.Valid;

import com.codewithmosh.store.dtos.ChangePasswordRequest;
import com.codewithmosh.store.users.Role;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;








@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder ;

    public UserController(UserRepository userRepository , UserMapper userMapper , PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper ;
        this.passwordEncoder = passwordEncoder ;
    }

    @GetMapping
    public Iterable<UserDto> getAllUsers(){
        return userRepository.findAll()
                            .stream()
                            .map(userMapper::toDto)
                            .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
       var user =  userRepository.findById(id).orElse(null);
       if(user == null){
        return ResponseEntity.notFound().build();
       }
       return ResponseEntity.ok(userMapper.toDto(user));
    }


    @PostMapping
    public ResponseEntity<?> creatUser(
        @Valid @RequestBody RegisterUserRequest request,
        UriComponentsBuilder uriBuilder ){

            if(userRepository.existsByEmail(request.getEmail())){
                return ResponseEntity.badRequest().body( 
                    Map.of("email","is already taken")
                );
            }
        

        var user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Hash the password before saving it to the database
        user.setRole(Role.USER); // Set the role from the request
        userRepository.save(user);

        var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(userMapper.toDto(user));
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
        @PathVariable(name = "id") Long id ,
        @RequestBody UpdateUserRequest request 
    ){

        var user = userRepository.findById(id).orElse(null);

            if(user == null){
                return ResponseEntity.notFound().build();
            }

            userMapper.updateUser(request , user);
            userRepository.save(user);

            return ResponseEntity.ok(userMapper.toDto(user));
    }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteUser(@PathVariable Long id ){
            var user = userRepository.findById(id).orElse(null);

            if(user == null ){
                return ResponseEntity.notFound().build();
            }
            userRepository.delete(user);
            return ResponseEntity.noContent().build();
        }


        @PostMapping("/{id}/change-password")
        public ResponseEntity<Void> changeUserPassword(
            @PathVariable Long id ,
            @RequestBody ChangePasswordRequest request 
        ){

            var user = userRepository.findById(id).orElse(null);

            if(user==null){
                return ResponseEntity.badRequest().build();
            }

            if(!user.getPassword().trim().equals(request.getOldPassword().trim())) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

            user.setPassword(request.getNewPassword());
            userRepository.save(user);

            return ResponseEntity.noContent().build();
        }
        }