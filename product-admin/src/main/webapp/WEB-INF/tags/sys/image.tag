<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="value" type="java.lang.String" required="true" description="文件ID，对应EXH_FILE_INFO表ID"%>
<%@ attribute name="width" type="java.lang.String" required="false" description="宽度"%>
<%@ attribute name="height" type="java.lang.String" required="false" description="高度"%>
<img src="${fns:getFileInfo(value).url}"  style="width:${empty width ? 100 : width}px;height:${empty height ? 100 : height}px;border:0;padding:3px;">