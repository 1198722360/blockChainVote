var blockID=0;
var nowPage=0;
var username='';
$(document).ready(function (){
    searchAll();
    $("#h").hide().fadeIn(1000);
    $("#allVotes").click(function (){
        if (nowPage!=0){
            $("#votesList").html('');
            searchAll();
            nowPage=0;
        }
    });
    $("#myVotes").click(function (){
        if (nowPage!=1){
            $("#votesList").html('');
            searchMyVotes();
            nowPage=1;
        }
    });
    $("#createAVote").click(function (){
       if (nowPage!=2){
            crt();//创建一个投票
           nowPage=2;
       }
    });
    $("#logo").animate({left:"20px"},"slow"),
    $("#logo").fadeTo("slow",1),
    $("#logo").fadeTo("slow",0),
    $("#logo").fadeTo("slow",1);



    username=$.cookie('username');
    if (username!=null){
        $("#me").html('<p id="username">'+username+'</p>'+'<br><a href="index.html" id="exit">退出登录</a>');
        $("#exit").click(function (){
            $.removeCookie('username',{path:"/"});
            $.removeCookie('password',{path:"/"});
            $.removeCookie('publicKey',{path:"/"});
        });
    }
});
generatePastelColor = () => {
    let R = Math.floor((Math.random() * 127) + 127);
    let G = Math.floor((Math.random() * 127) + 127);
    let B = Math.floor((Math.random() * 127) + 127);

    let rgb = (R << 16) + (G << 8) + B;
    return `#${rgb.toString(16)}`;
};

function searchAll(){
    $("#searchLogo").show(300);
    $("#ele").remove();
    $.get("SearchAllVotesServlet",{},function (data)
    {
        for (var i=0;i<data.length;i++)
        {
            //<div class="oneOfVote">
            //      <a href=" " >
            //          <div class="id">
            //          </div>
            //
            //          <div class="title">
            //          </div>
            //
            //          <div class="startTime">
            //          </div>
            //
            //          <div class="overTime">
            //          </div>
            //
            //          <div class="sumOfVotes">
            //          </div>
            //
            //      </a>
            //</div>
            var id=data[i].id;
            var title=data[i].title;
            var tt=title;
            if (title.length>11){
                tt=title.substring(0,11)+"...";
            }

            var startTime=data[i].startTime.substring(0,16);
            var overTime=data[i].overTime.substring(0,16);
            var sumOfVotes=data[i].sumOfVotes;
            var oneVote='<div class="bod"><div title="'+title+'" id="block'+blockID+'" class="oneOfVotes">';
            oneVote+='<a class="click" href="oneVote.html?id='+id+'&title='+title+'&startTime='+startTime+'&overTime='+overTime+'&sumOfVotes='+sumOfVotes+'">';
            oneVote+='<div class="id">'+'#'+id+'</div>';
            oneVote+='<div class="title">'+tt+'</div>';
            oneVote+='<div class="startTime">'+startTime+'</div>';
            oneVote+='⇩';
            oneVote+='<div class="overTime">'+overTime+'</div>';
            oneVote+='<div class="sumOfVotes">参与人数:'+sumOfVotes+'</div>';
            oneVote+='</a>';
            oneVote+='</div></div>';
            ////////////////////////////////////////////////////////////////
            //加入
            $("#votesList").append(oneVote).hide().fadeIn(300);
            var color=generatePastelColor();
            $("#block"+blockID).css({"background-color":color,"border-radius":"20px"}),blockID+=1;
            $(".oneOfVotes").css({"float":"left","text-align":"center"});
            $(".bod").css("float","left");
        }

    });
};
function searchMyVotes(){
    username=$.cookie("username");
    if (username==null){
      alert("请先登录");
      window.location.href="login.html"
    } else {
        $("#searchLogo").hide(300);
        $("#ele").remove();
        $.get("SearchMyVotesServlet",{"username":username},function (data)
        {
            for (var i=data.length-1;i>=0;i--)
            {
                //<div class="oneOfVote">
                //      <a href=" " >
                //          <div class="id">
                //          </div>
                //
                //          <div class="title">
                //          </div>
                //
                //          <div class="startTime">
                //          </div>
                //
                //          <div class="overTime">
                //          </div>
                //
                //          <div class="sumOfVotes">
                //          </div>
                //
                //      </a>
                //</div>
                var id=data[i].id;
                var title=data[i].title;
                var tt=title;
                if (title.length>11){
                    tt=title.substring(0,11)+"...";
                }
                var startTime=data[i].startTime.substring(0,16);
                var overTime=data[i].overTime.substring(0,16);
                var sumOfVotes=data[i].sumOfVotes;
                var oneVote='<div class="bod" id="bod_'+id+'"><div title="'+title+'" id="block'+blockID+'" class="oneOfVotes">';
                oneVote+='<a class="click" href="oneVote.html?id='+id+'&title='+title+'&startTime='+startTime+'&overTime='+overTime+'&sumOfVotes='+sumOfVotes+'">';
                oneVote+='<div class="id">'+'#'+id+'</div>';
                oneVote+='<div class="title">'+tt+'</div>';
                oneVote+='<div class="startTime" id="startTime_'+id+'">'+startTime+'</div>';
                oneVote+='⇩';
                oneVote+='<div class="overTime" id="overTime_'+id+'">'+overTime+'</div>';
                oneVote+='<div class="sumOfVotes">参与人数:'+sumOfVotes+'</div>';
                oneVote+='</a>';
                oneVote+='<button class="edit" value="'+id+'" style="width: 100%;'+
                    '    position: relative;top: 3px;'+
                    '    border-width: 0px;' +
                    '    border-radius: 3px;' +
                    '    background-color: darkgrey;' +
                    '    cursor: pointer;\n'+
                    '    font-family: Microsoft YaHei;' +
                    '    color: white">管理</button> </div></div>';
                ////////////////////////////////////////////////////////////////
                //加入
                $("#votesList").append(oneVote).hide().fadeIn(300);
                var color=generatePastelColor();
                $("#block"+blockID).css({"background-color":color,"border-radius":"20px"})
                    ,blockID+=1;
                $(".oneOfVotes").css("float","left");
                $(".bod").css("float","left");
            }
            $(".edit").click(function (){//管理 点击事件
                edit($(this).val());
            });

        });
    }

};
function crt(){//创建投票

    if (username==null){
        alert("请先登录");
        window.location.href="login.html"
    }
    else {
        var nTime=new Date();
        var format='';
        format += nTime.getFullYear()+"-";
        format += (nTime.getMonth()+1)<10?"0"+(nTime.getMonth()+1):(nTime.getMonth()+1);
        format += "-";
        format += nTime.getDate()<10?"0"+(nTime.getDate()):(nTime.getDate());
        format += "T";
        format += nTime.getHours()<10?"0"+(nTime.getHours()):(nTime.getHours());
        format += ":";
        format += nTime.getMinutes()<10?"0"+(nTime.getMinutes()):(nTime.getMinutes());
        $("#searchLogo").hide(300);
        var createBlock='<div id="ele">  <img src="./resources/titleLogo.png" alt="标题" style="width: 80px;-webkit-user-drag: none">   <div>   <div><textarea id="title"></textarea><span id="countTitle">30</span>/30</div>';
        createBlock+='<img src="./resources/timeLogo.png" alt="时间" style="width: 60px;margin-top: 50px;-webkit-user-drag: none">';
        createBlock+='<div><input type="datetime-local" id="startTime" value="'+format+'"></div>';
        createBlock += '<div><input type="datetime-local" id="overTime" value="'+format+'"></div><br>';
        createBlock+='<img src="./resources/bodyLogo.png" alt="正文" style="width: 60px;margin-top: 60px;-webkit-user-drag: none">';
        createBlock += '<div><textarea id="body"></textarea> <div><span id="countBody">150</span>/150</div></div><br>';
        createBlock+='<div><button id="createVote">创建投票</button></div><br>'
        createBlock+='</div></div>';
        $(".bod").remove();
        $("#h").append(createBlock);
        $("#title").css({"width":"600px","height":"60px","display":"inline"});
        $("#body").css({"height":"500px","width":"600px"});
        $("#ele").css({"width":"600px", "text-align":"center","margin":"0 auto"}).hide().show(400);
        $("textarea").css({"font-size":"30px","resize":"none"});
        $("#startTime").css({"display":"inline-block","float":"left","font-size":"18px"});
        $("#overTime").css({"display":"inline-block","float":"right","font-size":"18px"});
        $("#createVote").css(
            {"background-color": "green",
                "border": "none",
                "color": "white",
                "text-align": "center",
                "text-decoration": "none",
                "font-size": "28px",
                "cursor": "pointer"});

        //字数检测
        $("#title").on("input propertychange", function () {
            check($(this),30,$("#countTitle"));
        });
        $("#body").on("input propertychange", function () {
            check($(this),150,$("#countBody"));
        });
        $("#createVote").click(function (){
            if($("#title").val()==''){
                alert("请输入标题");
            }
            else if($("#startTime").val()==''||$("#overTime").val()==''){
                alert("请选择时间");
            }
            else if($("#overTime").val()<=$("#startTime").val()){
                alert("选择时间有误");
            }
            else if($("#body").val()==''){
                alert("请输入投票简介");
            }
            else {
                var sender=$.cookie('publicKey');
                //public Vote(int id, String title, String body, int sumOfVotes, Timestamp startTime, Timestamp overTime)
                $.post("CreateVoteServlet", {"title":$("#title").val(),"startTime":$("#startTime").val(),
                    "overTime":$("#overTime").val(),"body":$("#body").val(),"username":username,"sender":sender
                },function (){alert("创建投票成功");$("#myVotes").click();});
            }
        });
    }

}

function edit(id)
{
    $.post("SearchVoteByID",{"id":id},function (data){
        var nowTitle;
        var nowBody;
        var title=data.title;
        var body=data.body;
        var nowTime1;//原起始时间
        var nowTime2;//原结束时间
        //记录当前的title和body
        nowTitle=title;
        nowBody=body;
        var nowlen1=30-title.length;
        var nowlen2=150-title.length;
        var candidates=data.candidates;
        var div='<div id="editDiv" style="z-index: 999;position: absolute;background-color: bisque ;width: 1000px;left: 50%;margin-left: -500px;top: 50%;margin-top: -430px;border-radius: 5px;border: solid 1px">';
        div+='<img id="cancel" src="./resources/cancel.png" alt="关闭" style="-webkit-user-drag: none;width: 30px;height: 30px;float: right;cursor: pointer">';
        div+='</div>'
        $("body").append(div);
        $("#editDiv").css({"text-align": "center","font-size":"20px"});
        $("#editDiv").hide().show(300,function ff(){
            $("#editDiv").append('<p id="editID">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp 编号:'+id+'</p><br><br>');//id
            $("#editID").hide().show(100,function (){
                $("#editDiv").append('<p>标题</p><div><textarea id="editTitle" ' +//标题
                    'style="font-size: 20px;resize: none;width: 400px;height: 25px;border:1px dotted #999666">'//样式
                    +title+'</textarea></div><span id="countTitle">'+nowlen1+'</span>/30<br><br>');

                //字数检测
                $("#editTitle").on("input propertychange", function () {
                    check($(this),30,$("#countTitle"));
                });

                    $("#editTitle").hide().show(100,function (){

                        //获取投票时间
                        var t1=$("#startTime_"+id).text();//起始时间
                        //格式化
                        t1=t1.substring(0,10)+"T"+t1.substring(11);

                        var t2=$("#overTime_"+id).text();//结束时间
                        t2=t2.substring(0,10)+"T"+t2.substring(11);
                        nowTime1=t1;
                        nowTime2=t2;
                        $("#editDiv").append('时间 <br> <span id="editTime"><input type="datetime-local" id="editStartTime" value="'+t1+'">~<input type="datetime-local" id="editOverTime" value="'+t2+'"></span><br><br><br>');//////////////////////////////////////////////
                        $("input").css({"font-size":"18px"});
                        $("#editTime").hide().slideDown(300);
                        $("#editDiv").append('<p>简介</p><div><textarea id="editBody" ' +//简介
                            ' style="font-size: 20px;resize: none;width: 400px;height: 200px;border:1px dotted #999666">'//样式
                            +body+'</textarea></div><span id="countBody">'+nowlen2+'</span>/150<br><br>');
                        //字数检测
                        $("#editBody").on("input propertychange", function () {
                            check($(this),150,$("#countBody"));
                        });

                        $("#editBody").hide().show(100,function (){
                            $("#editDiv").append('<div id="pca"></div>')
                            for (var i=0;i<candidates.length;i++)
                            {
                                var ca = '';
                                ca+='<div style="margin-bottom: 30px" value="'+candidates[i].innerID+'" id="in_'+candidates[i].innerID+'">';
                                ca+='<div style="display: inline;float: left;margin-left: 300px">'+candidates[i].innerID+'</div>';
                                ca+='<div class="name" style="display: inline">'+candidates[i].name+'</div>';
                                ca+='<button style="float: right;margin-right: 100px" class="delete">删除</button>'
                                ca+='</div>';
                                $("#pca").append(ca);
                                $("#in_"+candidates[i].innerID).hide().slideDown(300);
                            }
                            $(".delete").css(
                    {"float": "right","margin-right": "300px","background-color": "red",
                            "border": "none",
                            "color": "white",
                            "text-align": "center",
                            "text-decoration": "none",
                            "font-size": "18px",
                            "cursor": "pointer"});

                            //添加选手
                            $("#editDiv").append('<button id="addButton">添加投票对象</button><br><br>');
                            $("#addButton").hide().show("100");
                            $("#editDiv").append('<button id="deleteTheVote">删除投票</button>');
                            $("#deleteTheVote").hide().show("100");
                            $("#addButton,#deleteTheVote").css(
                                {"background-color": "green",
                                    "border": "none",
                                    "color": "white",
                                    "text-align": "center",
                                    "text-decoration": "none",
                                    "font-size": "28px",
                                    "cursor": "pointer"});
                            $("#deleteTheVote").css({
                               "background-color":"red"
                            });
                            $("#deleteTheVote").click(function (){
                               var cf=confirm("确定删除该投票吗");
                               if (cf)
                               {
                                   $.post("DeleteTheVoteServlet",{"id":id},function (){
                                      alert("删除成功");
                                      location.reload();
                                   });
                               }
                            });
                            $("#addButton").click(function ff(){
                                var res = window.prompt("请在此输入投票者名字","");
                                if (res=="")
                                {
                                    alert("名字不能为空");
                                    ff();
                                }
                                else if(res.length>=20)
                                {
                                    alert("名字长度应小于20个字符");
                                    ff();
                                }
                                else
                                {
                                    var newID=$("#pca").children().last().attr("id");
                                    if (newID==null)
                                    {
                                        newID="in_0";
                                    }
                                    newID=parseInt(newID.substring(3))+1;
                                    $.post("AddCandidateServlet",{"id":id,"name":res,"innerID":newID},function (){
                                        var ca = '';
                                        ca+='<div style="margin-bottom: 30px" value="'+newID+'" id="in_'+newID+'">';
                                        ca+='<div style="display: inline;float: left;margin-left: 300px">'+newID+'</div>';
                                        ca+='<div class="name" style="display: inline">'+res+'</div>';
                                        ca+='<button style="float: right;margin-right: 100px" id="delete_'+newID+'" >删除</button>'
                                        ca+='</div>';
                                        $("#pca").append(ca);
                                        $("#in_"+newID).hide().slideDown(300);
                                        $("#delete_"+newID).css(
                                            {"float": "right","margin-right": "300px","background-color": "red",
                                                "border": "none",
                                                "color": "white",
                                                "text-align": "center",
                                                "text-decoration": "none",
                                                "font-size": "18px",
                                                "cursor": "pointer"});
                                        $("#delete_"+newID).click(function (){
                                            var name=$(this).prev().text();//删除按钮上一个节点为name
                                            var innerID=$(this).prev().prev().text()
                                            var r=confirm("确定删除"+name+"吗?");
                                            if (r){//确定，则删除
                                                $.post("DeleteCandidateServlet",{"id":id,"innerID":innerID},function (){
                                                    alert("删除成功") ;
                                                });
                                                $("#in_"+innerID).remove();
                                            }
                                        });
                                    });
                                }
                            });
                            //删除事件
                            $(".delete").click(function (){
                                var name=$(this).prev().text();//删除按钮上一个节点为name
                                var innerID=$(this).prev().prev().text()
                                var r=confirm("确定删除"+name+"吗?");
                                if (r){//确定，则删除
                                    $.post("DeleteCandidateServlet",{"id":id,"innerID":innerID},function (){
                                       alert("删除成功") ;
                                    });
                                    $("#in_"+innerID).remove();
                                }
                            });
                        });
                    });
            });
        });

        $("#cancel").click(function (){
            var newTitle=$("#editTitle").val();
            var newBody=$("#editBody").val()
            var newTime1=$("#editStartTime").val();
            var newTime2=$("#editOverTime").val();
            if (newTime2<=newTime1)
            {
                alert("结束时间不能小于起始时间");
            }
            else
            {
                if (nowTitle!=newTitle||nowBody!=newBody||nowTime1!=newTime1||nowTime2!=newTime2)
                {
                    var cf=confirm("是否保存");
                    if (cf)
                    {
                        $.post("ChangeVoteServlet",{"id":id,"title":newTitle,"body":newBody,"startTime":newTime1,"overTime":newTime2},function (){
                            alert("保存成功") ;
                            $("#votesList").html('');
                            searchMyVotes();
                            nowPage=1;
                        });
                    }
                }
                $("#editDiv").remove();
            }
        });

    });
}
function check($this,num,$count)
{
        _val = $this.val(),
        count = "";
    if (_val.length > num) {
        $this.val(_val.substring(0, num));
    }
    count = num - $this.val().length;
    $count.text(count);
}
function fun1(){
    $("#allVotes").click();
}
function fun2(){
    $("#myVotes").click();
}
