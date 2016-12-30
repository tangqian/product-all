// document
function audit(id, type) {
	var typeStr, buttonObj;
	var width = 600;
	var height = 450;
	if (type == "vedio") {
		typeStr = "视频";
		buttonObj = vedioBtn;
		width = 700;
		height = 450;
	} else if (type == "picture") {
		typeStr = "图集";
		buttonObj = pictureBtn;
		height = 400;
	} else if (type == "data") {
		typeStr = "资料";
		buttonObj = dataBtn;
		height = 200;
	} else if (type == "speech") {
		typeStr = "演讲稿";
		buttonObj = speechBtn;
		height = 200;
	} else if (type == "product") {
		typeStr = "产品";
		buttonObj = productBtn;
		width = 700;
		height = 450;
	}
	
	var url = "iframe:"+ ctx +"/exhibitor/" + type + "/form?id=" + id;
	
	top.$.jBox.open(url,
			typeStr + "审核", width, height, {
				buttons : buttonObj,
				submit : function(v, h, f) {
					var status;
					if (v == "pass") {
						status = 3;
					} else if (v == "nopass") {
						status = 4;
					}
					if (status != undefined) {
						// 执行保存
						loading('正在提交，请稍等...');
						$.ajax({
							type : "POST",
							url : ctx + "/exhibitor/" + type + "/audit",
							data : {
								id : id,
								status : status
							},
							success : function(msg) {
								top.$.jBox.tip("审核成功");
								window.location.reload();
							},
							error : function() {
								top.$.jBox.tip("审核失败,服务器出错了！！！", 'error');
							}
						});
						return true;
					}
				},
				loaded : function(h) {
					$(".jbox-content", top.document)
							.css("overflow-y", "hidden");
				}
			});
	return false;
}
