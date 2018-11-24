package cn.zzuisa.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.zzuisa.constant.RCons;
import cn.zzuisa.pojo.Users;
import cn.zzuisa.pojo.vo.UsersVO;
import cn.zzuisa.service.UserService;
import cn.zzuisa.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "用户相关业务的接口", tags = { "用户相关业务的controller" })
@RequestMapping("/user")
public class UserController extends BasicController {


	@Autowired
	private UserService userService;

	@ApiOperation(value = "用户上传头像", notes = "用户上传头像的接口")
	@ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "query")
	@PostMapping("/uploadFace")
	public R uploadFace(String userId, @RequestParam("file") MultipartFile[] files) throws Exception {
		if (StringUtils.isBlank(userId)) {
			return R.errorMsg("用户id不能为空");
		}
		// 文件保存的命名空间
		String uploadPathDB = "/" + userId + "/face";
		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		try {
			if (files != null && files.length > 0) {
				String fileName = files[0].getOriginalFilename();
				if (StringUtils.isNotBlank(fileName)) {
					// 文件上传的最终保存路径
//					String finalFacePath = WINDOWSPATH + uploadPathDB + "/" + fileName;
					String finalFacePath = UBUNTUPATH + uploadPathDB + "/" + fileName;
					// 设置数据库保存的路径
					uploadPathDB += ("/" + fileName);
					File outFile = new File(finalFacePath);

					if (outFile.getParentFile() != null || outFile.getParentFile().isDirectory()) {
						// 创建文件夹
						outFile.getParentFile().mkdirs();
					}
					fileOutputStream = new FileOutputStream(outFile);
					inputStream = files[0].getInputStream();
					IOUtils.copy(inputStream, fileOutputStream);
				}
			} else {
				return R.errorMsg("上传出错！");
			}
		} catch (Exception e) {

			return R.errorMsg("上传出错！");
		} finally {
			if (fileOutputStream != null) {
				fileOutputStream.flush();
				fileOutputStream.close();
			}
		}
		Users user = new Users();
		user.setId(userId);
		user.setFaceImage(uploadPathDB);
		userService.updataUserInfo(user);
		return R.ok(uploadPathDB);
	}

	@ApiOperation(value = "查询用户信息", notes = "查询用户信息的接口")
	@ApiImplicitParam(name = "userId", value = "用户id", required = true)
	@PostMapping("/query")
	public R query(String userId) throws Exception {
		if (StringUtils.isBlank(userId)) {
			return R.errorMsg("用户id不能为空");
		}
		Users userInfo = userService.queryUserInfo(userId);
		UsersVO usersVO = new UsersVO();
		BeanUtils.copyProperties(userInfo, usersVO);
		return R.ok(usersVO);
	}

}
