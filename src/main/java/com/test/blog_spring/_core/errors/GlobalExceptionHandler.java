package com.test.blog_spring._core.errors;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception400.class)
    public String exception400(Exception400 e, HttpServletRequest request, Model mo){
        printLog(400, e, request);

        mo.addAttribute("message", e.getMessage());

        return "err/400";
    }

    @ExceptionHandler(Exception401.class)
    public String exception401(Exception401 e, HttpServletRequest request, Model mo){
        printLog(401, e, request);

        mo.addAttribute("message", e.getMessage());

        return "err/401";
    }

    @ExceptionHandler(Exception403.class)
    public String exception403(Exception403 e, HttpServletRequest request, Model mo){
        printLog(403, e, request);

        mo.addAttribute("message", e.getMessage());

        return "err/403";
    }

    @ExceptionHandler(Exception404.class)
    public String exception404(Exception404 e, HttpServletRequest request, Model mo){
        printLog(404, e, request);

        mo.addAttribute("message", e.getMessage());

        return "err/404";
    }

    @ExceptionHandler(Exception500.class)
    public String exception500(Exception500 e, HttpServletRequest request, Model mo){
        printLog(500, e, request);

        mo.addAttribute("message", e.getMessage());

        return "err/500";
    }

    // Log Util
    private void printLog(int code, Exception e, HttpServletRequest request){
        log.error(" === EXCEPTION {} ===", code);
        log.error("url : {}", request.getRequestURL());
        log.error("message : {}", e.getMessage());
    }
}
