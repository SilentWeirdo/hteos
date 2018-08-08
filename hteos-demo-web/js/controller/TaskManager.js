(function () {
    /**
     * @author qiu.li <service@hteos.com>
     * @class HteOS.component.AppManager 应用管理组件
     */
    HteOS.controller.TaskManager = function () {

        this.tpl = HteOS.Template.compile("<%var index = 1 ;for(var taskId in tasks){%>" +
            "<% var task = tasks[taskId];%>" +
            "        <tr>\n" +
            "          <td><%=index%></td>\n" +
            "          <td align='left'><img src='<%=task.icon%>' /><%=task.name%></td>\n" +
            "          <td align='left'><%=task.templateUrl%></td>\n" +
            "          <td><%=task.startTime%></td>\n" +
            "        </tr>\n" +
            "<%index++;}%>");

        this.onShellRendered = function () {
            this.shellRendered = true;
            this.render();
        }

        this.render = function () {
            if (this.shellRendered !== true) {
                return;
            }

            var tasks = HteOS.TaskManager.tasks;
            var hasTask = false;
            for (var taskId in tasks) {
                hasTask = true;
                break;
            }

            var content
            if (hasTask) {
                content = this.tpl({tasks: tasks});
            } else {
                content = "<tr><td colspan='4'>没有正在运行的任务</td></tr>"
            }

            this.shell.getEl().find("tbody").html(content);
        }

        HteOS.EventManager.on("hte.task.start", this.render,this);
        HteOS.EventManager.on("hte.task.stop", this.render,this);

    }

    HteOS.TemplateManager.register("taskmanager.html", "<div class=\"hte-taskmanager\"'><table class=\"table table-condensed table-hover\">\n" +
        "      <thead>\n" +
        "        <tr>\n" +
        "          <th>编号</th>\n" +
        "          <th style='text-align: left'>名称</th>\n" +
        "          <th style='text-align: left'>URL</th>\n" +
        "          <th>时间</th>\n" +
        "        </tr>\n" +
        "      </thead>\n" +
        "      <tbody>\n" +
        "      </tbody>\n" +
        "    </table></div>");

})();