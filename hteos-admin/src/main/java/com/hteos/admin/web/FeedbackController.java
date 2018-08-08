package com.hteos.admin.web;

import com.hteos.biz.feedback.service.FeedbackService;
import com.hteos.framework.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FeedbackController extends BaseController {

	@Autowired
	private FeedbackService feedbackService;

	@RequestMapping("/feedback/list")
	@ResponseBody
	public Object listFeedback(String day, Integer size, Integer page) {
		return this.feedbackService.list(day, size, page);
	}

}
