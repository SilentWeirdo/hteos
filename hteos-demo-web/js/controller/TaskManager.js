(function () {
    /**
     * @author qiu.li <service@hteos.com>
     * @class HteOS.component.AppManager 应用管理组件
     */
    HteOS.controller.TaskManager = function () {
        this.onViewRender = function () {
            var self = this;
            function collect() {
                var tasks = HteOS.TaskManager.tasks;
                var taskData = [];
                for (var taskId in tasks) {
                    var task = tasks[taskId];
                    taskData.push({
                        id: task.id,
                        name: task.name,
                        startTime: task.startTime,
                        templateUrl: task.templateUrl,
                        icon: task.icon
                    });
                }
                self.vm.tasks = taskData;
            }

            this.vm = new Vue({
                el: "#hte-module-" + this.module.id,
                data: {
                    tasks: []
                }
            });

            HteOS.EventManager.on("hte.task.start", collect, this);
            HteOS.EventManager.on("hte.task.stop", collect, this);
            collect();
        }
    }

    HteOS.TemplateManager.register("taskmanager.html", "<div class=\"hte-taskmanager\">\n" +
        "    <table class=\"table table-condensed table-hover\">\n" +
        "        <thead>\n" +
        "        <tr>\n" +
        "            <th>编号</th>\n" +
        "            <th style='text-align: left'>名称</th>\n" +
        "            <th style='text-align: left'>URL</th>\n" +
        "            <th>时间</th>\n" +
        "        </tr>\n" +
        "        </thead>\n" +
        "        <tbody>\n" +
        "        <tr v-for=\"(task,index) in tasks\">\n" +
        "            <td>{{index + 1}}</td>\n" +
        "            <td  style='text-align: left'><img :src=\"task.icon\">{{task.name}}</td>\n" +
        "            <td  style='text-align: left'>{{task.templateUrl}}</td>\n" +
        "            <td>{{task.startTime}}</td>\n" +
        "        </tr>\n" +
        "        </tbody>\n" +
        "    </table>\n" +
        "</div>");

})();