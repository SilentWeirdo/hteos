(function () {

    /**
     * @class HteOS.storage.RemoteStorage
     */
    HteOS.storage.Remote = {

        server: HteOS.server || "http://localhost:8080",

        init: function () {

            var self = this;

            //注册事件同步到后台
            HteOS.EventManager.on("hte.group.reindex", function (groups) {
                var index = 1, indexes = [];
                for (var id in groups) {
                    var group = groups[id].group;
                    indexes.push({
                        id: group.id,
                        index: group.index
                    });
                }
                /**
                 * 保存分组的下标
                 */
                self.saveGroupIndex(indexes);
            });

            //注册事件同步到后台
            HteOS.EventManager.on("hte.group.change", function (group) {
                var index = 1,children = [],apps = group.apps;
                for(var appId in apps){
                    var app = apps[appId];
                    children.push({
                        id: appId,
                        index:app.indexOf()
                    });
                };
                /**
                 * 保存分组的下标
                 */
                self.saveGroup({
                    id: group.id,
                    apps: children
                });
            });

            HteOS.EventManager.on("hte.group.rename",function (group,oldName,newName) {
                self.saveGroupName(group);
            });

            HteOS.EventManager.on("hte.group.create",function (group) {
                self.saveNewGroup(group);
            });

            //注册事件同步到后台
            HteOS.EventManager.on("hte.wallpaper.change", function (wallpaper) {
                self.saveWallpaper(wallpaper);
            });

            //注册事件同步到后台
            HteOS.EventManager.on("hte.theme.change", function (theme) {
                self.saveTheme(theme);
            });

            //注册事件同步到后台
            HteOS.EventManager.on("hte.app.hide", function (app) {
                self.hideApp(app);
            });

            //注册事件同步到后台
            HteOS.EventManager.on("hte.app.show", function (app) {
                self.showApp(app);
            });

            //注册事件同步到后台
            HteOS.EventManager.on("hte.app.destroy", function (app) {
                self.destroyApp(app);
            });

            //注册事件同步到后台
            HteOS.EventManager.on("hte.app.resize", function (app, size) {
                self.saveAppSize(app, size);
            });

            HteOS.EventManager.on("hte.favorites.sort", function (favorites) {
                self.saveFavorites(favorites);
            });

            //注册事件同步到后台
            HteOS.EventManager.on("hte.favorite.add", function (app) {
                self.addFavorite(app);
            });

            //注册事件同步到后台
            HteOS.EventManager.on("hte.favorite.remove", function (id) {
                self.removeFavorite(id);
            });

            //注册事件同步到后台
            HteOS.EventManager.on("hte.mode.change", function (mode) {
                self.saveMode(mode);
            });

            //注册事件同步到后台
            HteOS.EventManager.on("hte.shortcutSize.change", function (shortcutSize) {
                self.saveShortcutSize(shortcutSize);
            });
        }
        ,

        getEnvironment: function () {
            return $.getJSON(this.server + "/api/environment");
        },

        /**
         * 保存分组排序
         * @returns {*}
         */
        saveGroupIndex: function (option) {
            var xhr = $.ajax({
                url: this.server + "/api/group/sort",
                method: 'POST',
                data: JSON.stringify(option),
                contentType: 'application/json;charset=utf-8'
            });
            xhr.done(function (data) {
                if (!data.success) {
                    HteOS.Notification.show("", "应用消息", data.message, true)
                }
            })
            xhr.fail(function (error) {
                HteOS.Notification.show("", "应用消息", "保存设置失败：" + error.status, true)
            });
            return xhr;
        } ,

        /**
         * 保存分组内的应用
         * @param group
         * @returns {*}
         */
        saveGroup : function(group){
            var xhr = $.ajax({
                url: this.server + "/api/group/save",
                method: 'POST',
                data: JSON.stringify(group),
                contentType: 'application/json;charset=utf-8'
            });
            xhr.done(function (data) {
                if (!data.success) {
                    HteOS.Notification.show("", "应用消息", data.message, true)
                }
            })
            xhr.fail(function (error) {
                HteOS.Notification.show("", "应用消息", "保存设置失败：" + error.status, true)
            });
            return xhr;
        },

        /**
         * 保存新的分组
         * @param group
         * @returns {*}
         */
        saveNewGroup : function(group){
            var apps = [];
            group.keys.forEach(function (id,index) {
                apps.push({
                    id : id,
                    index : index
                });
            });

            var xhr = $.ajax({
                url: this.server + "/api/group/create",
                method: 'POST',
                data: JSON.stringify(apps),
                contentType: 'application/json;charset=utf-8'
            });
            xhr.done(function (data) {
                if (!data.success) {
                    HteOS.Notification.show("", "应用消息", data.message, true)
                }else{
                    HteOS.app.GroupManager.unregister(group);
                    group.id = data.data;
                    HteOS.app.GroupManager.register(group);
                }
            });
            xhr.fail(function (error) {
                HteOS.Notification.show("", "应用消息", "创建分组失败：" + error.status, true)
            });
            return xhr;
        },

        /**
         * 保存分组名称
         * @param group
         * @returns {*}
         */
        saveGroupName : function(group){
            var xhr = $.ajax({
                url: this.server + "/api/group/rename",
                method: 'POST',
                data: {
                    id: group.id,
                    name: group.name
                }
            });
            xhr.done(function (data) {
                if (!data.success) {
                    HteOS.Notification.show("", "应用消息", data.message, true)
                }
            })
            xhr.fail(function (error) {
                HteOS.Notification.show("", "应用消息", "保存设置失败：" + error.status, true)
            });
            return xhr;
        },

        /**
         * 保存收藏夹栏
         * @param favorites
         * @returns {*}
         */
        saveFavorites: function(favorites){
            var xhr = $.ajax({
                url: this.server + "/api/favorites/save",
                method: 'POST',
                data: JSON.stringify(favorites),
                contentType: 'application/json;charset=utf-8'
            });
            xhr.done(function (data) {
                if (!data.success) {
                    HteOS.Notification.show("", "应用消息", data.message, true)
                }
            })
            xhr.fail(function (error) {
                HteOS.Notification.show("", "应用消息", "保存设置失败：" + error.status, true)
            });
            return xhr;
        },


        /**
         * 保存模式
         * @param mode
         * @returns {*}
         */
        saveMode: function (mode) {
            var deferred = $.Deferred();
            var xhr = $.post(this.server + "/api/saveMode", {mode: mode});
            xhr.success(function (result) {
                if (!result.success) {
                    HteOS.Notification.show("", "应用消息", result.message, true);
                } else {
                    deferred.resolve();
                }
            });
            xhr.error(function (error) {
                HteOS.Notification.show("", "应用消息", "保存背景图片失败:" + error.status, true);
                deferred.reject(error);
            })
            return deferred.promise();
        },

        /**
         * 保存图标尺寸
         * @param shortcutSize
         * @returns {*}
         */
        saveShortcutSize: function (shortcutSize) {
            var deferred = $.Deferred();
            var xhr = $.post(this.server + "/api/saveShortcutSize", {shortcutSize: shortcutSize});
            xhr.success(function (result) {
                if (!result.success) {
                    HteOS.Notification.show("", "应用消息", result.message, true);
                } else {
                    deferred.resolve();
                }
            });
            xhr.error(function (error) {
                HteOS.Notification.show("", "应用消息", "保存背景图片失败:" + error.status, true);
                deferred.reject(error);
            })
            return deferred.promise();
        }
        ,

        /**
         * 保存墙纸
         * @param wallpaper
         * @returns {*}
         */
        saveWallpaper: function (wallpaper) {
            var deferred = $.Deferred();
            var xhr = $.post(this.server + "/api/saveWallpaper", {wallpaper: wallpaper});
            xhr.success(function (result) {
                if (!result.success) {
                    HteOS.Notification.show("", "应用消息", result.message, true);
                } else {
                    deferred.resolve();
                }
            });
            xhr.error(function (error) {
                HteOS.Notification.show("", "应用消息", "保存背景图片失败:" + error.status, true);
                deferred.reject(error);
            })
            return deferred.promise();
        }
        ,

        /**
         * 保存主题
         * @param theme
         * @returns {*}
         */
        saveTheme: function (theme) {
            var deferred = $.Deferred();
            var xhr = $.post(this.server + "/api/saveTheme", {theme: theme});
            xhr.success(function (result) {
                if (!result.success) {
                    HteOS.Notification.show("", "应用消息", result.message, true);
                } else {
                    deferred.resolve();
                }
            });
            xhr.error(function (error) {
                HteOS.Notification.show("", "应用消息", "保存背景图片失败:" + error.status, true);
                deferred.reject(error);
            })
            return deferred.promise();
        }
        ,

        /**
         * 保存磁贴尺寸
         * @param app
         * @param size
         * @returns {*}
         */
        saveAppSize: function (app, size) {
            var deferred = $.Deferred();
            var xhr = $.post(this.server + "/api/app/resize", {id: app.id, size: size});
            xhr.success(function (result) {
                if (!result.success) {
                    HteOS.Notification.show("", "应用消息", result.message, true);
                } else {
                    deferred.resolve();
                }
            });
            xhr.error(function (error) {
                HteOS.Notification.show("", "应用消息", "保存设置失败:" + error.status, true);
                deferred.reject(error);
            })
            return deferred.promise();
        },

        destroyApp: function (app) {
            var deferred = $.Deferred();
            var xhr = $.post(this.server + "/api/app/uninstall", {id: app.id});
            xhr.success(function (result) {
                if (!result.success) {
                    HteOS.Notification.show("", "应用消息", result.message, true);
                } else {
                    deferred.resolve();
                }
            });
            xhr.error(function (error) {
                HteOS.Notification.show("", "应用消息", "保存设置失败:" + error.status, true);
                deferred.reject(error);
            })
            return deferred.promise();
        }
        ,

        /**
         * 隐藏磁贴
         * @param app
         * @returns {*}
         */
        hideApp: function (app) {
            var deferred = $.Deferred();
            var xhr = $.post(this.server + "/api/app/hide", {id: app.id});
            xhr.success(function (result) {
                if (!result.success) {
                    HteOS.Notification.show("", "应用消息", result.message, true);
                } else {
                    deferred.resolve();
                }
            });
            xhr.error(function (error) {
                HteOS.Notification.show("", "应用消息","保存设置失败:" + error.status, true);
                deferred.reject(error);
            })
            return deferred.promise();
        }
        ,

        /**
         * 显示磁贴
         * @param app
         * @returns {*}
         */
        showApp: function (app) {
            var deferred = $.Deferred();
            var xhr = $.post(this.server + "/api/app/show", {id: app.id});
            xhr.success(function (result) {
                if (!result.success) {
                    HteOS.Notification.show("", "应用消息", result.message, true);
                } else {
                    deferred.resolve();
                }
            });
            xhr.error(function (error) {
                HteOS.Notification.show("", "应用消息","保存设置失败:" + error.status, true);
                deferred.reject(error);
            })
            return deferred.promise();
        }
        ,

        /**
         * 添加收藏
         * @param app
         * @returns {*}
         */
        addFavorite: function (app) {
            var deferred = $.Deferred();
            var xhr = $.post(this.server + "/api/favorite/add", {id: app.id});
            xhr.success(function (result) {
                if (!result.success) {
                    HteOS.Notification.show("", "应用消息", result.message, true);
                } else {
                    deferred.resolve();
                }
            });
            xhr.error(function (error) {
                HteOS.Notification.show("", "应用消息", "保存设置失败:" + error.status, true);
                deferred.reject(error);
            })
            return deferred.promise();
        }
        ,

        /**
         * 删除收藏
         * @param id
         * @returns {*}
         */
        removeFavorite: function (id) {
            var deferred = $.Deferred();
            var xhr = $.post(this.server + "/api/favorite/remove", {id: id});
            xhr.success(function (result) {
                if (!result.success) {
                    HteOS.Notification.show("", "应用消息", result.message, true);
                } else {
                    deferred.resolve();
                }
            });
            xhr.error(function (error) {
                HteOS.Notification.show("", "应用消息","保存设置失败:" + error.status, true);
                deferred.reject(error);
            })
            return deferred.promise();
        }
    }
})();