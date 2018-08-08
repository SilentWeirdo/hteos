package com.hteos.framework.web.controller;

import com.hteos.framework.core.constant.ErrorStatus;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LIQIU
 * @date 2018-5-3
 **/
@Controller
public class ErrorController extends BasicErrorController {

    public ErrorController(ErrorAttributes errorAttributes, ServerProperties serverProperties,
                           ObjectProvider<List<ErrorViewResolver>> errorViewResolversProvider) {
        super(errorAttributes, serverProperties.getError(), errorViewResolversProvider.getIfAvailable());
    }

    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        ResponseEntity responseEntity = super.error(request);
        Map<String, Object> body = (Map<String, Object>) responseEntity.getBody();
        Map<String, Object> result = new HashMap<>(body.size());
        result.put("success", false);
        result.put("code", ErrorStatus.INTERNAL_SERVER_ERROR.value());
        result.put("msg", body.get("message"));
        return new ResponseEntity<>(result, responseEntity.getStatusCode());
    }

    @Override
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        return super.errorHtml(request, response);
    }
}
