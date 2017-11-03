/**
 *
 * User: Liaoxb
 * Date：2017/11/3 0003
 * Time：14:30
 */
var index_vm = new Vue({
    el: "#index_vm",
    data: {
        user:{},
        menuList:{},
        main: "",
        password: "",
        newPassword: "",
        navTitle: "控制台"
    },
    methods: { // 定义方法
        getMenuList: function () {
            $.getJSON("sys/menu/list", function (r) {
                index_vm.menuList = r.Rows;
            });
        }
    },
    created: function () {
        this.getMenuList();
    }


})