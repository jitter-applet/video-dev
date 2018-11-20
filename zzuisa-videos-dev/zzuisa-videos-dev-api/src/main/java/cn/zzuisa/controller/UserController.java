package cn.zzuisa.controller;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cn.zzuisa.constant.RCons;
import cn.zzuisa.pojo.Users;
import cn.zzuisa.pojo.vo.UsersVO;
import cn.zzuisa.service.UserService;
import cn.zzuisa.utils.MD5Utils;
import cn.zzuisa.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "用户注册登录的接口", tags = { "注册和登录的controller" })
public class UserController extends BasicController {

	@Autowired
	private UserService userService;

	@ApiOperation(value = "用户注册", notes = "用户注册的接口")
	@PostMapping("/regist")
	public R regist(@RequestBody Users user) throws Exception {
		// 1. 判断用户名和密码必须不为空
		if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
			return R.errorMsg(RCons.VALIDLOGIN);
		}
		// 2. 判断用户名是否存在
		boolean usernameIsExist = userService.queryUsernameIsExist(user.getUsername());
		// 3. 保存用户注册信息
		if (!usernameIsExist) {
			user.setNickname(user.getUsername());
			user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
			user.setFansCounts(0);
			user.setReceiveLikeCounts(0);
			user.setFollowCounts(0);
			userService.saveUser(user);
		} else {
			return R.errorMsg(RCons.USERHASEXIST);
		}
		user.setPassword("");
		UsersVO userVO = setUserRedisSessionToken(user);
		return R.ok(userVO);
	}

	public UsersVO setUserRedisSessionToken(Users userModel) {
		String uniqueToken = UUID.randomUUID().toString();
		redis.set(USER_REDIS_SESSION + ":" + userModel.getId(), uniqueToken, 1000 * 60 * 30);
		UsersVO userVO = new UsersVO();
		BeanUtils.copyProperties(userModel, userVO);
		userVO.setUserToken(uniqueToken);
		return userVO;
	}

	@ApiOperation(value = "用户登录", notes = "用户登录的接口")
	@PostMapping("/login")
	public R login(@RequestBody Users user) throws Exception {
		String username = user.getUsername();
		String password = user.getPassword();
		// 判断用户名和密码不为空Ï
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			R.errorMsg(RCons.VALIDLOGIN);
		}
		password = MD5Utils.getMD5Str(password);
		Users userResult = userService.querryUsersForLogin(username, password);
		if (userResult != null) {
			userResult.setPassword("");
			UsersVO userVO = setUserRedisSessionToken(userResult);
			return R.ok(userVO);
		} else {
			return R.errorMsg(RCons.ERRORLOGIN);
		}
	}

	@ApiOperation(value = "用户注销", notes = "用户注销的接口")
	@ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "query")
	@PostMapping("/logout")
	public R logout(String userId) {
		redis.del(USER_REDIS_SESSION + ":" + userId);
		return R.ok(RCons.LOGOUTOK);
	}

}
