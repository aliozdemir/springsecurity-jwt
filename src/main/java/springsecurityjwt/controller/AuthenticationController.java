package springsecurityjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springsecurityjwt.entity.DigitalUser;
import springsecurityjwt.model.AuthenticationModel;
import springsecurityjwt.service.DigitalUserDetails;
import springsecurityjwt.service.DigitalUserDetailsService;
import springsecurityjwt.util.JwtUtil;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DigitalUserDetailsService digitalUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody  @Validated AuthenticationModel authenticationModel) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationModel.getUsername(),authenticationModel.getPassword()));
        }catch (BadCredentialsException e){
            throw new Exception("Incorrect username and password", e);
        }

        //If user authenticated
        final UserDetails userDetails = digitalUserDetailsService.loadUserByUsername(authenticationModel.getUsername());

        //Generate jwt with userdetails
        final String jwt = jwtUtil.generateToken(userDetails);

        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }

}
