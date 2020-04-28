package org.itachi.codestar.interceptor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.itachi.codestar.util.Constants;
import org.itachi.codestar.util.ThrowableUtil;
import org.itachi.codestar.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by itachi on 2017/4/23.
 * User: itachi
 * Date: 2017/4/23
 * Time: 09:45
 *
 * @author itachi
 */
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    private static final String CODE = "code";
    private static final String MESSAGE = "message";
    private static final String HTTP = "http";

    @Autowired
    @Qualifier("messageSource")
    private MessageSource messageSource;

    private HttpServletRequest request;
    private HttpServletResponse response;

    private final ObjectMapper mapper = new ObjectMapper();

    public AuthorizationInterceptor() {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        this.request = request;
        this.response = response;

        Map<String, Object> result = new HashMap<>(16);
        result.put(CODE, HttpStatus.UNAUTHORIZED.value());

        try {
            String servletPath = request.getServletPath();
            return checkSession(result, servletPath);
        } catch (Exception e) {
            String message = ThrowableUtil.getInstance().getMessage(e);
            result.put(MESSAGE, message == null ? "操，登录异常了！" : message);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().append(mapper.writeValueAsString(result)).flush();
            response.getWriter().close();
            return false;
        }
    }

    private boolean validateApiPath(String servletPath) {
        return validatePath("api.path", servletPath);
    }

    private boolean validateIgnorePath(String servletPath) {
        return validatePath("ignore.path", servletPath);
    }

    private boolean validatePath(String key, String servletPath) {
        String paths = messageSource.getMessage(key, null, null, Locale.getDefault());
        if (paths != null && !paths.isEmpty()) {
            Pattern pattern = Pattern.compile(paths.trim());
            if (pattern.matcher(servletPath).matches()) {
                return true;
            }
        }
        return false;
    }

    private boolean checkSession(Map<String, Object> result, String servletPath) throws IOException {
        HttpSession session = request.getSession(false);
        boolean apiFlag = validateApiPath(servletPath);
        if (session == null) {
            result.put(CODE, 502);
            result.put(MESSAGE, "session不存在，用户没登陆");
            Utils.cleanSessions(request);
            abort(apiFlag, result);
            return false;
        }

        if (session.getAttribute(Constants.SESSION_KEY) == null) {
            result.put(CODE, 503);
            result.put(MESSAGE, "session中对应的对象不存在，用户没登陆");
            Utils.cleanSessions(request);
            abort(apiFlag, result);
            return false;
        }
        return true;
    }

    private void abort(boolean apiFlag, Map<String, Object> result) throws IOException {
        if (apiFlag) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().append(mapper.writeValueAsString(result)).flush();
            response.getWriter().close();
        } else {
            String url = messageSource.getMessage("login.path", null, "login", Locale.getDefault());
            if (url.startsWith(HTTP)) {
                response.sendRedirect(url);
            } else {
                response.sendRedirect(request.getContextPath() + "/" + url);
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
