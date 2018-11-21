package cn.zzuisa.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.zzuisa.constant.RCons;
import cn.zzuisa.pojo.Users;
import cn.zzuisa.pojo.vo.UsersVO;
import cn.zzuisa.service.BgmService;
import cn.zzuisa.utils.MD5Utils;
import cn.zzuisa.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(value = "视频相关的业务接口", tags = { "视频业务的controller " })
@RequestMapping("/video")
public class VideoController extends BasicController {

	@Autowired
	private BgmService bgmService;

	@ApiOperation(value = "上传视频", notes = "上传视频的接口")
	@PostMapping("/upload")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userId",value="用戶id",required= true,dataType="String",paramType="query"),
		@ApiImplicitParam(name="bgmId",value="背景音乐id",required= false,dataType="String",paramType="query"),
		@ApiImplicitParam(name="videoSeconds",value="背景音乐播放长度",required= true,dataType="String",paramType="query"),
		@ApiImplicitParam(name="videoWidth",value="视频宽度",required= true,dataType="String",paramType="query"),
		@ApiImplicitParam(name="videoHeight",value="视频高度",required= true,dataType="String",paramType="query"),
		@ApiImplicitParam(name="desc",value="视频描述",required=false ,dataType="String",paramType="query"),
	})
	public R uoload(String userId,
			String bgmId,double videoSeconds, int videoWidth,int videoHeight,String desc,
			@ApiParam(value="短视频",required=true) MultipartFile videofile) throws Exception {
		if (StringUtils.isBlank(userId)) {
			return R.errorMsg("用户id不能为空");
		}
		// 文件保存的命名空间
		String uploadPathDB = "/" + userId + "/video";
		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		try {
			if (videofile != null ) {
				String fileName = videofile.getOriginalFilename();
				if (StringUtils.isNotBlank(fileName)) {
					// 文件上传的最终保存路径
					String finalVideoPath = WINDOWSPATH + uploadPathDB + "/" + fileName;
					// 设置数据库保存的路径
					uploadPathDB += ("/" + fileName);
					File outFile = new File(finalVideoPath);

					if (outFile.getParentFile() != null || outFile.getParentFile().isDirectory()) {
						// 创建文件夹
						outFile.getParentFile().mkdirs();
					}
					fileOutputStream = new FileOutputStream(outFile);
					inputStream = videofile.getInputStream();
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
		return R.ok(uploadPathDB);
	}

}
