<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>新建直播</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/uploadify/css/uploadify.css" />
    <script type="text/javascript" src="${ctxStatic}/uploadify/jquery.uploadify.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
			$("#name").focus();
			jQuery.validator.addMethod("compareDate", function(value, element, param) {
				var startDate = $(param).val() + ":00";
				var endDate = value + ":00";
	            
	            var date1 = new Date(Date.parse(startDate.replace(new RegExp(/-/g),'/')));
	            var date2 = new Date(Date.parse(endDate.replace(new RegExp(/-/g),'/')));
	            return date1 < date2;
		    });
			$("#inputForm").validate({
				rules: {
					endTime: {
						compareDate: "#startTime",
					},
					bgEndTime: {
						compareDate: "#startTime",
					}
				},
				messages: {
					endTime: {
						compareDate: "对外显示结束时间必须晚于开始时间!"
					},
					bgEndTime: {
						compareDate: "结束时间必须晚于开始时间!"
					}
				},
				submitHandler: function(form){
					if($("#liveImageId").val().length > 0){
						loading('正在提交，请稍等...');
						form.submit();						
					}else{
						alert("请上传封面图");
					}
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
			$("#coverId").uploadify({
			    'swf': ctxStatic + '/uploadify/swf/uploadify.swf?var=' + (new Date()).getTime(),
			    'uploader': 'http://live.smartlifein.com/sys/file/secretUpload?var=' + (new Date()).getTime(),
			    'height': 30,
			    // 设置文件浏览按钮的高度
			    'width': 120,
			    // 设置文件浏览按钮的宽度
			    'buttonText': '上传封面图',
			    // 浏览按钮的文本
			    'buttonCursor': 'hand',
			    // 鼠标指针悬停在按钮上
			    'fileSizeLimit': '20MB',
			    // 上传文件的大小限制
			    'fileObjName': 'file',
			    // 文件上传对象的名称
			    'fileTypeExts': '*.jpg;*.gif;*.jpeg;*.png;*.bmp;',
			    // 设置可以选择的文件的类型，格式如：’*.jpg;*.gif;*.jpeg;*.png;*.bmp;’
			    'preventCaching': true,
			    // 不允许缓存
			    'auto': true,
			    // 设置为true当选择文件后就直接上传了，为false需要点击上传按钮才上传
			    'multi': false,
			    // 设置为true时可以上传多个文件
			    'method': 'post',
			    // 提交方式Post或Get
			    'progressData': 'percentage',
			    'successTimeout' : 3,
			    // 设置上传进度显示方式，percentage显示上传百分比，speed显示上传速度
			    'onUploadSuccess': function(file, data, response) {
			    	if(data != ""){
				        var info = eval("(" + data + ")");
						if(info.status == "0"){
							$("#liveImageId").val(info.fileId);
							$("#liveImagePreview img").attr("src", "${fns:getLiveUrlPrefix()}" + info.uri);
							$("#liveImagePreview").show();
						}else{
							alert("上传封面图失败！");
						}
			    	} else {
			    		alert("上传失败!请检查直播系统是否能正常访问(http://live.smartlifein.com)");
			    	}
			    },
			    'onUploadError' : function(file, errorCode, errorMsg, errorString) {
		            alert('The file ' + file.name + ' could not be uploaded: ' + errorString);
		        }
			});

            $("#reviewId").uploadify({
                'swf': ctxStatic + '/uploadify/swf/uploadify.swf?var=' + (new Date()).getTime(),
                'uploader': 'http://live.smartlifein.com/sys/file/secretUpload?var=' + (new Date()).getTime(),
                'height': 30,
                // 设置文件浏览按钮的高度
                'width': 120,
                // 设置文件浏览按钮的宽度
                'buttonText': '上传回顾图',
                // 浏览按钮的文本
                'buttonCursor': 'hand',
                // 鼠标指针悬停在按钮上
                'fileSizeLimit': '20MB',
                // 上传文件的大小限制
                'fileObjName': 'file',
                // 文件上传对象的名称
                'fileTypeExts': '*.jpg;*.gif;*.jpeg;*.png;*.bmp;',
                // 设置可以选择的文件的类型，格式如：’*.jpg;*.gif;*.jpeg;*.png;*.bmp;’
                'preventCaching': true,
                // 不允许缓存
                'auto': true,
                // 设置为true当选择文件后就直接上传了，为false需要点击上传按钮才上传
                'multi': false,
                // 设置为true时可以上传多个文件
                'method': 'post',
                // 提交方式Post或Get
                'progressData': 'percentage',
                'successTimeout' : 3,
                // 设置上传进度显示方式，percentage显示上传百分比，speed显示上传速度
                'onUploadSuccess': function(file, data, response) {
                    if(data != ""){
                        var info = eval("(" + data + ")");
                        if(info.status == "0"){
                            $("#liveReviewId").val(info.fileId);
                            $("#liveReviewImage img").attr("src", "${fns:getLiveUrlPrefix()}" + info.uri);
                            $("#liveReviewImage").show();
                        }else{
                            alert("上传回顾图失败！");
                        }
                    } else {
                        alert("上传失败!请检查直播系统是否能正常访问(http://live.smartlifein.com)");
                    }
                },
                'onUploadError' : function(file, errorCode, errorMsg, errorString) {
                    alert('The file ' + file.name + ' could not be uploaded: ' + errorString);
                }
            });
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/live/room/list">直播列表</a></li>
		<li class="active"><a href="${ctx}/live/room/form?id=${room.id}">直播<shiro:hasPermission name="live:room:edit">${not empty room.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="live:room:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="room" action="${ctx}/live/room/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">直播主题:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">关键字:</label>
			<div class="controls">
				<form:input path="keyword" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
				<span class="help-inline">多个关键字用半角逗号（,）隔开</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">举办公司:</label>
			<div class="controls">
				<form:select path="speakerId"  class="input-xlarge required">
					<form:options items="${fns:getSpeakerList()}" itemLabel="company" itemValue="id" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">直播模式:</label>
			<div class="controls">
				<label for="publishMode0"><form:radiobutton id="publishMode0" path="publishMode" value="0" checked="true"/>视频/PPT（含图文直播）</label>
				<label for="publishMode1"><form:radiobutton id="publishMode1" path="publishMode" value="1" />图文直播（无视频、无PPT）</label>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">观看模式:</label>
			<div class="controls">
				<label for="watchMode0"><form:radiobutton id="watchMode0" path="watchMode" value="0" checked="true"/>游客模式</label>
				<label for="watchMode1"><form:radiobutton id="watchMode1" path="watchMode" value="1" />会员</label>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所在行业:</label>
			<div class="controls">
				<form:select path="industriesIdList"  class="input-xlarge required">
					<form:options items="${fns:getDictList('industry_option')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始时间:</label>
			<div class="controls">
				<input id="startTime"  name="startTime"  type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required" style="width:163px;"
				value="<fmt:formatDate value="${room.startTime}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间:</label>
			<div class="controls">
				<input id="bgEndTime"  name="bgEndTime"  type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required" style="width:163px;"
					   value="<fmt:formatDate value="${room.bgEndTime}" pattern="yyyy-MM-dd HH:mm"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">对外显示结束时间:</label>
			<div class="controls">
				<input id="endTime"  name="endTime"  type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required" style="width:163px;"
				value="<fmt:formatDate value="${room.endTime}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">封面图:</label>
			<div class="controls">
				<input id="liveImageId" type="hidden" value="${room.coverId}" name="coverId">
				<ol id="liveImagePreview" <c:if test="${empty room.coverId}">style="display:none;"</c:if> >
					<li>
						<img style="max-width:200px;max-height:200px;_height:200px;border:0;padding:3px;" src="${fns:getLiveSysFile(room.coverId).url}">
					</li>
				</ol>
				<input type="file" id="coverId" />
				<span class="help-inline">建议尺寸：262(宽)x173(高)</span> 
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">回顾图:</label>
            <div class="controls">
                <input id="liveReviewId" type="hidden" value="${room.reviewId}" name="reviewId">
                <ol id="liveReviewImage" <c:if test="${empty room.reviewId}">style="display:none;"</c:if> >
                    <li>
                        <img style="max-width:200px;max-height:200px;_height:200px;border:0;padding:3px;" src="${fns:getLiveSysFile(room.reviewId).url}">
                    </li>
                </ol>
                <input type="file" id="reviewId" />
                <span class="help-inline">建议尺寸：790(宽)x448(高)</span>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">摘要:</label>
			<div class="controls">
				<textarea style="width: 500px;height: 150px;" name="summary" id="summary" class="input-medium required">${room.summary }</textarea>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">直播介绍:</label>
			<div class="controls">
				<textarea style="width: 500px;height: 150px;" name="detail" id="detail" class="input-medium required">${room.detail }</textarea>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">显示联系方式:</label>
			<div class="controls">
				<label for="isContactShow0"><form:radiobutton id="isContactShow0" path="isContactShow" value="0" checked="true"/>是</label>
				<label for="isContactShow1"><form:radiobutton id="isContactShow1" path="isContactShow" value="1" />否</label>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否显示:</label>
			<div class="controls">
				<label for="isShow0"><form:radiobutton id="isShow0" path="isShow" value="0" checked="true"/>是</label>
				<label for="isShow1"><form:radiobutton id="isShow1" path="isShow" value="1" />否</label>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<c:if test="${not empty room.id}">
			<div class="control-group">
				<label class="control-label">创建时间:</label>
				<div class="controls">
					<label class="lbl"><fmt:formatDate value="${room.createDate}" type="both" dateStyle="full"/></label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">修改时间:</label>
				<div class="controls">
					<label class="lbl"><fmt:formatDate value="${room.updateDate}" type="both" dateStyle="full"/></label>
				</div>
			</div>
		</c:if>
		<div class="form-actions">
			<shiro:hasPermission name="live:room:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>