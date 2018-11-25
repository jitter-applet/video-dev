package cn.zzuisa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.zzuisa.pojo.Videos;
import cn.zzuisa.pojo.vo.VideosVO;
import cn.zzuisa.utils.MyMapper;

public interface VideosCustomMapper extends MyMapper<Videos> {
	public List<VideosVO> queryAllVideos(@Param("videoDesc") String videoDesc);
}