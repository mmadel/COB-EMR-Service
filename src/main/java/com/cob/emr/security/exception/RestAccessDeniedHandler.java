package com.cob.emr.security.exception;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("handlerRestAccessDenied")
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws ServletException, IOException {
//        ControllerErrorResponse controllerErrorResponse = new ControllerErrorResponse(e.getMessage(), HttpStatus.UNAUTHORIZED);
//        Gson gson = new GsonBuilder()
//                .setPrettyPrinting()
//                .create();
//        gson.toJson(controllerErrorResponse);
//        gson.toJson(controllerErrorResponse, httpServletResponse.getWriter());
    }
}