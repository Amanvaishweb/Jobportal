package jobportal.Interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class LoggingInterceptor implements HandlerInterceptor {
    Logger log = LoggerFactory.getLogger(LoggingInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

    if(handler instanceof HandlerMethod handlerMethod) {
        log.info("Controller Name : {} , Method Name : {}",
                handlerMethod.getBeanType().getSimpleName(),
                handlerMethod.getMethod().getName());
    }
    return true;
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        log.info("Interceptor Status : {}"
        , response.getStatus());
    }
}
