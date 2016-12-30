$(function() {
	var editor;
    KindEditor.ready(function(K){
    	editor=K.create('#bulletinContent', {
            height : "300px",
            width : "600px",
            pasteType: 1,
            fullscreenShortcut: false,
            resizeType : 1,
			allowPreviewEmoticons : false,
			allowImageUpload : false,
            items : ['source','link','unlink'],
            afterBlur : function(){
                //编辑器失去焦点时直接同步，可以取到值  
                this.sync();  
            },
            htmlTags:{
            	font : ['color'],
            	a : ['href', 'target'],
            	u : []
            },
            newlineTag:'br'
        });
    });
    $("#insertLink").click(function(){
    	var dialog = KindEditor.dialog({
                width : 300,
                height: 100,
                title : '添加链接',
                body : '<div align="center"><input type="text" name="dialog_input" style="width:280px" value="http://"/></div>',
                closeBtn : {
                        name : '关闭',
                        click : function(e) {
                                dialog.remove();
                        }
                },
                yesBtn : {
                        name : '确定',
                        click : function(e) {
                        	var val = $("input[name='dialog_input']").val();
                        	var title = editor.selectedHtml();
                        	if(title == undefined || title.length==0){
                        		title = val;
                        	}
                        	editor.insertHtml('<u><font color="blue"><a href="'+val+'" target="_blank">'+title+'</a></font></u>');
                            dialog.remove();
                        }
                },
                noBtn : {
                        name : '取消',
                        click : function(e) {
                                dialog.remove();
                        }
                }
        });
    });
	$("#bulletinForm").validate({
		debug : false,
		errorClass : "error",
		focusInvalid : true,
		ignore : "[name='']",
		submitHandler : function(form) {
			form.submit();
		},
		errorPlacement : function(error, element) {
			var name = $(element).attr("name");
			var tipElement = $("[id='tip_" + name + "']");
			if (tipElement != null && tipElement.length > 0) {
				error.appendTo(tipElement);
			} else {
				error.appendTo(element.parent());
			}
		},
		rules : {
			exhibitionId : {
				required : true
			},
			publishAreaInput : {
				required : true
			},
			content : {
				required : true
			},
			publishTime : {
				required : true
			},
			endTime : {
				required : true
			}
		},
		messages : {
			exhibitionId : {
				required : "请选择展会"
			},
			publishAreaInput : {
				required : "请选择公告位置"
			},
			content : {
				required : "请填写公告内容"
			},
			publishTime : {
				required : "请填写发布时间"
			},
			endTime : {
				required : "请填写结束时间"
			}
		}
	});
});