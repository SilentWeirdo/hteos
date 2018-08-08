package com.hteos.framework.exception;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class ExceptionHandler implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
										 HttpServletResponse response, Object handler, Exception ex) {
		ex.printStackTrace();
		if (!(handler instanceof HandlerMethod)) {
			return null;
		}
		HandlerMethod method = (HandlerMethod) handler;
		if (method.getMethodAnnotation(ResponseBody.class) != null) {
			JSONObject json = new JSONObject();
			json.element("success", false).element("message",
					ex.getMessage() != null ? ex.getMessage() : ex.toString());
			try {
				response.getWriter().print(json);
				response.getWriter().close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		} else {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("exception", ex);
			return new ModelAndView("error/500", model);
		}

	}
}