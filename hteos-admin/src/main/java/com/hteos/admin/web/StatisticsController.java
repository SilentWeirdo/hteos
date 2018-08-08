package com.hteos.admin.web;

import com.hteos.biz.statistics.service.StatisticsService;
import com.hteos.framework.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/statistics")
public class StatisticsController extends BaseController {

	@Autowired
	private StatisticsService statisticsService;


	@RequestMapping("/get")
	@ResponseBody
	public Object get(Date start,Date end) {
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("statistics", this.statisticsService.get(start,end));
		data.put("map", this.statisticsService.getMapData(start,end));
		data.put("os", this.statisticsService.getOSData(start,end));
		data.put("device", this.statisticsService.getDeviceData(start,end));
		data.put("browser", this.statisticsService.getBrowserData(start,end));
		return data;
	}
	
	@RequestMapping("/chart")
	@ResponseBody
	public Object map(Date start,Date end) {
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("map", this.statisticsService.getMapData(start,end));
		data.put("os", this.statisticsService.getOSData(start,end));
		data.put("device", this.statisticsService.getDeviceData(start,end));
		data.put("browser", this.statisticsService.getBrowserData(start,end));
		return data;
	}
	
	@RequestMapping("/browserVersion")
	@ResponseBody
	public Object browserVersion(Date start,Date end,String browser) {
		return this.statisticsService.getBrowserVersionData(start, end, browser);
	}

	@RequestMapping(value = "/access/list")
	@ResponseBody
	public Object list(Date start,Date end, Integer size, Integer page) {
		return this.statisticsService.list(start,end, size, page);
	}
}
