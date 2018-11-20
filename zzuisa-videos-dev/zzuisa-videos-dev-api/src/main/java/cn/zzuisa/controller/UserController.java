package cn.zzuisa.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.zzuisa.constant.RCons;
import cn.zzuisa.service.UserService;
import cn.zzuisa.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "用户相关业务的接口", tags = { "用户相关业务的controller" })
public class UserController extends BasicController {

	@Autowired
	private UserService userService;

	@ApiOperation(value = "用户上传头像", notes = "用户上传头像的接口")
	@ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "query")
	@PostMapping("/uploadFace")
	public R uploadFace(String userId, @RequestParam("file") MultipartFile[] files) throws Exception {
		//文件保存的命名空间
		String fileSpace = "D:/wx-videos";
		String uploadPathDB = "/" +userId + "/face";
		if(files!=null&&files.length>0){
			FileOutputStream fileOutputStream = null;
			InputStream inputStream = null;
			String fileName = files[0].getOriginalFilename();
			if(StringUtils.isNotBlank(fileName)){
				//文件上传的最终保存路径
				String finalFacePath = fileSpace + uploadPathDB +"/" + fileName;
				//设置数据库保存的路径
				uploadPathDB +=("/"+fileName);
				File outFile = new File(finalFacePath);
				
				if(outFile.getParentFile()!=null || outFile.getParentFile().isDirectory()){
					outFile.getParentFile().mkdirs();
				}
				
				fileOutputStream = new FileOutputStream(outFile);
				inputStream = files[0].getInputStream();
				IOUtils.copy(inputStream, fileOutputStream);
			}
		}
		return R.ok(RCons.LOGOUTOK);
	}

}
