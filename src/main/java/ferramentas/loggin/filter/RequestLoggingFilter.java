package ferramentas.loggin.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ferramentas.loggin.util.LoggingUtils;

import java.io.IOException;

@Component
public class RequestLoggingFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        long start = System.currentTimeMillis();

        String correlationId = LoggingUtils.generateCorrelationId();

        try {

            log.info("HTTP request received method={} path={}",
                    req.getMethod(),
                    req.getRequestURI());
            chain.doFilter(request, response);

        } finally {

            long duration = System.currentTimeMillis() - start;
            log.info("HTTP request completed status={} durationMs={}",
                    res.getStatus(),
                    duration);
            LoggingUtils.clear();

        }


    }
}

