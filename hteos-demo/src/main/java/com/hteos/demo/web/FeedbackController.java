package com.hteos.demo.web;

import com.hteos.biz.feedback.service.FeedbackService;
import com.hteos.framework.util.ResponseUtils;
import com.hteos.framework.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FeedbackController extends BaseController {

	@Autowired
	private FeedbackService feedbackService;

	@RequestMapping("/feedback/save")
	public @ResponseBody
	Object saveFeedback(String email, String name, String content) {
		this.feedbackService.saveFeedback(email, name, content);
		return ResponseUtils.sendSuccess("message", "接收反馈成功");
	}

}
