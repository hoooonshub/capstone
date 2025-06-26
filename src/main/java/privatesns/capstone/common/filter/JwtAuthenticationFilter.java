package privatesns.capstone.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import privatesns.capstone.common.security.jwt.JwtParser;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final JwtParser jwtParser;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader(AUTHORIZATION_HEADER);
        if (token != null) {
            setAuthentication(token);
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String token) {
        var authentication = jwtParser.parseAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
