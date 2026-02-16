package com.codewithmosh.store.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithmosh.store.dtos.LoginRequest;
import com.codewithmosh.store.users.UserDto;
import com.codewithmosh.store.users.UserMapper;
import com.codewithmosh.store.users.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;




@RestController
@RequestMapping("/auth")
public class AuthController {
    // private final UserRepository userRepository;
    // private final PasswordEncoder passwordEncoder ;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtConfig jwtConfig;
    private final AuthService authService;

    // @Value("${Spring.Jwt.refreshExpiration}")
    // private long  refreshExpiration ; // # days in milliseconds


    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserRepository userRepository, UserMapper userMapper , JwtConfig jwtConfig , AuthService authService) {
         this.userRepository = userRepository;
         this.userMapper = userMapper;
         this.jwtConfig = jwtConfig;
         this.authService = authService;
        // this.userRepository = userRepository;
        // this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(
        @Valid @RequestBody LoginRequest request ,
        HttpServletResponse response // We need this to set the refresh token as a cookie
    ) {

        // var user = userRepository.findByEmail(request.getEmail()).orElse(null);
        // if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        //    return ResponseEntity.status(401).build();
        // }
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        var accesstoken = jwtService.generateAccessToken(user);
        var refreshtoken = jwtService.generateRefreshToken(user);

        var cookie  = new Cookie( "refreshToken" , refreshtoken.toString());
        cookie.setHttpOnly(true); // Make the cookie accessible only through HTTP (not JavaScript)
        cookie.setPath("/"); // Set the path for which the cookie is valid
        cookie.setMaxAge((int) (jwtConfig.getRefreshExpiration())); // Set the cookie to expire in # days
        cookie.setSecure(true); // Ensure the cookie is sent only over HTTPS
        response.addCookie(cookie); // Add the cookie to the response



        
        return ResponseEntity.ok(new JwtResponse(accesstoken.toString()));

    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me (
        @RequestHeader ("Authorization") String token
    ){
        // Extract the token from the Authorization header


        var user = authService.getCurrentUser();
        if(user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        // Map the user entity to a UserDto and return it
        var userDto = userMapper.toDto(user);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/validate")
        public boolean validate(@RequestHeader("Authorization") String token){

            System.out.println("Validating called"); // Debugging line
            var jwtToken = token.replace("Bearer ", ""); // Remove "Bearer " prefix if present
            var jwt = jwtService.parseToken(jwtToken);
            if(jwt == null || jwt.isExpired()){
                return false;
            }
            return true;
        }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED ).build();
    }



    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(
        @CookieValue(value = "refreshToken", required = false) String refreshToken
    ){
        var jwt = jwtService.parseToken(refreshToken);
         if(jwt == null || jwt.isExpired()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var user = userRepository.findById(jwt.getUserId()).orElse(null);
        if(user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        var accessToken = jwtService.generateAccessToken(user);
        return ResponseEntity.ok(new JwtResponse(accessToken.toString()));
    }
}
