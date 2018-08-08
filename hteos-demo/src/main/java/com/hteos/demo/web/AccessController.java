package com.hteos.demo.web;

import com.hteos.biz.access.service.AccessService;
import com.hteos.biz.access.entity.Access;
import com.hteos.biz.model.UserVo;
import com.hteos.biz.statistics.service.StatisticsService;
import com.hteos.biz.utils.UserContext;
import com.hteos.framework.bean.Position;
import com.hteos.framework.util.IPUtils;
import com.hteos.framework.util.WebUtils;
import com.hteos.framework.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/statistics")
public class AccessController extends BaseController {

	@Autowired
	private StatisticsService statisticsService;

	@Autowired
	private AccessService accessService;

	@RequestMapping("/access")
	public void access(Access access,HttpServletRequest request) {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
		String day = format.format(date);
		Calendar c = Calendar.getInstance();
		access.setYear(String.valueOf(c.get(Calendar.YEAR)));
		access.setMonth(String.valueOf(c.get(Calendar.YEAR)) + "-" + String.valueOf(c.get(Calendar.MONTH) + 1));
		access.setDay(day);
		access.setTime(format2.format(date));
		access.setIp(WebUtils.getClientIp());
		access.setUserAgent(request.getHeader("user-agent"));
		access.setDate(date);
		access.setAccessCount(1L);
		access.setDuration(0L);
		UserVo account = UserContext.getUser();
		access.setUser(account != null ? account.getEmail() : "");
		Position position = IPUtils.getPosition(WebUtils.getClientIp());
		if (position != null) {
			access.setProvince(position.getProvince());
			access.setCity(position.getCity());
		}
		this.accessService.save(access);
	}

	@RequestMapping(value = "/leave")
	public Object leave(Long duration) {
		 this.accessService.setDuration(WebUtils.getClientIp(), duration);
		 return this.success();
	}
}
