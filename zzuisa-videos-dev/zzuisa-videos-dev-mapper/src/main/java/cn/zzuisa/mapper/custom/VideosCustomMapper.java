package cn.zzuisa.mapper.custom;

import java.util.List;

import cn.zzuisa.pojo.Videos;
import cn.zzuisa.pojo.vo.VideosVO;
import cn.zzuisa.utils.MyMapper;

public interface VideosCustomMapper extends MyMapper<Videos> {
	public List<VideosVO> queryAllVideos(); 
}