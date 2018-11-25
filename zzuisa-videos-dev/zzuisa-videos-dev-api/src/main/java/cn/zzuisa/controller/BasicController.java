package cn.zzuisa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import cn.zzuisa.utils.RedisOperator;

@RestController
public class BasicController {

	@Autowired
	public RedisOperator redis;

	public static final String USER_REDIS_SESSION = "user-redis-session";

	public static final String MACPATH = "file:/Users/zzu/Desktop/videos/";
	public static final String MACPATHOFVIDEO = "file:/Users/zzu/Desktop/videos/";
	public static final String WINDOWSPATH = "D:/wx-videosResource";
	public static final String WINDOWSPATHOFVIDEO = "D:/wx-videosResource/";
	public static final String WINDOWSFFMPEG_EXE = "E:\\ffmpeg\\bin\\ffmpeg.exe";
	public static final String UBUNTUPATH = "/usr/wx-videos";
	public static final String UBUNTUPATHOFVIDEO = "/usr/wx-videos";
	public static final String UBUNTUFFMPEG = "ffmpeg";
	// 文件保存的命名空间
	public static final String FILE_SPACE = "C:/imooc_videos_dev";

	// ffmpeg所在目录
	public static final String FFMPEG_EXE = "C:\\ffmpeg\\bin\\ffmpeg.exe";

	// 每页分页的记录数
	public static final Integer PAGE_SIZE = 5;
	

}
