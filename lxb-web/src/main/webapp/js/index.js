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
                vm.menuList = r.list;
            });
        },
        showMenu: function (url) {
            vm.main = url;
        },
        logout: function () {
            window.location.href= "sys/logout";
        }
    },
    created: function () {
        this.getMenuList();
    }
})

//iframe自适应
$(window).on('resize', function() {
    var $content = $('.content');
    $content.height($(this).height() - 120);
    $content.find('iframe').each(function() {
        $(this).height($content.height());
    });
}).resize();