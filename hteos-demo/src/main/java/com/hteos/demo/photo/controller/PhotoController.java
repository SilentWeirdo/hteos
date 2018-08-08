package com.hteos.demo.photo.controller;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hteos.demo.photo.quartz.PhotoRefreshJob;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhotoController {
    @RequestMapping("/photo")
    public Object photo() {
        return PhotoRefreshJob.result;
    }
}
