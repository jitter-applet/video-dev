package cn.zzuisa.mapper;

import java.util.List;

import cn.zzuisa.pojo.SearchRecords;
import cn.zzuisa.utils.MyMapper;

public interface SearchRecordsMapper extends MyMapper<SearchRecords> {
	public List<String> getHotwords();
}