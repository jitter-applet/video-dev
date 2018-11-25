package cn.zzuisa.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.zzuisa.enums.VideoStatusEnum;
import cn.zzuisa.pojo.Bgm;
import cn.zzuisa.pojo.Videos;
import cn.zzuisa.pojo.vo.VideosVO;
import cn.zzuisa.service.BgmService;
import cn.zzuisa.service.VideoService;
import cn.zzuisa.utils.FetchVideoCover;
import cn.zzuisa.utils.MergeVideoMp3;
import cn.zzuisa.utils.PagedResult;
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
	@Autowired
	private VideoService videoSerivce;

	@ApiOperation(value = "上传视频", notes = "上传视频的接口")
	@PostMapping(value = "/upload", headers = "content-type=multipart/form-data")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId", value = "用戶id", required = true, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "bgmId", value = "背景音乐id", required = false, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "videoSeconds", value = "背景音乐播放长度", required = true, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "videoWidth", value = "视频宽度", required = true, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "videoHeight", value = "视频高度", required = true, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "videoDesc", value = "视频描述", required = false, dataType = "String", paramType = "form"), })
	public R uoload(String userId, String bgmId, double videoSeconds, int videoWidth, int videoHeight, String videoDesc,
			@ApiParam(value = "短视频", required = true) MultipartFile file) throws Exception {
		if (StringUtils.isBlank(userId)) {
			return R.errorMsg("用户id不能为空");
		}
		// 文件保存的命名空间 缓存目录
		String uploadPathDB = "/" + userId + "/cache";
		// 文件目录
		String uploadVideoPathDB = UBUNTUPATH + "/" + userId + "/video";
		String coverPathDB = "/" + userId + "/videoCovers";
		String finalVideoPath = "";
		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		String RID = UUID.randomUUID().toString();
		String videoOutputName = RID + ".mp4";
		try {
			if (file != null) {
				String fileName = file.getOriginalFilename();

				String FileNamePrefix = fileName.split("\\.")[0];

				if (StringUtils.isNotBlank(fileName)) {
					// 文件上传的最终保存路径
					finalVideoPath = UBUNTUPATH + uploadPathDB + "/" + fileName;

					// 设置数据库保存的路径
					uploadPathDB += ("/" + fileName);
					coverPathDB = coverPathDB + "/" + RID + ".jpg";
					File outFile = new File(finalVideoPath);
					if (outFile.getParentFile() != null || outFile.getParentFile().isDirectory()) {
						// 创建文件夹
						outFile.getParentFile().mkdirs();
						new File(uploadVideoPathDB).mkdirs();
						new File(UBUNTUPATH + coverPathDB).getParentFile().mkdirs();
					}
					fileOutputStream = new FileOutputStream(outFile);
					inputStream = file.getInputStream();
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
		if (StringUtils.isNotBlank(bgmId)) {
			Bgm bgm = bgmService.queryBgmById(bgmId);
			String mp3InputPath = UBUNTUPATH + bgm.getPath();
//			MergeVideoMp3 tool = new MergeVideoMp3(WINDOWSFFMPEG_EXE);
			 MergeVideoMp3 tool = new MergeVideoMp3(UBUNTUFFMPEG);
			String videoInputPath = finalVideoPath;
			uploadPathDB = "/" + userId + "/video/" + videoOutputName;
			finalVideoPath = UBUNTUPATH + uploadPathDB;
			tool.convertor(mp3InputPath, videoInputPath, videoSeconds, finalVideoPath, false);
		}
		System.out.println();
		System.out.println("uploadPathDB:" + uploadPathDB);
		System.out.println("finalVideoPath:" + finalVideoPath);
		// 对视频进行截取
//		FetchVideoCover videoInfo = new FetchVideoCover(WINDOWSFFMPEG_EXE);
		 FetchVideoCover videoInfo = new FetchVideoCover(UBUNTUFFMPEG);
		videoInfo.getCover(finalVideoPath, UBUNTUPATH + coverPathDB);

		// 保存视频信息到数据库
		Videos video = new Videos();
		video.setAudioId(bgmId);
		video.setUserId(userId);
		video.setVideoSeconds((float) videoSeconds);
		video.setVideoWidth(videoWidth);
		video.setVideoDesc(videoDesc);
		video.setCoverPath(coverPathDB);
		video.setVideoHeight(videoHeight);
		video.setVideoPath(uploadPathDB);
		video.setStatus(VideoStatusEnum.SUCCESS.value);
		video.setCreateTime(new Date());
		String videoId = videoSerivce.saveVideo(video);
		return R.ok(videoId);
	}

	@ApiOperation(value = "上传视频封面", notes = "上传视频封面的接口")
	@PostMapping(value = "/uploadCover", headers = "content-type=multipart/form-data")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "videoId", value = "视频主键id", required = true, dataType = "String", paramType = "form"), })
	public R uoloadCover(String userId, String videoId, @ApiParam(value = "视频封面", required = true) MultipartFile file)
			throws Exception {
		if (StringUtils.isBlank(videoId) || StringUtils.isBlank(userId)) {
			return R.errorMsg("视频主键Id和用户id不能为空");
		}
		// 文件保存的命名空间
		String uploadPathDB = "/" + userId + "/";
		String finalCoverPath = "";
		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		try {
			if (file != null) {
				String fileName = file.getOriginalFilename();
				if (StringUtils.isBlank(userId)) {
					return R.errorMsg("用戶id不能为空");
				}
				// 文件上传的最终保存路径
				finalCoverPath = UBUNTUPATH + uploadPathDB + "/videoCovers/" + fileName;
				// 设置数据库保存的路径
				uploadPathDB += ("videoCovers/" + fileName);
				File outFile = new File(finalCoverPath);

				if (outFile.getParentFile() != null || outFile.getParentFile().isDirectory()) {
					// 创建文件夹
					outFile.getParentFile().mkdirs();
				}
				fileOutputStream = new FileOutputStream(outFile);
				inputStream = file.getInputStream();
				IOUtils.copy(inputStream, fileOutputStream);

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
		videoSerivce.updateVideo(videoId, uploadPathDB);
		return R.ok();
	}

	/**
	 * 分页和搜索查询视频列表 isSaveRecord: 1-需要保存 2-不需要保存，或者为空的时候
	 * 
	 * @param video
	 * @param page
	 * @param isSaveRecord
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/showAll")
	public R showAll(@RequestBody Videos video, Integer page, Integer isSaveRecord, Integer pageSize) throws Exception {
		if (page == null)
			page = 1;
		if (pageSize == null)
			pageSize = PAGE_SIZE;
		PagedResult<VideosVO> result = videoSerivce.getAllVideos(video, isSaveRecord, page, pageSize);
		return R.ok(result);

	}

	@PostMapping(value = "/hot")
	public R showAll() throws Exception {
		return R.ok(videoSerivce.getHotwords());
	}

}
