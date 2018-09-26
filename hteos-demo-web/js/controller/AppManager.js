(function () {
    /**
     * @author LIQIU <service@hteos.com>
     * @class HteOS.component.AppManager 应用管理组件
     */
    HteOS.controller.AppManager = function () {
        var me = this;
        var html = "<%for(i = 0; i < groups.length; i ++) {%>" +
            "<%var group = groups[i],apps = group.apps;%>" +
                "<div class=\"hte-appmanager-group\">" +
                    "<div class=\"hte-appmanager-group-header\">" +
                        "<i class=\"glyphicon glyphicon-triangle-right collapser\"></i>" +
                        "<i class=\"glyphicon glyphicon-triangle-bottom expander\"></i>" +
                        "<%=group.name%>" +
                    "</div>" +
                    "<div class=\"hte-appmanager-group-body\">" +
                        "<%for(j = 0; j < apps.length; j ++) {%>" +
                            "<div id=\"hte-appmanager-app-<%=apps[j].id%>\" data-app=\"<%=apps[j].id%>\" class=\"hte-appmanager-app\">"
                            + "<div class=\"hte-appmanager-app-icon\"><img src=\"<%=apps[j].icon%>\"></div>"
                            + "<div class=\"hte-appmanager-app-name\"><%=apps[j].name%></div>" +
                            "</div>" +
                        "<%}%>" +
                    "</div>" +
                "</div>" +
            "<%}%>";

        this.tpl = HteOS.Template.compile(html);

        $(document).on("click.hte.appmanager.app.api", ".hte-appmanager-app", function (event) {
            var overview = $(this);
            HteOS.dock.DockManager.clear();
            HteOS.TaskManager.launch(HteOS.AppManager.get(overview.data("app")));
            event.stopPropagation();
        });

        $(document).on("click.hte.appmanager.group.api", ".hte-appmanager-group-header", function (event) {
            $(this).parent().toggleClass("collapsed");
            event.stopPropagation();
        });

        HteOS.EventManager.on("hte.app.destroy", function (app) {
            me.remove(app)
        });

        HteOS.EventManager.on("hte.app.move", function () {
            me.render();
        });

        //监听点击事件
        HteOS.EventManager.on('hte.app.create',
            function () {
                me.render();
            });

        //监听点击事件
        HteOS.EventManager.on('hte.group.sort',
            function () {
                me.render();
            });

        //监听点击事件
        HteOS.EventManager.on('hte.group.rename',
            function () {
                me.render();
            });
    };

    HteOS.apply(HteOS.controller.AppManager.prototype, {

        rendered: false,
        /**
         * 初始化全局管理组件
         */
        onViewRender: function () {
            var me = this;
            var el = me.getEl();

            //渲染
            me.render();
            //初始化右键
            me.initContextMenu();
            //初始化查询
            me.initSearch();
        },

        render: function () {
            var me = this;
            var groups = this.collect();
            var html = this.tpl({
                groups: groups
            });
            me.getEl().find(".hte-appmanager-content").html(html);
            me.rendered = true;
        },

        initSearch: function(){
            $("#app-search-btn").click(search);
            $("#app-search-input").keyup(function (event) {
                search();
            });

            function search() {
                var val = $("#app-search-input").val();
                if (!val) {
                    $(".hte-appmanager-item").show();
                } else {
                    $(".hte-appmanager-group").hide();
                }
                var apps = HteOS.AppManager.apps;
                for (var a in apps) {
                    var app = apps[a];
                    if (app.name.indexOf(val) >= 0) {
                        $("#hte-appmanager-item-" + app.id).show();
                    } else {
                        $("#hte-appmanager-item-" + app.id).hide();
                    }
                }
            }
        },

        collect: function () {
            var groups = HteOS.app.GroupManager.groups;
            var result = [];
            for (var a in groups) {
                if (groups[a].isEmpty !== true) {
                    var group = {};
                    group.name = groups[a].name;
                    group.index = groups[a].index;
                    var apps = groups[a].apps;
                    if (apps) {
                        var array = [];
                        for (var id in apps) {
                            array.push(apps[id]);
                        }
                        array.sort(function (a, b) {
                            if (a.index > b.index) {
                                return 1;
                            }
                            return -1;
                        });
                        group.apps = array;
                        result.push(group);
                    }
                }
            }
            result.sort(function (a, b) {
                if (a.index > b.index) {
                    return 1;
                }
                return -1;
            });
            return result;
        },

        getEl: function () {
            return $(".hte-appmanager");
        },

        remove: function (app) {
            $("#hte-appmanager-item-" + app.id).remove();
        },

        initContextMenu: function () {
            var contextmenu = new HteOS.menu.Menu({
                selector: ".hte-appmanager-app",
                cls: "hte-appmanager-contextmenu",
                items: [{
                    id: 'open',
                    icon: 'glyphicon glyphicon-new-window',
                    name: '打开',
                    handler: function () {
                        var item = $(".hte-appmanager-app.selected");
                        HteOS.TaskManager.launch(HteOS.AppManager.get(item.data("app")));
                        HteOS.component.AppManager.hide();
                    }
                }, {
                    id: 'show',
                    icon: 'glyphicon glyphicon-eye-open',
                    name: '显示',
                    handler: function () {
                        var item = $(".hte-appmanager-app.selected");
                        HteOS.AppManager.get(item.data("app")).show();
                    }
                }, {
                    id: 'hide',
                    icon: 'glyphicon glyphicon-eye-close',
                    name: '隐藏',
                    handler: function () {
                        var item = $(".hte-appmanager-app.selected");
                        HteOS.AppManager.get(item.data("app")).hide();
                    }
                }, '-', {
                    id: 'uninstall',
                    icon: 'glyphicon glyphicon-trash',
                    name: '卸载',
                    handler: function () {
                        var item = $(".hte-appmanager-app.selected");
                        HteOS.AppManager.get(appmanager.data("app")).uninstall();
                    }
                }]
            });
            contextmenu.on("show",function (target) {
                var me = this,target = $(target);
                if (!target.hasClass("hte-appmanager-app")) {
                    target = target.parent(".hte-appmanager-app");
                }
                $(target).addClass("selected");
                var app = HteOS.AppManager.get($(target).data("app"));
                if (app) {
                    me[app.path ? 'enable' : 'disable']('open');
                    if (app.hidden) {
                        me.hideItem("hide");
                        me.showItem("show");
                    } else {
                        me.showItem("hide");
                        me.hideItem("show");
                    }

                    if (app.isNative) {
                        me.disable("uninstall");
                    } else {
                        me.enable("uninstall");
                    }
                }
            });
            contextmenu.on("hide",function () {
                $(".hte-appmanager-app.selected").removeClass("selected");
            });
        }
    });

    //注册模板
    HteOS.TemplateManager.register('appmanager.html',"<div class=\"hte-appmanager hte-webkit-scrollbar\" >	\n" +
        "	<div class=\"hte-appmanager-body\" >	\n" +
        "		<input id=\"app-search-input\" type=\"text\" class=\"form-control\" placeholder=\"输入关键字进行搜索\">\n" +
        "		<div class=\"hte-appmanager-content\">\n" +
        "		</div>\n" +
        "	</div>\n" +
        "</div>");

})();