$(document).ready(function f(){
        var t1='<img src="script/assets/img/p1.jpg" alt="">';
        var t2='<img src="script/assets/img/p2.jpg" alt="">';
        var t3='<img src="script/assets/img/p3.jpg" alt="">';

        $("#bg").html(t1);
        $("#bg").hide();
        $("#bg").fadeIn(2000,function (){
            $("#bg").fadeOut(1000,function (){
                $("#bg").html(t2);
                $("#bg").fadeIn(2000,function (){
                    $("#bg").fadeOut(1000,function (){
                        $("#bg").html(t3);
                        $("#bg").fadeIn(2000,function (){
                            $("#bg").fadeOut(1000,function (){
                                f();
                            });
                        });
                    });
                });
            });
        });

});