package com.hteos.biz.user.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import com.hteos.biz.user.entity.User;
import com.hteos.biz.user.service.UserService;
import com.hteos.biz.app.storage.AliyunOssStorage;
import com.hteos.biz.utils.UserContext;
import com.hteos.framework.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hteos.framework.contants.AccountContants;
import com.hteos.framework.context.http.HttpRequestContextHolder;
import com.hteos.framework.exception.account.IllegalCertificateException;
import com.hteos.framework.util.CookieUtils;
import com.hteos.framework.util.EncryptUtils;

@RestController
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private AliyunOssStorage aliyunOssStorage;

    @RequestMapping(value = "/user/list")
    public Object list(String key, Integer size, Integer page) {
        return this.userService.list(key, size, page);
    }

    @RequestMapping(value = "/user/info/save")
    public Object saveInfo(String nickName, String gender, Date dateOfBirth,
                           String homeTown, String residence, String description) {
        homeTown = homeTown.replaceAll("null", "");
        residence = residence.replaceAll("null", "");
        User user = this.userService.doSaveAccountInfo(nickName, residence);
        HttpRequestContextHolder.getRequest().getSession().setAttribute("ACCOUNT", user);
        return this.success("基本信息保存成功。");
    }

    @RequestMapping("/user/unlock")
    public Object unlock(String password) {
        /*if (EncryptUtils.getMD5(password).equals(
                UserContext.getUser().getPassword())) {*/
            return this.success();
        /*} else {
            return this.failure();
        }*/
    }

    @RequestMapping("/user/password/modify")
    public Object modifyPassoword(String oldPassword, String newPassword, Model model) {
        try {
            if (!newPassword.equals(oldPassword)) {
                this.userService.doModifyPassword(UserContext.getUser()
                                .getUsername(), EncryptUtils.getMD5(oldPassword),
                        EncryptUtils.getMD5(newPassword));
                CookieUtils.add(
                        AccountContants.Account.ACCOUNT_LOGIN_TOKEN_COOKIE_KEY,
                        null, 0);
                return this.success("密码修改成功，下次登录时请使用新密码。");
            } else {
                return this.failure("密码修改失败，新旧密码不能相同。");
            }
        } catch (IllegalCertificateException e) {
            return this.failure("密码修改失败，旧密码输入错误。");
        }
    }

    @RequestMapping(value = "/user/face/save")
    public Object saveFace(String type, String url) throws Exception {
        String dir = HttpRequestContextHolder.getRequest().getServletContext()
                .getRealPath("/")
                + "user/face/";

        if (type.equals("file")) {
            this.saveFaceImage(url, dir, 1);
        } else if (type.equals("camera")) {
            this.saveFaceImage(url, dir, 0);
        } else if (type.equals("url")) {
            this.downloadImage(url, dir);
        }
        return this.success("保存头像成功");
    }

    public void saveFaceImage(String url, String path, int offset)
            throws Exception {
        url = url.substring("data:image/png;base64,".length() + offset,
                url.length());
    }

    public void downloadImage(String url, String dir) throws Exception {
        URL uri = new URL(url);
        InputStream is = uri.openStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        byte[] data = new byte[bis.available()];
        int result;
        // 新生成的图片
        String imgFilePath = dir + UserContext.getUser().getUsername() + ".jpg";
        FileOutputStream out = new FileOutputStream(imgFilePath);
        BufferedOutputStream bos = new BufferedOutputStream(out);
        while ((result = bis.read(data)) != -1) {
            bos.write(data, 0, result);
        }
        bis.close();
        bos.flush();
        bos.close();
    }

    @RequestMapping(value = "/user/saveWallpaper")
    public Object saveWallpaper(String wallpaper) {
        if (UserContext.isLoggedIn()) {
            this.userService.doSaveWallpaper(wallpaper);
            UserContext.getUser().setWallpaper(wallpaper);
            return this.success();
        } else {
            return this.failure("您还没有登录，无法保存您的壁纸");
        }
    }

    @RequestMapping(value = "/user/theme/save")
    public Object saveTheme(String theme) {
        if (UserContext.isLoggedIn()) {
            this.userService.doSaveTheme(theme);
            UserContext.getUser().setTheme(theme);
            return this.success();
        } else {
            return this.failure("您还没有登录，无法保存您的主题");
        }
    }

    @RequestMapping(value = "/user/mode/save")
    public Object saveMode(String mode) {
        if (UserContext.isLoggedIn()) {
            this.userService.doSaveMode(mode);
            UserContext.getUser().setMode(mode);
        } else {
            //return this.failure("您还没有登录，无法保存您的显示模式");
            HttpRequestContextHolder.getRequest().getSession().setAttribute("mode", mode);
        }
        return this.success();
    }

    @RequestMapping(value = "/user/shortcutSize/save")
    public Object saveShortcutSize(String shortcutSize) {
        if (UserContext.isLoggedIn()) {
            this.userService.doSaveShortcutSize(shortcutSize);
            UserContext.getUser().setShortcutSize(shortcutSize);
            return this.success();
        } else {
            return this.failure("您还没有登录，无法保存您的显示模式");
        }
    }

    @RequestMapping(value = "/user/face")
    public String getAccountFace() throws Exception {
        if (!UserContext.isLoggedIn()) {
            return "";
        }
        File file = new File(HttpRequestContextHolder.getRequest()
                .getServletContext().getRealPath("/")
                + "/images/user/face/"
                + UserContext.getUser().getId()
                + ".jpg");
        if (file.exists()) {
            return "redirect:/images/user/face/"
                    + UserContext.getUser().getId() + ".jpg";
        } else {
            return "redirect:/images/user/face/default.png";
        }
    }

    @RequestMapping("/user/face/upload")
    public void upload(MultipartFile face, HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        try {
            if (face == null) {
                response.getWriter().write("<script type=\"text/javascript\">parent.faceUploadFail('选择要上传的图片');</script>");
                response.getWriter().close();
                return;
            }
            if (face.getContentType().indexOf("image/") < 0) {
                response.getWriter().write("<script type=\"text/javascript\" charset=\"utf-8\">parent.faceUploadFail('请选择图片文件');</script>");
                response.getWriter().close();
                return;
            }
            if (face.getSize() > 1024 * 1024 * 1024 * 5) {
                response.getWriter().write("<script type=\"text/javascript\">parent.faceUploadFail('文件太大了，不要超过5MB');</script>");
                response.getWriter().close();
                return;
            }

            String url = this.aliyunOssStorage.upload(face.getInputStream(), "images/user/face/" + UserContext.getUser().getId() + ".png");
            this.userService.doSaveFace(url);
            response.getWriter().write("<script type=\"text/javascript\">parent.faceUploadSuccess();</script>");
            response.getWriter().close();
        } catch (IOException e) {
            try {
                response.getWriter().write(
                        "<script type=\"text/javascript\">parent.faceUploadFail('"
                                + e.getMessage() + "');</script>");
                response.getWriter().close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @RequestMapping("/user/wallpaper/upload")
    public Object uploadWallpaper(MultipartHttpServletRequest request) {
        if (!UserContext.isLoggedIn()) {
            return this.failure("您还没有登录，无法上传壁纸");
        }
        MultipartFile multipartFile = request.getFile("wallpaper");
        String dir = request.getServletContext().getRealPath("/") + "/images/wallpaper/";
        BufferedInputStream faceInput;
        try {
            if (multipartFile == null) {
                return this.failure("没有选择要上传的壁纸");
            }
            if (multipartFile.getContentType().indexOf("image/") < 0) {
                return this.failure("请选择图片类型的文件");
            }
            if (multipartFile.getSize() > 1024 * 1024 * 5) {
                return this.failure("图片大小不能超过5MB");
            }
            faceInput = new BufferedInputStream(multipartFile.getInputStream());
            File faceFile = new File(dir + UserContext.getUser().getId() + ".jpg");
            OutputStream faceOutput = new FileOutputStream(faceFile);
            byte[] data = new byte[1024];
            while (faceInput.read(data) != -1) {
                faceOutput.write(data, 0, data.length);
            }
            faceOutput.flush();
            faceOutput.close();
            return this.success("images/wallpaper/" + UserContext.getUser().getId() + ".jpg");

        } catch (IOException e) {
        }
        return this.success();
    }
}
