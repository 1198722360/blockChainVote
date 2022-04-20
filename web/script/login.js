$(function (){
    $("#submit").click(function (){
        $.post("searchUserServlet",{"username":$("#username").val(),"password":$("#password").val()},function (user){
            if (user==null){
                $("p").html('用户名或密码错误');
            }
            else
            {
                $.cookie('username',$("#username").val(),{express:7,path:"/"});
                $.cookie('password',$("#password").val(),{express:7,path:"/"});
                $.cookie('publicKey',user.publicKey,{express:7,path:"/"});
                window.location.href="index.html";
            }
        });
    });
});