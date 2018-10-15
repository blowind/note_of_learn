import com.zxf.zxfbatis.simple.mapper.UserMapper;
import com.zxf.zxfbatis.simple.model.SysRole;
import com.zxf.zxfbatis.simple.model.SysUser;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class UserMapperTest extends BaseMapperTest{

	@Test
	public void testSelectById() {
		SqlSession sqlSession = getSqlSession();
		try{
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectById(1L);
			Assert.assertNotNull(user);
			Assert.assertEquals("admin", user.getUserName());
		}finally {
			sqlSession.close();
		}
	}

	@Test
	public void testSelectAll() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<SysUser>  userMappers = userMapper.selectAll();
			Assert.assertNotNull(userMappers);
			Assert.assertTrue(userMappers.size() > 0);
		}finally {
			sqlSession.close();
		}
	}

	@Test
	public void testSelectRolesByUserId() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<SysRole> sysRoles = userMapper.selectRolesByUserId(1L);
			Assert.assertNotNull(sysRoles);
			Assert.assertTrue(sysRoles.size() > 0);
		}finally {
			sqlSession.close();
		}
	}

	@Test
	public void testInsert() {
		SqlSession sqlSession = getSqlSession();
		try{
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = new SysUser();
			user.setUserName("test1");
			user.setUserPassword("123456");
			user.setUserEmail("test@111.com");
			user.setUserInfo("test info");
			user.setHeadImg(new byte[]{1,2,3});
			user.setCreateTime(new Date());
			int result = userMapper.insert(user);
			Assert.assertEquals(1, result);
			Assert.assertNull(user.getId());
		}finally {
			sqlSession.rollback();
			sqlSession.close();
		}
	}

	@Test
	public void testInsert3() {
		SqlSession sqlSession = getSqlSession();
		try{
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = new SysUser();
			user.setUserName("test2");
			user.setUserPassword("songsiying");
			user.setUserEmail("zxf@153.com");
			user.setUserInfo("hahaha");
			user.setHeadImg(new byte[]{1, 2, 3});
			user.setCreateTime(new Date());
			int result = userMapper.insert2(user);
			Assert.assertEquals(1, result);
			Assert.assertNotNull(user.getId());
		}finally {
			sqlSession.rollback();
			sqlSession.close();
		}
	}

	@Test
	public void testUpdateById() {
		SqlSession sqlSession = getSqlSession();
		try{
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectById(1L);
			Assert.assertEquals("admin", user.getUserName());
			user.setUserName("admin_test");
			int result = userMapper.updateById(user);
			Assert.assertEquals(1, result);
			user = userMapper.selectById(1L);
			Assert.assertEquals("admin_test", user.getUserName());
		}finally {
			sqlSession.rollback();
			sqlSession.close();
		}
	}

	@Test
	public void testDeleteById() {
		SqlSession sqlSession = getSqlSession();
		try{
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user1 = userMapper.selectById(1L);
			Assert.assertNotNull(user1);
			Assert.assertEquals(1, userMapper.deleteById(1L));
			Assert.assertNull(userMapper.selectById(1L));
		}finally {
			sqlSession.rollback();
			sqlSession.close();
		}
	}

	@Test
	public void testSelectRolesByUserIdAndRoleEnabled() {
		SqlSession sqlSession = getSqlSession();
		try{
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<SysRole> userList = userMapper.selectRolesByUserIdAndRoleEnabled(1L, 1);
			Assert.assertNotNull(userList);
			Assert.assertTrue(userList.size() > 0);
		}finally {
			sqlSession.close();
		}
	}

	@Test
	public void testSelectByUser() {
		SqlSession sqlSession = getSqlSession();
		try{
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser query = new SysUser();
//			query.setUserName("ad");
			query.setUserEmail("test@163.com");
			List<SysUser> userList = userMapper.selectByUser(query);
			Assert.assertTrue(userList.size() > 0);
		}finally {
			sqlSession.close();
		}
	}

	@Test
	public void testSelectByUserIf() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = new SysUser();
			user.setId(1L);
			user.setUserName("myname");
			int result = userMapper.updateByIdSelective(user);
			Assert.assertEquals(1, result);
			user = userMapper.selectById(1L);
			Assert.assertEquals("myname", user.getUserName());
		}finally {
			sqlSession.rollback();
			sqlSession.close();
		}
	}

	@Test
	public void testSelectByIdOrUserName() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser query = new SysUser();
			query.setId(1L);
			SysUser user = userMapper.selectByIdOrUserName(query);
			Assert.assertNotNull(user);
		}finally {
			sqlSession.close();
		}
	}

	@Test
	public void testSelectByIdList() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<Long> idList = new ArrayList<>();
			idList.add(1L);
			idList.add(1001L);
			List<SysUser> userList = userMapper.selectByIdList(idList);
			Assert.assertEquals(2, userList.size());
		}finally {
			sqlSession.close();
		}
	}

	@Test
	public void testInsertList() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<SysUser> userList = new ArrayList<>();
			for(int i=0; i<2; i++) {
				SysUser user = new SysUser();
				user.setUserName("test" + i);
				user.setUserPassword("123456");
				user.setUserEmail("test@mybatis.tk");
				userList.add(user);
			}

			int result = userMapper.insertList(userList);
			Assert.assertEquals(2, result);
			for(SysUser user : userList) {
				System.out.println(user.getId());
			}

		}finally {
			sqlSession.rollback();
			sqlSession.close();
		}
	}

	@Test
	public void testUpdateByMap() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			Map<String, Object> map = new HashMap<>();
			map.put("id", 1L);
			map.put("user_email", "my@163.kkk");
			map.put("user_password", "123321");
			userMapper.updateByMap(map);
			SysUser user = userMapper.selectById(1L);
			Assert.assertEquals("my@163.kkk", user.getUserEmail());
		}finally {
			sqlSession.rollback();
			sqlSession.close();
		}
	}

}
