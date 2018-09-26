(function () {
    //个人中心
    HteOS.controller.Profile = function () {

        this.onViewRender = function () {

            //FIXME 通过ID判断是否登录，后续优化
            if (!HteOS.Preference.logon) {
                $(".hte-profile-name").hide();
                $(".hte-profile-footer").hide();
            }else{
                $(".hte-profie-login").hide();
                $("#hte-user-name").text( HteOS.Preference.nickName);
                if(HteOS.Preference.residence){
                    $("#hte-user-position").text(HteOS.Preference.residence);
                }
            }

            $("#profile-lock-action").click(function () {
                HteOS.Locker.lock();
            });

            this.initController();
        }

        this.initController = function () {

            var me = this;

            $("#qlogin-btn").click(function () {
                var src = "https://graph.qq.com/oauth/show?which=ConfirmPage&display=pc&client_id=101095186" +
                    "&response_type=token&scope=all&redirect_uri=http%3A%2F%2Fdemo.hteos.com%2Fqq_login_callback.jsp";
                HteOS.TaskManager.start({
                    id : 'qqlogin',
                    name : "QQ登录",
                    template : "<iframe frameborder='0' src=\"" + src + "\"></iframe>",
                    icon : "images/qq.png",
                    mode : 'window',
                    width:800,
                    height:450,
                    resizable : false,
                    maximizable : false
                });
            });

            $("#login-btn").click(function () {
                HteOS.Messager.show({
                    size: 'small',
                    title: "登录",
                    message: '请输入账号和密码进行登录',
                    fields: [{name: 'username',label: '用户名/邮箱'},
                        {name: 'password',label: '密码',inputType: "password"}],
                    buttons: [{cls: 'btn-default',text: '登录',key: 'login', autofocus: true},
                        {cls: 'btn-default',key: 'no',text: '取消',autofocus: true}],
                    callback: function (btn, values) {
                        if(btn == 'login'){
                            me.doLogin(values);
                            return false;
                        }
                    }
                });
            });

            $("#register-btn").click(function () {
                HteOS.Messager.show({
                    size: 'small',
                    title: "注册",
                    message: '请输入邮箱和密码进行注册',
                    fields: [{ name: '邮箱', label: '邮箱',inputType: "email"},
                        {name: 'password',label: '密码',inputType: "password"},
                        {name: 'password2',label: '确认密码',inputType: "password"}],
                    buttons: [{cls: 'btn-default',text: '确定',key: 'commit',autofocus: true},
                        {cls: 'btn-default',key: 'cancel',text: '取消',autofocus: true
                        }],
                    callback: function (btn, values) {
                        if(btn == 'commit'){
                            me.doLogin(values);
                            return false;
                        }
                    }
                });
            });
        }

        this.doLogin = function (values) {
            $("[data-key='login']").attr("disabled", "disabled");
            $(".hte-messager-tip").html("正在登录...")
            var xhr = $.post('http://localhost:8080/login', values);
            xhr.success(function (result) {
                if (result.success) {
                    localStorage.setItem("hteos.token", result.data.token);
                    localStorage.setItem("hteos.theme",result.data.theme);
                    window.location.reload();
                    HteOS.Messager.hide();
                } else {
                    $(".hte-messager-tip").text(result.message);
                    $("[data-key='login']").removeAttr("disabled");
                }
            });
            xhr.error(function () {
                $(".hte-messager-tip").text("登录失败，请稍候再试");
                $("[data-key='login']").removeAttr("disabled");
            })
        }
    }

})();
