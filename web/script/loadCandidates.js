var candidate=new Array(1000);
var receiver;
var index=0;
$(document).ready(function (){
    var search=window.location.search;
    search=search.substring(1);
    var id=search.split("&")[0].split("=")[1];
    $.post("SearchVoteByID",{"id":id},function (data){
        receiver=data.publicKey;
        $.post("loadCandidates",{"id":id},function (data)
        {
            var startTime=data.startTime.substring(0,4)+"年"+data.startTime.substring(5,7)+"月"+data.startTime.substring(8,10)+"日"+data.startTime.substring(11,16);
            var overTime=data.overTime.substring(0,4)+"年"+data.overTime.substring(5,7)+"月"+data.overTime.substring(8,10)+"日"+data.overTime.substring(11,16);
            var rand="id";//默认按id排序,0为从小到大
            var met=0;
            $("#title").append('<p id="VoteID">投票ID:'+id+'</p>');
            $("#title").append('<p id="whoCreated" >创建者:'+data.whoCreated+'</p>');
            $("#title").append('<p id="times" >'+startTime+'~~'+overTime+'</p>');
            $("#title").append('<p id="publicKey" style="word-break:break-all;font-size: 22px;border-bottom: #242222 solid 1px">创建者公钥:'+receiver+'</p>');
            $("#title").append('<p>'+data.title+'</p>');
            var body='<p>'+data.body+'</p>';
            $("#body").html(body);
            /*var divs='<p>编号名字&emsp票数</p>'*/
            $("#candidatesBlock").append(' <div id="randID" class="line randP">编号⬆</div> <div id="randName" class="line randP">参赛者</div> <div id="randSumOfVotes" class="line randP">票数</div> <div id="cat"> <div>&nbsp;</div></div>');
            $(".randP").css("cursor","pointer");
            $(".randP").click(function (){
                var jiantou=["⬆","⬇"];
                var $this=$(this).attr("id");
                if ($this=="randID")
                {
                    rand="id";
                    met=1-met;
                    $("#randID").text("编号"+jiantou[met]);
                    $("#randName").text("参赛者");
                    $("#randSumOfVotes").text("票数");
                }
                else if ($this=="randName")
                {
                    rand="name";
                    met=1-met;
                    $("#randID").text("编号");
                    $("#randName").text("参赛者"+jiantou[met]);
                    $("#randSumOfVotes").text("票数");
                }
                else if ($this=="randSumOfVotes")
                {
                    rand="sum";
                    met=1-met;
                    $("#randID").text("编号");
                    $("#randName").text("参赛者");
                    $("#randSumOfVotes").text("票数"+jiantou[met]);
                }
                $(".candidateLine").remove();
                $("#container").remove();
                data.candidates=randFunction(data.candidates,rand,met);
                addc(data);
            });
            addc(data);//进入时执行一次
        });
    });

});

function txt(candidate){//绘制条形图

    var jo={
        chart: {
            type: 'column',
            zoomType: 'x'
        },
        title: {
            text: '票数统计'
        },
        subtitle: {
            text: ''
        },
        xAxis: {
            gridLineWidth:'1',
            categories: [

            ],
            crosshair: true
        },
        yAxis: {
            gridLineColor:'#019000',
            gridLineDashStyle:'Dash',
            min: 0,
            title: {
                text: '总票数'
            },
            tickInterval: 1,
            labels: {
                formatter: function () {
                    return this.value+'票';
                }
            }
        },
        tooltip: {
            // head + 每个 point + footer 拼接成完整的 table
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.0f} 票</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                borderWidth: 0
            }
        },
        series: [{
            name: '',
            data: []
        }],
        credits: {
            enabled:false
        },
        exporting: {
            enabled:false
        }
    };
    var sum=[];
    for (var i=0;i<candidate.length;i++) {
        jo.xAxis.categories.push(candidate[i].name);
        sum.push(candidate[i].sum);
    }
    jo.series=[{name:'',data:sum}];
    var chart = Highcharts.chart('container',jo);
}
function sxt(candidate){//绘制扇形图
    var suma=0.0;
    for (var i=0;i<candidate.length;i++)
    {
        suma+=candidate[i].sum;
    }
    var jo={
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie',
        },
        title: {
            text: '票数统计',
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    }
                }
            }
        },
        series: [{
            name: '票数占比',
            colorByPoint: true,
            data: []
        }],
        credits: {
            enabled:false
        },
        exporting: {
            enabled:false
        }
    };
    var tmp=[];
    for (var i=0;i<candidate.length;i++)
    {
        var rate=candidate[i].sum/suma;
        tmp.push({name:candidate[i].name,y:rate});
    }
    jo.series=[{name: '票数占比',colorByPoint: true,data:tmp}];
    var chart = Highcharts.chart('container',jo);
}
function randFunction(candidates,rand,met)
{
    if (met==0)
    {
        return rand0(candidates,rand);
    }
    else
    {
        return rand1(candidates,rand);
    }
}
function rand0(candidates,rand)//从小到大
{

    for (var i=0;i<candidates.length;i++)
    {
        for (var j=candidates.length-1;j>i;j--)
        {
            if (rand=="id")
            {
                if (candidates[j].innerID<candidates[j-1].innerID)
                {
                    var tmp=candidates[j];
                    candidates[j]=candidates[j-1];
                    candidates[j-1]=tmp;
                }
            }
            if (rand=="name")
            {
                if (candidates[j].name<candidates[j-1].name)
                {
                    var tmp=candidates[j];
                    candidates[j]=candidates[j-1];
                    candidates[j-1]=tmp;
                }
            }
            if (rand=="sum")
            {
                if (candidates[j].sum<candidates[j-1].sum)
                {
                    var tmp=candidates[j];
                    candidates[j]=candidates[j-1];
                    candidates[j-1]=tmp;
                }
            }
        }
    }
    return candidates;
}
function rand1(candidates,rand)//从小到大
{
    for (var i=0;i<candidates.length;i++)
    {
        for (var j=candidates.length-1;j>i;j--)
        {
            if (rand=="id")
            {
                if (candidates[j].innerID>candidates[j-1].innerID)
                {
                    var tmp=candidates[j];
                    candidates[j]=candidates[j-1];
                    candidates[j-1]=tmp;
                }
            }
            if (rand=="name")
            {
                if (candidates[j].name>candidates[j-1].name)
                {
                    var tmp=candidates[j];
                    candidates[j]=candidates[j-1];
                    candidates[j-1]=tmp;
                }
            }
            if (rand=="sum")
            {
                if (candidates[j].sum>candidates[j-1].sum)
                {
                    var tmp=candidates[j];
                    candidates[j]=candidates[j-1];
                    candidates[j-1]=tmp;
                }
            }
        }
    }
    return candidates;
}

function addc(data)
{
    for (var i=0;i<data.candidates.length;i++)
    {
        /*
            动态添加参赛者
        */
        var divs = '';
        divs+='<div class="candidateLine" id="'+data.candidates[i].innerID+'">';
        divs+='<div class="line id">'+data.candidates[i].innerID+'</div>';
        divs+='<div class="line name">'+data.candidates[i].name+'</div>';
        divs+='<div class="line sum">'+data.candidates[i].sum+'</div>';
        divs+='<button class="btn">投票</button>'
        divs+='</div>';
        $("#candidatesBlock").append(divs);
    };
    $("#cat").css("border-bottom","1px solid #000");
    $(".line").css({"float":"left","position":"relative","width":"300px","user-select":"none"});
    $("#candidatesBlock").css({"text-align":"center"});
    $(".btn").click(function (){

        var nowTime=getNowFormatDate();
        if ($.cookie('username')==null)
        {
            alert("请先登录");
            window.location.href="login.html";
        }
        else
        {
            if (nowTime>data.overTime)
            {
                alert("投票已截止");
            }
            else
            {
                //点击投票事件
                var oppo='';
                var id=$(this).siblings(".id").text();
                var name=$(this).siblings(".name").text()
                oppo+='<div id="sure">' +
                    '<p>candidateID:'+id+'</p>' +
                    '    <p>candidateName:'+name+'</p>' +
                    '    <p></p>' +
                    '    <textarea id="privateKey" placeholder="请输入你的私钥以进行签名"></textarea> <br>' +
                    '    <button id="sendVote">投票</button>'+
                    '    <button id="cancle">取消</button>' +
                    '</div>';
                $("#main").append(oppo);
                $("#sure").css({"opacity":1,"position":"absolute","width":"500px","height":"400px",
                    "top":"50%","left":"50%","margin-left":"-250px","margin-top":"-200px","z-index":"105","background-color":"blanchedalmond","opacity":"0.95"});
                $("#privateKey").css({"resize":"none","width":"300px","height":"150px","font-size":"15px"});
                $("#sure").hide().show("1000");
                $("#cancle").click(function (){
                    oppo='';
                    $("#sure").remove();
                });
                $("#sendVote").click(function (){
                    /*                String receiver=req.getParameter("publicKey_receiver");
                                    String sender=req.getParameter("publicKey_sender");
                                    String privateKey=req.getParameter("privateKey");*/
                    var sender=$.cookie('publicKey');
                    // var receiver   //回调函数之前已经得到receiver
                    var chuangjianzhe=receiver;
                    var privateKey=$("#privateKey").val();
                    var voteID=$("#VoteID").text().substring(5);
                    $.post("SendVoteServlet",{"receiver":receiver,"sender":sender,"privateKey":privateKey,"id":voteID,"innerID":id},function (res){
                        if (res=="false")//签名失败
                        {
                            alert("签名失败");
                        }
                        else if (res=="notValid")//not valid
                        {
                            alert("已投票，投票无效!");
                        }
                        else//valid
                        {
                            alert("您的区块:\n"+res+"\n已被添加");
                        }
                        location.reload();
                    });
                });
            }
        }
    });
    $("#charts").append('<div id="container" style="min-width:500px;height:800px;border-radius: 5px;"></div>');
    txt(data.candidates);//初始显示条形图
    $("#txt").click(function (){
        txt(data.candidates);
    });
    $("#sxt").click(function (){
        sxt(data.candidates);
    });
    $("#candidatesBlock").hide().slideDown(500);
}

function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    var minutes=date.getMinutes();
    var hours=date.getHours();
    var seconds=date.getSeconds();
    if (seconds<10)
    {
        seconds="0"+seconds;
    }
    if (minutes<10)
    {
        minutes="0"+minutes;
    }
    if (hours<10)
    {
        hours="0"+hours;
    }
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
        + " " + hours + seperator2 + minutes
        + seperator2 + seconds+".0";
    return currentdate;
}