package springsecurityjwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springsecurityjwt.entity.DigitalUser;
import springsecurityjwt.repository.UserRepository;

@Service
public class DigitalUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DigitalUser user = userRepository.findByIdentityNo(username);
        if(user == null){
            throw new UsernameNotFoundException("Digital user not found");
        }
        return new DigitalUserDetails(user);
    }
}
