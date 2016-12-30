

/*
* 弹窗对象
* 参数为object，属性：
* id：字符串 |　弹窗显示元素的选择器
* shade：布尔值　|　true，默认值，显示遮罩　|　false，无遮罩
* close：字符串/布尔值false | 传字符串必须是关闭按钮的选择器，布尔值false则用默认关闭按钮
* clickShadeClose：布尔值 | 点击遮罩关闭弹窗
* */
function PopUp(o) {

    var defaultCss = {
        'visibility': 'hidden',
        'display': 'block',
        'position': 'fixed',
        'left': '50%',
        'top': '50%',
        'zIndex': '1000000'
    };

    this.$el = $(o.id);
    //是否需要遮罩
    this.shade = typeof o.shade == 'boolean' ? o.shade : true;
    //是否配置关闭按钮
    this.isClose = typeof o.close == 'string' ? true : false;
    //配置了关闭按钮则用配置的按钮，否则用默认的按钮
    this.$close = this.isClose ? $(o.close) : $('<span class="close-popup"><img src="/static/web/modules/speaker/img/close.png" /></span>');
    //默认关闭点击遮罩关闭弹窗
    this.clickShadeClose = typeof o.clickShadeClose == 'boolean' ? o.clickShadeClose : false;
    //弹窗样式
    this.css = $.extend({}, defaultCss, o.css || {});
}

PopUp.prototype = {
    //向dom中插入遮罩层
    appendShade: function() {
        var $shade = $('<div class="shade"></div>'); //遮罩层
        $('body').append($shade);
    },
    //关闭弹窗
    closePopup: function() {
        $('.shade').remove(); //删除遮罩层
        if(!this.isClose) {
            this.$close.remove(); //删除关闭按钮
            $('form .table input[type=checkbox]').attr('checked', false);// 取消全部选中
        }
        this.$el.css({'visibility': 'hidden', 'display': 'none'});
    },
    //显示弹窗
    showPopup: function() {
        //$('html,body').css({'height': '100%', 'overflow': 'hidden'});
        this.$el.css(this.css);
        var _width = this.$el.outerWidth(),  //弹窗宽度
            _height = this.$el.outerHeight(),  //弹窗高度
            that = this;
        if(this.shade) {
            //遮罩
            this.appendShade();
        }

        if(!this.isClose) {
            //插入关闭按钮
            this.$el.append(this.$close);
        }else {
            //已有关闭按钮添加class
            this.$close.addClass('close-popup');
        }

        //关闭事件
        $('body').on('click', '.close-popup', function(e) {
            that.closePopup();
        });
        $('body').on('click', '.shade', function() {
            if(!that.clickShadeClose) {
                return false;
            }
            that.closePopup();
        });
        this.$el.css({
            'visibility': 'visible',
            'marginTop': this.css.position == 'fixed' ? -_height/2 : 0,
            'marginLeft': -_width/2
        });
    }
};

//左菜单栏 展开收起效果
$('body').on('click', '#menu .ol-title', function() {

    var $parent = $(this).parent(), //父级
        $son = $(this).siblings('.menu-son'); //子级

    if($parent.hasClass('menu-cur')) {
        $parent.removeClass('menu-cur');
        return;
    }

    $parent.addClass('menu-cur');

});

//全选
$('body').on('click', '#checkAll, #checkAll_a, #checkAll_b, #checkAll_c', function() {
    var $table = $(this).parents('table');
    if(this.checked) {
        $table.find('td .checkbox-a').attr('checked', true);
    } else {
        $table.find('td .checkbox-a').attr('checked', false);
    }
});

//直播资料选择
$('body').on('click', '#addPPT, #addVideo, #addData', function() {
    var popupId;
    switch(this.id) {
        case 'addPPT':
            popupId = '#selectPPT';
            break;
        case 'addVideo':
            popupId = '#selectVideo';
            break;
        case 'addData':
            popupId = '#selectData';
            break;
        default:
            console.log('弹窗错误：直播资料选择');
            return false;
    }
    var pptPopup = new PopUp({
        id: popupId,
        shade: true,
        clickShadeClose: true,
        css: {
            position: 'absolute',
            top: 100+$(window).scrollTop()+'px'
        }
    });
    pptPopup.showPopup();
});

//输入排序
//只允许输入数字
$('body').on('keyup input propertychange', '.input-sort', function(e) {

    var value = parseInt($(this).val());
    value = ( isNaN(value) || (value == '0') )  ? '' : value;
    $(this).val(value);

});

//分页：整页刷新式跳转
if( typeof laypage == 'function' ) {

    laypage({
        cont: 'page',
        pages: $('#pageNum').val(), //可以叫服务端把总页数放在某一个隐藏域，再获取。假设我们获取到的是18
        skin: '#AF0000',
        skip: true, //是否开启跳页
        groups: 5, //连续显示分页数
        curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
            var page = location.search.match(/page=(\d+)/);
            return page ? page[1] : 1;
        }(), 
        jump: function(e, first){ //触发分页后的回调
            if(!first){ //一定要加此判断，否则初始时会无限刷新
                location.href = '?page='+e.curr;
            }
        }
    });

}


//查看观众信息弹窗
(function() {

    var lookInfo = null;
    
    //弹窗实例
    lookInfo = new PopUp({
        id: '#lookInfo',
        shade: true,
        clickShadeClose: true
    });

    //弹窗：数据报告 - 查看观众
    $('body').on('click', '.data-username a', function() {

        //请求观众数据
        $.ajax({
            url: '',
            type: 'GET',
            data: '',
            dataType: 'JSON',
            success: function(d) {
                lookInfo.showPopup();
            }
        });
        
    });
    
    //自定义关闭
    $('body').on('click', '.pop-tr-close', function() {
        lookInfo.closePopup();
    });

})();


//聊天记录，展会展开收起
(function() {

    //默认关闭
    var chatRecordTabSwitch = false;
    //tab总数
    var tabSize = $('#chatRecordTab a').size();
    //每行3个tab，计算高度
    var outerH = tabSize % 3 == 0 ? tabSize / 3 * 40 : Math.ceil(tabSize / 3) * 40;

    if (tabSize > 6) {
        $('#chatRecordTab').css('height', 80);
        $('.chat-rt').show();
    }

    $('body').on('click', '.chat-rt', function() {

        if (chatRecordTabSwitch) {
            $(this).html('展开全部').removeClass('chat-rt-off');
            $('#chatRecordTab').animate({height: 80}, 400);
            chatRecordTabSwitch = false;
            return
        }

        $(this).html('收起').addClass('chat-rt-off');
        $('#chatRecordTab').animate({height: outerH}, 400);
        chatRecordTabSwitch = true; 

    });

})();