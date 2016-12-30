<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<title>edm-demo</title>
</head>
<body style="padding:0;margin:0;background:#f2f2f2;">
	<table width="800" border="0" cellspacing="0" cellpadding="0" align="center" bgcolor="#ffffff">
		<tr>
			<td align="center" style="padding: 20px 0 30px 0; border-top: 2px solid #cc0000; border-right: 1px solid #dcdcdc; border-left: 1px solid #dcdcdc;">
				<a href="http://live.smartlifein.com" target="_blank"><img src="http://live.smartlifein.com/static/web/img/logo_email.png" height="45" width="170" alt="智慧生活网 直播" /></a>
			</td>
		</tr>
		<tr>
			<td style="padding: 0 60px 0 60px; font: normal 14px/18px SimSun; border-right: 1px solid #dcdcdc; border-left: 1px solid #dcdcdc;">
				<b>尊敬的用户：</b><br /><br />
				&#x3000;&#x3000;智慧生活网提醒您今天<span style="color: #c00;">（${(startTime?string("MM月dd日"))!}）${(startTime?string("HH:mm"))!}</span>准时观看${name}直播，请 <a href="http://live.smartlifein.com/live/${roomId}" target="_blank" style="color: #007ae1; font-weight: bold; white-space: nowrap;">点击这里</a> 进入直播间。
			</td>
		</tr>
		<tr>
			<td style="padding: 45px 0 0 0; font-size: 12px; color: #8d8d8d; line-height: 20px; border-right: 1px solid #dcdcdc; border-left: 1px solid #dcdcdc;">
				<table width="550" border="0" cellspacing="0" cellpadding="0" align="center" style="font-size: 12px;  line-height: 20px; border-collapse:collapse;">
					<tr>
						<td width="90" align="center" style="padding: 12px 0 12px 0; border: 1px solid #e5e5e5;">举办公司</td>
						<td style="padding: 0 0 0 20px; border: 1px solid #e5e5e5;">${company}</td>
					</tr>
					<tr>
						<td width="90" align="center" style="padding: 12px 0 12px 0; border: 1px solid #e5e5e5;">直播主题</td>
						<td style="padding: 0 0 0 20px; border: 1px solid #e5e5e5;">${name}</td>
					</tr>
					<tr>
						<td width="90" align="center" style="padding: 12px 0 12px 0; border: 1px solid #e5e5e5;">直播时间</td>
						<td style="padding: 0 0 0 20px; border: 1px solid #e5e5e5;">（北京时间）${(startTime?string("yyyy年MM月dd日 HH:mm:ss"))!}</td>
					</tr>
					<tr>
						<td width="90" align="center" style="padding: 12px 0 12px 0; border: 1px solid #e5e5e5;">直播简介</td>
						<td style="padding: 12px 20px 12px 20px; line-height: 20px; border: 1px solid #e5e5e5;">${detail}</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td style="padding: 50px 0 35px 0; border-right: 1px solid #dcdcdc; border-left: 1px solid #dcdcdc;">
				<a href="http://live.smartlifein.com/live/${roomId}" target="_blank" style="display: block; width: 150px; height: 40px; margin: 0 auto 0 auto; text-align: center; font: bold 14px/40px SimSun; color: #fff; text-decoration: none; background: #cb0000;border-radius: 4px;">进入直播间</a>
			</td>
		</tr>
		<tr>
			<td align="center" style="padding: 0 0 35px 0; font: normal 12px/18px SimSun; color: #999999; border: 1px solid #dcdcdc; border-top: 0;">Copyright © OFweek.com. 本网站所有内容均受版权保护</td>
		</tr>
	</table>
</body>
</html>