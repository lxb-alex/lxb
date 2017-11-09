/**
 *
 * User: Liaoxb
 * Date：2017/11/3 0003
 * Time：14:30
 */
 var vm =new Vue({
    el: "#apps",
    data: {
        user:{}, /*未使用*/
        menuList:{},
        main: "", /*未使用*/
        password: "", /*未使用*/
        newPassword: "", /*未使用*/
        navTitle: "控制台"
    },
    methods: { // 定义方法
        getMenuList: function () {
            $.getJSON("sys/menu/list", function (r) {
                vm.menuList = r.Rows;
            });
        },
        showMenu: function (url) {
            vm.main = url;
        }
    },
    created: function () {
        this.getMenuList();
    }
})