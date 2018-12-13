package com.zxf.spring.security.service.impl;

import com.zxf.spring.security.dbservice.RoleRepository;
import com.zxf.spring.security.dbservice.UserRepository;
import com.zxf.spring.security.model.DatabaseUser;
import com.zxf.spring.security.model.DatabaseRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *  @ClassName: UserDetailsServiceImpl
 *  @Description:  实现spring.security相关的UserDetailsService接口，用于AuthenticationManagerBuilder构造参数
 *  @Author: ZhangXuefeng
 *  @Date: 2018/12/13 17:19
 *  @Version: 1.0
 **/
@Service
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<DatabaseUser> dbUser = userRepository.findByUserName(username);
		List<DatabaseRole> roleList = roleRepository.findByUsername(username);
		if(dbUser == null || dbUser.size() == 0) {
			throw new UsernameNotFoundException("用户不存在");
		}else{
			return changeToUser(dbUser.get(0), roleList);
		}
	}

	private UserDetails changeToUser(DatabaseUser dbUser, List<DatabaseRole> roleList) {
		List<GrantedAuthority> authorityList = new ArrayList<>();
		for(DatabaseRole role : roleList) {
			GrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleName());
			authorityList.add(authority);
		}
		UserDetails userDetails = new User(dbUser.getUserName(), dbUser.getPwd(), authorityList);
		return userDetails;
	}
}
