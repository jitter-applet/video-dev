package cn.zzuisa.service;

import cn.zzuisa.pojo.Users;

/**
 * 
 * @ClassName: UserService
 * @Description: TODO
 * @author Ao
 * @date Nov 18, 2018
 * 
 */
public interface UserService {
	/**
	 * 判断用户名是否存在
	 * 
	 * @param username
	 * @return
	 */
	public boolean queryUsernameIsExist(String username);

	/**
	 * 用户登录，根据用户名和密码查询用户
	 */
	public Users querryUsersForLogin(String username, String password);

	/**
	 * 保存用户(用户注册)
	 * 
	 * @param user
	 */
	public void saveUser(Users user);

}
