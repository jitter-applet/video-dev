package cn.zzuisa.service;

import java.util.List;

import cn.zzuisa.pojo.Bgm;

/**
 * 
 * @ClassName: UserService
 * @Description: TODO
 * @author Ao
 * @date Nov 18, 2018
 * 
 */
public interface BgmService {
	/**
	 * Bgm 列表
	 */
	public List<Bgm> queryBgmList();

	/**
	 * 根据Id查询
	 */
	public Bgm queryBgmById(String bgmId);

}
