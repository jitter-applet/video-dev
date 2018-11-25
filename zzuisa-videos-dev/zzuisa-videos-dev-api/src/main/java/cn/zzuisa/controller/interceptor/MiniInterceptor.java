package cn.zzuisa.controller.interceptor;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.github.pagehelper.util.StringUtil;

import cn.zzuisa.utils.JsonUtils;
import cn.zzuisa.utils.R;
import cn.zzuisa.utils.RedisOperator;

public class MiniInterceptor implements HandlerInterceptor {

	@Autowired
	RedisOperator redis;
	public static final String USER_REDIS_SESSION = "user-redis-session";

	/**
	 * 拦截请求，在controller调用之前
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		String userId = request.getHeader("userId");
		String userToken = request.getHeader("userToken");
		System.out.println("id:" + userId + "   " + "token:" + userToken);
		if (StringUtil.isNotEmpty(userId) && StringUtils.isNotBlank(userToken)) {
			String uniqueToken = redis.get(USER_REDIS_SESSION + ":" + userId);
			System.out.println("id:" + userId + "   " + "token:" + userToken);
			System.out.println("uniqueToken:" + uniqueToken);
			if (StringUtils.isEmpty(uniqueToken) && StringUtils.isBlank(uniqueToken)) {
				System.out.println("请登录..1.");
				returnErrorResponse(response, new R().errorTokenMsg("请登录..."));
				return false;
			} else {
				if (!uniqueToken.equals(userToken)) {
					System.out.println("其他设备登录");
					returnErrorResponse(response, new R().errorTokenMsg("其他设备登录"));
					return false;
				}
			}
		} else {
			System.out.println("id:" + userId + "   " + "token:" + userToken);
		
			return true;
		}
		/**
		 * 返回 false：请求被拦截，返回 返回true：请求OK，可以继续执行，放行
		 */
		return true;
	}

	public void returnErrorResponse(HttpServletResponse response, R result) throws IOException {
		OutputStream out = null;
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			out = response.getOutputStream();
			out.write(JsonUtils.objectToJson(result).getBytes("utf-8"));
			out.flush();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 请求controller之后，渲染视图之前
	 */
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 请求controller之后，视图渲染之后
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}
}
