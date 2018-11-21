/**
 * 
 */
package cn.zzuisa.service.impl;

import java.util.List;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.zzuisa.mapper.BgmMapper;
import cn.zzuisa.pojo.Bgm;
import cn.zzuisa.service.BgmService;

/**
 * @ClassName: UserServiceImpl
 * @Description: TODO
 * @author Ao
 * @date Nov 18, 2018
 *
 */
@Service
public class BgmServiceImpl implements BgmService {

	@Autowired
	private BgmMapper bgmMapper;
	@Autowired
	private Sid sid;

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<Bgm> queryBgmList() {
		return bgmMapper.selectAll();
	}

}

