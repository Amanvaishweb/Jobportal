package jobportal.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(1)
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger log =
            LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        long startTime = System.currentTimeMillis();

        log.info(
                "Request Incoming: {}, {}",
                request.getMethod(),
                request.getRequestURI()
        );

        String requestId = UUID.randomUUID().toString();

        response.setHeader("requestId", requestId);

        filterChain.doFilter(request, response);

        log.info(
                "Execution time By Filter: {} ms",
                System.currentTimeMillis() - startTime
        );

        log.info(
                "HTTP Status: {}",
                response.getStatus()
        );
    }
}