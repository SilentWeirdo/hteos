package com.hteos.demo.weather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hteos.framework.util.WebUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hteos.framework.util.IPUtils;

@Controller
public class WeatherController {

    private static String AK = "Wd8t0HHsOVYVfyy3W8vusInq";

    private static String OUT_PUT = "json";

    private static String WEATHER_URL = "http://api.map.baidu.com/telematics/v3/weather";

    @RequestMapping("/weather")
    @ResponseBody
    public Object weather() {
        String location = IPUtils.getCity(WebUtils.getClientIp());
        String param = "?";
        param += "ak=" + AK;
        if (StringUtils.isNotEmpty(location)) {
            try {
                param += "&location=" + URLEncoder.encode(location, "utf-8");
            } catch (Exception e1) {

            }
            param += "&output=" + OUT_PUT;

            StringBuffer buffer = new StringBuffer();
            URLConnection connection;
            try {
                connection = new URL(WEATHER_URL + param).openConnection();
                InputStreamReader isr = new InputStreamReader(
                        connection.getInputStream(), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String str;
                while ((str = br.readLine()) != null) {
                    buffer.append(str);
                }
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> result = objectMapper.readerFor(Map.class).readValue(isr);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
