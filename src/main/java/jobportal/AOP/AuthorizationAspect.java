package jobportal.AOP;

import jobportal.Annotation.Authorization;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AuthorizationAspect {

    private static final Logger log =
            LoggerFactory.getLogger(AuthorizationAspect.class);

    @Around("@annotation(authorization)")
    public Object authorize(
            ProceedingJoinPoint joinPoint,
            Authorization authorization) throws Throwable {

        // 1. Method ke liye required roles nikalo
        String[] requiredRoles = authorization.roles();

        // 2. Current logged-in user ki Authentication nikalo
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        // 3. Check karo authentication available hai ya nahi
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Authentication required");
        }

        // 4. User ke actual authorities/roles check karo
        boolean authorized = authentication.getAuthorities()
                .stream()
                .anyMatch(authority ->
                        Arrays.asList(requiredRoles)
                                .contains(authority.getAuthority())
                );

        // 5. Role match karta hai
        if (authorized) {

            log.info(
                    "Authorization successful for user: {}",
                    authentication.getName()
            );

            // Original service method execute karo
            return joinPoint.proceed();
        }

        // 6. Role match nahi karta
        log.warn(
                "Authorization failed for user: {}",
                authentication.getName()
        );

        throw new RuntimeException("Access denied");
    }
}