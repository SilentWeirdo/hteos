(function () {

    /**
     * @author LIQIU <service@hteos.com>
     * @class HteOS.component.Setting 用户设置组件
     */
    HteOS.controller.Setting = function () {
        this.onViewRender = function () {
            $(document).off("click.hte.wallpaper.link");
            $(document).on("click.hte.wallpaper.link", ".btn-link-wallpaper", function () {
                HteOS.Messager.prompt("设置壁纸", "请输入网络图片链接", [{
                    name: 'url',
                    label: '图片链接',
                    inputType: "url"
                }], function (btn, values) {
                    if (btn == 'yes') {
                        if (values.url) {
                            var strRegex = "^((https|http|ftp|rtsp|mms)?://)"
                                + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@
                                + "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
                                + "|" // 允许IP和DOMAIN（域名）
                                + "([0-9a-z_!~*'()-]+\.)*" // 域名- www.
                                + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名
                                + "[a-z]{2,6})" // first level domain- .com or .museum
                                + "(:[0-9]{1,4})?" // 端口- :80
                                + "((/?)|" // a slash isn't required if there is no file name
                                + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
                            var re = new RegExp(strRegex), result = re.test(values.url);
                            if (result) {
                                HteOS.EventManager.trigger('hte.wallpaper.change', values.url);
                            } else {
                                $(".hte-messager-tip").text("输入的地址不是正确的URL地址");
                                return false;
                            }
                        }
                    }
                });
            });

            $(document).off("click.hte.wallpaper.upload");
            $(document).on("click.hte.wallpaper.upload", ".btn-upload-wallpaper", function () {
                $(".wallpaper-upload-file").click();
            });

            $(".wallpaper-upload-file").off();
            $(".wallpaper-upload-file").change(
                function () {
                    var file = this.files[0];
                    if (file.type != "image/png"
                        && file.type != "image/gif"
                        && file.type != "image/jpeg") {
                        HteOS.Messager.alert("设置壁纸", "请选择图片文件");
                    } else if (file.size > 1024 * 1024 * 10) {
                        HteOS.Messager.alert("设置壁纸", "图片文件太大");
                    } else {
                        upload(file);
                    }
                });

            var upload = function (file) {
                var button = $(".btn-upload-wallpaper");
                button.html("正在上传..");
                button.attr("disabled", "disabled");
                var xhr = new XMLHttpRequest();
                xhr.onreadystatechange = function () {
                    if (xhr.readyState == 4) {
                        button.html("上传壁纸");
                        button.removeAttr("disabled");
                        if (xhr.status == 200) {
                            var result = JSON.parse(xhr.responseText);
                            if (result.success) {
                                HteOS.Notification.show("", "应用消息", "上传壁纸成功");
                                HteOS.Preference.setWallpaper(result.message);
                            } else {
                                HteOS.Notification.show("", "应用消息", "上传壁纸失败：" + result.message);
                            }
                        }
                        else {
                            HteOS.Notification.show("", "应用消息", "上传壁纸失败：" + xhr.status + " " + xhr.statusText);
                        }
                    }
                }
                xhr.open("POST", HteOS.API.WALLPAPER_UPLOAD, true);
                var fd = new FormData();
                fd.append("wallpaper", file);
                xhr.send(fd);
            }

            //触发墙纸更改事件
            $(document).off('click.hte.wallpaper.data-api', '.hte-wallpaper-list > ul > li > img');
            $(document).on(
                'click.hte.wallpaper.data-api',
                '.hte-wallpaper-list > ul > li > img',
                function () {
                    HteOS.Preference.setWallpaper($(this).attr("src"));
                });
            //触发主题更改事件
            $(document).off('click.hte.theme.data-api', '.hte-theme-item');
            $(document).on('click.hte.theme.data-api', '.hte-theme-item',
                function () {
                    var theme = $(this).data('theme');
                    HteOS.Preference.setTheme(theme);
                });
        }
    }

    HteOS.TemplateManager.register('setting.html', "<div class=\"hte-settings-panel hte-webkit-scrollbar\">\n" +
        "	<div class=\"hte-settings-inner\">\n" +
        "	<ul class=\"nav nav-tabs messager-nav nav-justified\">\n" +
        "		<li class=\"active\"><a href=\"#theme\" data-toggle=\"tab\">\n" +
        "				<h4>主题</h4>\n" +
        "		</a></li>\n" +
        "		<li><a href=\"#wallpaper\" data-toggle=\"tab\">\n" +
        "				<h4>壁纸</h4>\n" +
        "		</a></li>\n" +
        "	</ul>\n" +
        "	<div class=\"tab-content\" style=\"height: 100%\">\n" +
        "		<div class=\"tab-pane fade in active hte-theme-list\" id=\"theme\" >\n" +
        "			<div class=\"hte-theme-item\" style=\"background-color: rgb(31,91,183);\" data-theme=\"blue\"></div>\n" +
        "			<div class=\"hte-theme-item\" style=\"background-color: rgb(89,183,40);\" data-theme=\"green\"></div>\n" +
        "			<div class=\"hte-theme-item\" style=\"background-color: rgb(155,29,171);\" data-theme=\"purple\"></div>\n" +
        "			<div class=\"hte-theme-item\" style=\"background-color: rgb(204,53,53);\" data-theme=\"red\"></div>\n" +
        "			<div class=\"hte-theme-item\" style=\"background-color: rgb(208, 150, 21);\" data-theme=\"orange\"></div>\n" +
        "			<div class=\"hte-theme-item\" style=\"background-color: rgb(84, 74, 74);\" data-theme=\"gray\"></div>\n" +
        "			<div class=\"hte-theme-item\" style=\"background-color: rgb(47, 128, 82);\" data-theme=\"cyan\"></div>\n" +
        "		</div>\n" +
        "		<div class=\"tab-pane fade hte-wallpaper-list\" id=\"wallpaper\">\n" +
        "				<ul>\n" +
        "					<li class=\"wallpaper-actions\">\n" +
        "						<div style=\"width:100%\">\n" +
        "							<button class='btn btn-default btn-upload-wallpaper' >上传壁纸</button>\n" +
        "							<input type=\"file\" class=\"wallpaper-upload-file\" style=\"display:none\"/>\n" +
        "							<button class='btn btn-default btn-link-wallpaper' >使用网络图片</button>\n" +
        "						</div>\n" +
        "					</li>\n" +
        "					<li><img src=\"images/wallpaper/10.jpg\"></li>\n" +
        "					<li><img src=\"images/wallpaper/11.jpg\"></li>\n" +
        "					<li><img src=\"images/wallpaper/football.jpg\"></li>\n" +
        "					<li><img src=\"images/wallpaper/fifa.jpg\"></li>\n" +
        "					<li><img src=\"images/wallpaper/wallpaper.jpg\"></li>\n" +
        "				</ul>\n" +
        "		</div>\n" +
        "	</div>\n" +
        "</div>\n" +
        "</div>");
})();