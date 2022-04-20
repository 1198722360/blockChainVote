var checkCode="";
var second=180;//时间默认值
$(document).ready(function () {

    $("#sendMail").click(function () {
        if ($("#username").val() == "") {
            $("#p1").html('邮箱不能为空');
        } else if (!$("#username").val().match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)) {
            $("#p1").html('邮箱格式有误');
        } else {
            var ifMailExit = true;
            $.post("searchUsernameServlet", {"username": $("#username").val()}, function (res) {
                if (res == false) {//邮箱不存在则继续
                    $("#p1").html('');
                    //不存在则发验证码
                    $.post("sendMailServlet", {"username": $("#username").val()}, function (res) {
                        checkCode=res;
                    });

                   var countdown=window.setInterval(function(){
                        $("#sendMail").html(' '+second);
                       $("#sendMail").attr("disabled",true);
                       second--;
                        if (second<-1)
                        {
                            $("#sendMail").html('重新发送');
                            $("#sendMail").attr("disabled",false);
                            second=180;
                            window.clearInterval(countdown);
                        }
                    }, 1000);
                } else {//邮箱已存在
                    $("#p1").html('该邮箱已被注册');
                }
            })
        }
    });
    $("#password").blur(function (){
        if ($("#password").val().length<6||$("#password").val().length>16)
        {
            $("#p2").html('密码长度应在6到12位之间');
        }
        else {
            $("#p2").html('');
        }
    });
    $("#checkPassword").blur(function (){

        if ($("#password").val()!=$("#checkPassword").val())
        {
            $("#p3").html('两次密码不匹配');
        }
        else {
            $("#p3").html('');
        }
    });
    $("#regist").click(function (){
        if ($("#password").val()==$("#checkPassword").val()&&$("#password").val().length>=6&&$("#password").val().length<=16){
            if ($("#checkCode").val()!=checkCode||$("#checkCode").val()==""){
                $("#p4").html('验证码有误');
            }
            else {
                $.post("RegistServlet",{"username":$("#username").val(),"password":$("#password").val()});
                alert("注册成功!");
                $.cookie('username',$("#username").val(),{express:7,path:"/"});
                $.cookie('password',$("#password").val(),{express:7,path:"/"});
                window.location.href="index.html";
            }
        }
    });
});
