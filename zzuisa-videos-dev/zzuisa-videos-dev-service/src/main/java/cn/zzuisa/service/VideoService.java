package cn.zzuisa.service;

import cn.zzuisa.pojo.Videos;
import cn.zzuisa.pojo.vo.VideosVO;
import cn.zzuisa.utils.PagedResult;

/**
 * 
 * @ClassName: UserService
 * @Description: TODO
 * @author Ao
 * @date Nov 18, 2018
 * 
 */
public interface VideoService {
	/**
	 * 保存视频
	 * @param video
	 */
	public String saveVideo(Videos video);
	/**
	 * 修改视频封面
	 * @param videoId
	 * @param coverPath
	 * @return
	 */
	public void updateVideo(String videoId,String coverPath);
	/**
	 * 分页查询视频
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public PagedResult<VideosVO> getAllVideos (Integer page,Integer pageSize);
}
