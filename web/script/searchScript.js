
$(document).ready(function (){
   $("#searchLogo").click(function (){
      var t='<input type="text" id="sendSearch" class="search" name="search" placeholder="搜索">'
      $("#h").append(t);
      $("#sendSearch").css({});
      $("#sendSearch").hide().show(300);
      $("#sendSearch").focus();
      $("#sendSearch").blur(function (){
         $("#sendSearch").hide(300,function (){
            $(this).remove();
         });
      });
   });
   $(document).keyup(function(event){//提交搜索数据
      if(event.keyCode ==13&&$("#sendSearch").is(":focus")&&$("#sendSearch").val()!=''){
         $.post("SearchByInput",{"input":$("#sendSearch").val()},function (data){
            $(".bod").remove();
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
      }
   });
});

//获取url中的参数
function getUrlParam(name) {
   var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
   var r = window.location.search.substr(1).match(reg);  //匹配目标参数
   if (r != null) return unescape(r[2]); return null; //返回参数值
}
