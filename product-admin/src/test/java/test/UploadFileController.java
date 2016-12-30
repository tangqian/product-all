package test;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import com.thinkgem.jeesite.common.web.BaseController;

@RestController
@RequestMapping(value = "${adminPath}/upload/")
public class UploadFileController extends BaseController {

	/*@ExceptionHandler(value = (Exception.class))
	public ModelAndView doException(Exception ex, HttpServletRequest request)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		if (ex instanceof MaxUploadSizeExceededException) {
			model.put("errors", "文件应不大于 " + getFileMB( ((MaxUploadSizeExceededException) ex).getMaxUploadSize()));
		} else if (ex instanceof RuntimeException) {
			model.put("error", "未选中文件");
		} else {
			model.put("errors", "未知错误: " + ex.getMessage());
		}
		return new ModelAndView("upload", model);

	}*/

	private static String getFileKB(long byteFile) {
		if (byteFile == 0)
			return "0KB";
		long kb = 1024;
		return "" + byteFile / kb + "KB";
	}
	private static String getFileMB(long byteFile) {
		if (byteFile == 0)
			return "0MB";
		long mb = 1024 * 1024;
		return "" + byteFile / mb + "MB";
	}

	
}
