(function () {

    $.ajaxSetup({
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true
    });

    HteOS.controller = {};
    HteOS.EventManager.on("ready", function () {
        initEnviroment();
        $(".hte-preloader-text").text("正在加载配置..");
        //注册存储
        HteOS.StorageManager.register(HteOS.storage.Remote);
        HteOS.StorageManager.getStorage().getEnvironment().done(function (enviroment) {
            $(".hte-preloader-text").text("正在加载桌面..");

            //动态加载皮肤
            var promise = HteOS.Loader.load(HteOS.basePath + "css/" + enviroment.preference.theme + "/hteos.min.css");
            promise.done(function () {
                window.setTimeout(function () {
                    HteOS.Bootstrap.start(enviroment);
                }, 300);
            });
        }).fail(function () {
            $(".hte-preloader-icon").hide();
            $(".hte-preloader-text").text("加载桌面配置失败，请稍后再试!");
            return;
        });


    });

    /**
     * 初始化桌面环境
     */
    function initEnviroment() {

        HteOS.logo = "images/logo.png";
        HteOS.title = "<span class=\"segoe\">HteOS</span> - <small>与众不同的 <span class=\"segoe\">Web</span></small>";

        HteOS.EventManager.on("hteos.started", function () {
            $(".hte-preloader").fadeOut();
            showWelcom();
        });

        //自定义右键菜单
        customContextMenu();
        //自定义移动导航栏
        customDockbar();
        //自定义任务栏
        customTaskBar();
    }

    /**
     * 定制移动端底部栏
     */
    function customDockbar() {


        var items = [{
            icon: "glyphicon glyphicon-user",
            name: "我的HteOS",
            handler: function () {
                var app = HteOS.AppManager.getByKey("myhteos");
                HteOS.TaskManager.launch(app);
            }
        }, {
            icon: "glyphicon glyphicon-folder-close",
            name: "应用市场",
            handler: function () {
                HteOS.TaskManager.launch(HteOS.app.AppManager.getByKey('myhteos'));
            }
        }, {
            icon: "glyphicon glyphicon-list",
            name: "我的应用",
            handler: function () {
                HteOS.TaskManager.start({
                    id: 'appmanager',
                    templateUrl: "appmanager.html",
                    controller: "HteOS.controller.AppManager",
                    name: "应用管理"
                });
            }
        }, {
            icon: "glyphicon glyphicon-cog",
            name: "个性化设置",
            handler: function () {
                HteOS.TaskManager.launch(HteOS.app.AppManager.getByKey('settings'));
            }
        }, {
            icon: "glyphicon glyphicon-th",
            name: "切换模式",
            handler: function () {
                if (HteOS.Preference.mode == 'desktop') {
                    HteOS.switchMode("metro");
                } else {
                    HteOS.switchMode("desktop");
                }
            }
        }];

        HteOS.Dockbar.items = HteOS.Dockbar.items.concat(items);
    }

    function customContextMenu() {
        var items = [{
            id: 'locker',
            icon: 'glyphicon glyphicon-lock',
            name: '锁定屏幕',
            handler: function () {
                if (HteOS.User.logon === true) {
                    HteOS.Locker.lock();
                }
            }
        }, {
            id: 'taskmanager',
            icon: 'glyphicon glyphicon-th-list',
            name: '任务管理',
            handler: function () {
                HteOS.TaskManager.start({
                    id: 'taskmanager',
                    name: "任务管理",
                    templateUrl: "taskmanager.html",
                    controller: "HteOS.controller.TaskManager",
                    shell: 'window',
                    dependencies: "https://cdn.bootcss.com/vue/2.5.17-beta.0/vue.min.js"
                });
            }
        }, '-', {
            id: 'myapp',
            icon: 'glyphicon glyphicon-th',
            name: '应用管理',
            items: [{
                id: 'manager',
                icon: 'glyphicon glyphicon-list',
                name: '我的应用',
                handler: function () {
                    HteOS.TaskManager.start({
                        id: 'appmanager',
                        name: "我的应用",
                        templateUrl: "tpl/appmanager.html",
                        controller: "HteOS.controller.AppManager",
                        shell: 'dock',
                        dependencies: "https://cdn.bootcss.com/vue/2.5.17-beta.0/vue.min.js"
                    });
                }
            }, {
                id: 'store',
                icon: 'glyphicon glyphicon-folder-close',
                name: '应用市场',
                handler: function () {
                    var app = HteOS.AppManager.getByKey("store");
                    if (app) {
                        HteOS.TaskManager.launch(app);
                    } else {
                        HteOS.Notification.show("", "应用提示",
                            "运行[应用市场]失败，请先安装[应用市场]应用");
                    }
                }
            }]
        }, {
            id: 'wallpaper',
            icon: 'glyphicon glyphicon-cog',
            name: '个性化设置',
            handler: function () {
                var app = HteOS.AppManager.getByKey("settings");
                if (app) {
                    HteOS.TaskManager.launch(app);
                } else {
                    HteOS.Notification.show("", "应用提示",
                        "运行[个性化设置]，请先安装[个性化设置]应用");
                }
            }
        }, {
            id: 'profile',
            icon: 'glyphicon glyphicon-user',
            name: '我的HteOS',
            handler: function () {
                var app = HteOS.AppManager.getByKey("myhteos");
                if (app) {
                    HteOS.TaskManager.launch(app);
                } else {
                    HteOS.Notification.show("", "应用提示",
                        "运行[个人中心]，请先安装[个人中心]应用");
                }
            }
        }, '-', {
            id: 'about',
            name: '关于HteOS',
            icon: 'glyphicon glyphicon-info-sign',
            handler: function () {
                HteOS.Messager.alert("关于HteOS", HteOS.introduction);
            }
        }];

        HteOS.ContextMenu.items = HteOS.ContextMenu.items.concat(items);
    };


    /**
     * 定制桌面任务栏
     */
    function customTaskBar() {

        var actions = [{
            "title": "我的HteOS",
            "icon": "glyphicon glyphicon-user",
            "position": "left",
            "app": "myhteos",
        }, {
            "title": "我的应用",
            "icon": "glyphicon glyphicon-list",
            "position": "left",
            "handler": function () {
                HteOS.TaskManager.start({
                    id: "appmanager",
                    name: "我的应用",
                    templateUrl: "tpl/appmanager.html",
                    shell: "dock",
                    controller: "HteOS.controller.AppManager",
                    dependencies: "https://cdn.bootcss.com/vue/2.5.17-beta.0/vue.min.js"
                });
            }
        }, {
            title: '关于HteOS',
            icon: 'glyphicon glyphicon-info-sign',
            handler: showWelcom,
            index: 2
        }, {
            "title": "个性化设置",
            "icon": "glyphicon glyphicon-cog",
            "handler": function () {
                HteOS.TaskManager.start({
                    id: "setting",
                    name: "个性化设置",
                    templateUrl: "setting.html",
                    shell: "dock",
                    controller: "HteOS.controller.Setting"
                });
            }
        }, {
            "title": "应用市场",
            "icon": "glyphicon glyphicon-folder-close",
            "position": "right",
            "app": "store"
        }, {
            "title": "即时通讯",
            "position": "right",
            "icon": "glyphicon glyphicon-comment",
            "handler": function () {
                HteOS.TaskManager.start({
                    id: "messager",
                    name: "即时通讯",
                    templateUrl: "tpl/messager.html",
                    shell: "dock",
                    controller: "HteOS.controller.Messages"
                });
            }
        }, {
            title: '反馈意见',
            icon: 'glyphicon glyphicon-edit',
            handler: function () {
                HteOS.Messager.prompt("意见反馈", "我们迫切希望得到您有价值的反馈，有您的反馈，我们会做的更好！", [{
                    name: 'email',
                    label: '联系方式',
                    value: "",
                    inputType: "email"
                }, {
                    name: 'content',
                    label: '反馈意见',
                    multiLine: true
                }], function (btn, values) {
                    if (btn == 'yes') {
                        if (values.content) {
                            saveFeedback(values);
                        }
                    }
                });
            }
        }];

        HteOS.Taskbar.actions = HteOS.Taskbar.actions.concat(actions);
    }

    function showWelcom() {
        window.setTimeout(function () {
            HteOS.Messager.alert("欢迎", HteOS.introduction);
        }, 100);
    }

})();
