package cn.zzuisa.service.impl;

import java.awt.image.RescaleOp;
import java.util.List;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.zzuisa.mapper.SearchRecordsMapper;
import cn.zzuisa.mapper.VideosCustomMapper;
import cn.zzuisa.mapper.VideosMapper;
import cn.zzuisa.pojo.SearchRecords;
import cn.zzuisa.pojo.Videos;
import cn.zzuisa.pojo.vo.VideosVO;
import cn.zzuisa.service.VideoService;
import cn.zzuisa.utils.PagedResult;

@Service
public class VideoServiceImpl implements VideoService {

	@Autowired
	private Sid sid;
	@Autowired
	private VideosMapper videosMapper;
	@Autowired
	private VideosCustomMapper videosCustomMapper;
	@Autowired
	private SearchRecordsMapper searchRecordsMapper;

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public String saveVideo(Videos video) {
		// TODO Auto-generated method stub
		String id = sid.nextShort();
		video.setId(id);
		videosMapper.insertSelective(video);
		return id;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public void updateVideo(String videoId, String coverPath) {
		// TODO Auto-generated method stub
		Videos video = new Videos();
		video.setId(videoId);
		video.setCoverPath(coverPath);
		videosMapper.updateByPrimaryKeySelective(video);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public PagedResult<VideosVO> getAllVideos(Videos video, Integer isSaveRecord, Integer page, Integer pageSize) {
		String desc = video.getVideoDesc();
		//保存热搜词
		if (desc != null && isSaveRecord == 1) {
			SearchRecords record = new SearchRecords();
			String recordId = sid.nextShort();
			record.setId(recordId);
			record.setContent(desc);
			searchRecordsMapper.insert(record);
		}
		// TODO Auto-generated method stub
		PageHelper.startPage(page, pageSize);
		List<VideosVO> list = videosCustomMapper.queryAllVideos(desc);
		PageInfo<VideosVO> pageList = new PageInfo<VideosVO>(list);
		PagedResult<VideosVO> pageResult = new PagedResult<VideosVO>();
		pageResult.setPage(page);
		pageResult.setTotal(pageList.getPages());
		pageResult.setRows(list);
		pageResult.setRecords(pageList.getTotal());
		return pageResult;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<String> getHotwords() {
		return searchRecordsMapper.getHotwords();
	}

}
