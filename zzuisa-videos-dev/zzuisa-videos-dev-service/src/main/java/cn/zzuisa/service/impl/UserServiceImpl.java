/**
 * 
 */
package cn.zzuisa.service.impl;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.zzuisa.mapper.UsersMapper;
import cn.zzuisa.pojo.Users;
import cn.zzuisa.service.UserService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @ClassName: UserServiceImpl
 * @Description: TODO
 * @author Ao
 * @date Nov 18, 2018
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UsersMapper userMapper;
	@Autowired
	private Sid sid;

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public boolean queryUsernameIsExist(String username) {
		// TODO Auto-generated method stub
		Users user = new Users();
		user.setUsername(username);
		Users result = userMapper.selectOne(user);
		return result == null ? false : true;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void saveUser(Users user) {
		// TODO Auto-generated method stub
		String userId = sid.nextShort();
		user.setId(userId);
		userMapper.insert(user);
	}

	@Override
	public Users querryUsersForLogin(String username, String password) {
		// TODO Auto-generated method stub
		Example userExample = new Example(Users.class);
		Criteria criteria = userExample.createCriteria();
		criteria.andEqualTo("username", username);
		criteria.andEqualTo("password", password);
		Users result = userMapper.selectOneByExample(userExample);
		return result;
	}

	@Override
	public void updataUserInfo(Users user) {
		// TODO Auto-generated method stub
		Example userExample = new Example(Users.class);
		Criteria criteria = userExample.createCriteria();
		criteria.andEqualTo("id", user.getId());
		userMapper.updateByExampleSelective(user, userExample); // 非空更新，否则不更新

	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Users queryUserInfo(String userid) {
		Example userExample = new Example(Users.class);
		Criteria criteria = userExample.createCriteria();
		criteria.andEqualTo("id", userid);
		Users user = userMapper.selectOneByExample(userExample);
		return user;
	}

}
