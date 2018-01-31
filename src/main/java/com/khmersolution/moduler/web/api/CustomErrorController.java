package com.khmersolution.moduler.web.api;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Vannaravuth Yo
 * Date : 1/31/2018, 4:40 PM
 * Email : ravuthz@gmail.com
 */

@RestController
public class CustomErrorController implements ErrorController {
    private static final String PATH = "/error";

    @Value("${error.debug}")
    private boolean debug;

    @Autowired
    private ErrorAttributes errorAttributes;

//    @RequestMapping(PATH)
//    public String defaultErrorMessage() {
//        return "Request resource is not found !!!!";
//    }

    @RequestMapping(PATH)
    public ErrorJson errorJson(HttpServletRequest request, HttpServletResponse response) {
        // Appropriate HTTP  response code (Ex: 404, 500) is automatically set by Spring.
        // Here we just define response body
        return new ErrorJson(response.getStatus(), getErrorAttributes(request, debug));
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }
}

@Data
class ErrorJson {
    private Integer status;
    private String error;
    private String message;
    private String timestamp;
    private String trace;

    public ErrorJson(Integer status, Map<String, Object> errorAttributes) {
        this.status = status;
        this.error = (String) errorAttributes.get("error");
        this.message = (String) errorAttributes.get("message");
        this.timestamp = errorAttributes.get("timestamp").toString();
        this.trace = (String) errorAttributes.get("trace");
    }
}
