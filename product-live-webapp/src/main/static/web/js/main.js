jQuery(function($) {
    (function() {
        //'use strict';
        //轮换图
        var index = 1;
        var len = $("#banner_list li").length;
        $("#banner_list li:not(:first-child)").hide();

        function show() {
            $("#banner_list li").eq(index).fadeIn(1000).siblings("li").fadeOut(1000);
            $("#dot_list li").eq(index).addClass("active").siblings("li").removeClass("active");
            index++;
            if (index > len - 1) {
                index = 0;
            }
        }
        var timer = setInterval(show, 4000);
        $("#banner_list").hover(function() {
            clearInterval(timer);

        }, function() {
            timer = setInterval(show, 4000);
        });

        $("#dot_list li").click(function() {
            index = $(this).index();
            $("#banner_list li").eq(index).fadeIn(1000).siblings().fadeOut(1000);
            $(this).addClass("active").siblings().removeClass("active");
        });

    })();

    //右侧固定悬浮
    var rightfix = function() {
        var rtop = $(".main_right ").offset().top;
        $(window).scroll(function() {
            if ($(this).scrollTop() > rtop) {
                $(".main_right").addClass("fix");
            } else {
                $(".main_right").removeClass("fix");

            }
        });

    }
    rightfix();


    //下拉加载 
    var dropDwonLoad=function(){
        var $more = $("#js-more"),
            loading = true,
            pageIndex = 1,
            html = "";
        function getListData(pageIndex){
            $.getScript('http://v3.jiathis.com/code/jia.js',function(){
            $.ajax({
                url: "http://live.smartlifein.com/api/overLives?pageNo=" + pageIndex,
                dataType: 'json',
                success: function(data) {
                    if (!data.length) {
                        $more.remove(); //没有数据则移除更多按钮
                        return; //如果没有数据则返回
                    }
                    html="";

                    for (var i in data) {
                        var enterUrl = "http://live.smartlifein.com/live/" + data[i].id;
                        if (data[i].type == 'EXPO') {
                            enterUrl = "http://expo.ofweek.com/exhibition/index.xhtml?expo=" + data[i].id;
                        } else if (data[i].type == 'WEBINAR') {
                            enterUrl = "http://webinar.ofweek.com/active.action?activity.id=" + data[i].id;
                        }

                        html += '<dl>';
                        html += '<dt class="fl">';
                        html += '<a href="' + enterUrl + '" target="_blank"><img src=' + data[i].bigUrl + '></a>';
                        html += '<i></i>';
                        html += '<span class="end">直播结束</span>';
                        html += '</dt>';

                        html += '<dd class="fr">';
                        html += '<h3><a href="' + enterUrl + '" target="_blank">' + data[i].name + '</a></h3>';
                        html += '<div class="timeshare">';
                        if (data[i].endTime) {
                            html += '<strong>' + new Date(data[i].startTime).format("yyyy-MM-dd hh:mm")
                            + ' 至 ' + new Date(data[i].endTime).format("yyyy-MM-dd hh:mm") + '</strong>';
                        } else {
                            html += '<strong>' + new Date(data[i].startTime).format("yyyy-MM-dd hh:mm") + '</strong>';
                        }
                        html += '<div class="jiathis_style fr liveshare">';
                        html += '<a href="javascript:;" class="jiathis sharelink" target="_blank">分享</a>';
                        html += '</div>';
                        html += '</div>';
                        if (data[i].speaker) {
                            html += '<p class="zhujiang">';
                            html += '<span>主办公司：' + data[i].company + '</span>';
                            html += '<span>&nbsp;主讲人：' + data[i].speaker + '</span>';
                            html += '</p>';
                        }
                        html += '<p>';
                        html += data[i].brief;
                        html += '<a href="' + enterUrl + '" target="_blank">详细</a>';
                        html += '</p>';
                        html += '<div class="opera">';
                        html += '<a href="' + enterUrl + '" class="grey_btn" target="_blank">查看回顾</a>';

                        html += '</div>';
                        html += '</dd>';
                        html += '</dl>';
                    }

                    $(html).hide().insertBefore($more).fadeIn(800);
                    $more.html("更多").removeClass("loading");
                    loading = true;

                }
            });
            });
        }

        $(window).scroll(function () {
            if ($(this).scrollTop() + $(window).height() >= $more.offset().top + $more.outerHeight() && loading) {
                $more.html("<span>加载中</span>").addClass("loading");
                loading = false; //防止加载多次
                getListData(pageIndex);
                pageIndex++;

            }
        });

        //getListData(1);

    }


    dropDwonLoad();

    //让每条直播分享它自己的内容，而不是全部分享相同的内容。
    $(".live_list").on('mouseover',".liveshare",function(){
        setShare($(this).closest("dd").find("h3").text(),$(this).closest("dd").find("h3 a").attr("href"),$(this).closest("dd").find(".opera").prev().text(),$(this).closest("dl").find("img").attr("src"));
    })



})



//分享标题、链接，摘要
function setShare(title, url,summary,pic) {
    jiathis_config.title = title;
    jiathis_config.url = url;
    jiathis_config.summary=summary;
    jiathis_config.pic=pic;
}
var jiathis_config = {}

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
