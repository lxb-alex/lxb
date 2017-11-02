<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>首页</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="hold-transition login-page">
    <div class="login-box" id="rrapp" v-cloak>
        <div class="login-logo">
            <b>首页</b>
        </div>
        <!-- /.login-logo -->
        <div class="login-box-body">
            <p class="login-box-msg">管理员登录</p>
            <div v-if="error" class="alert alert-danger alert-dismissible">
                <h4 style="margin-bottom: 0px;"><i class="fa fa-exclamation-triangle"></i> {{errorMsg}}</h4>
            </div>
            <div class="form-group has-feedback">
                <input type="text" class="form-control" v-model="username" placeholder="账号">
                <span class="glyphicon glyphicon-user form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="password" class="form-control" v-model="password" placeholder="密码">
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="text" class="form-control" v-model="captcha" @keyup.enter="login" placeholder="验证码">
                <span class="glyphicon glyphicon-warning-sign form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <img alt="如果看不清楚，请单击图片刷新！" class="pointer" :src="src" @click="refreshCode">
                &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" @click="refreshCode">点击刷新</a>
            </div>


            <div class="row">
                <div class="col-xs-8">
                    <div class="checkbox icheck">
                    </div>
                </div>
                <!-- /.col -->
                <div class="col-xs-4">
                    <button type="button" class="btn btn-primary btn-block btn-flat" @click="login">登录</button>
                </div>
                <!-- /.col -->
            </div>
            <!-- /.social-auth-links -->

        </div>
        <!-- /.login-box-body -->
    </div>
<!-- /.login-box -->
<script src="statics/libs/jquery.min.js"></script>
<script src="statics/libs/vue.min.js"></script>
<script src="statics/libs/bootstrap.min.js"></script>
<script src="statics/libs/jquery.slimscroll.min.js"></script>
<script src="statics/libs/fastclick.min.js"></script>
<script src="statics/libs/app.js"></script>
<script type="text/javascript">
    var vm = new Vue({
        el:'#rrapp',
        data:{
            username: '',
            password: '',
            captcha: '',
            error: false,
            errorMsg: '',
            src: 'captcha.jpg'
        },
        beforeCreate: function(){
            if(self != top){
                top.location.href = self.location.href;
            }
        },
        methods: {
            refreshCode: function(){
                this.src = "captcha.jpg?t=" + $.now();
            },
            login: function (event) {
                var data = "username="+vm.username+"&password="+vm.password+"&captcha="+vm.captcha;
                $.ajax({
                    type: "POST",
                    url: "sys/login",
                    data: data,
                    dataType: "json",
                    success: function(result){
                        if(result.code == 0){//登录成功
                            parent.location.href ='index.html';
                        }else{
                            vm.error = true;
                            vm.errorMsg = result.msg;

                            vm.refreshCode();
                        }
                    }
                });
            }
        }
    });
</script>
</body>
</html>
