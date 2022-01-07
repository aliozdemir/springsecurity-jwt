package springsecurityjwt.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import springsecurityjwt.service.DigitalUserDetailsService;
import springsecurityjwt.util.Constant;
import springsecurityjwt.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtValidationFilter extends OncePerRequestFilter {

    @Autowired
    private DigitalUserDetailsService digitalUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader(Constant.HEADER_STRING);
        String jwt = null;
        String username = null;
        if(authHeader != null && authHeader.startsWith(Constant.TOKEN_PREFIX)){
            jwt = authHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        //user not authenticated
        if(jwt != null && username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            final UserDetails userDetails = digitalUserDetailsService.loadUserByUsername(username);
            boolean tokenIsValid = jwtUtil.validateToken(jwt, userDetails);
            if(tokenIsValid){
                final UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                                                                userDetails.getPassword(),
                                                                userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request,response);

    }
}
