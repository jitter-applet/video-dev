package cn.zzuisa.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.zzuisa.constant.RCons;
import cn.zzuisa.pojo.Users;
import cn.zzuisa.pojo.vo.UsersVO;
import cn.zzuisa.service.BgmService;
import cn.zzuisa.utils.MD5Utils;
import cn.zzuisa.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "背景音乐业务的接口", tags = { "背景音乐业务的controller " })
@RequestMapping("/bgm")
public class BgmController extends BasicController {

	@Autowired
	private BgmService bgmService;

	@ApiOperation(value = "获取背景音乐列表", notes = "获取背景音乐列表的接口")
	@PostMapping("/list")
	public R list() throws Exception {
		return R.ok(bgmService.queryBgmList());
	}

}
