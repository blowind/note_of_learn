
import org.apache.ibatis.annotations.Param;


public interface POJOMapper {
	POJOModel selectById(Long id);
	
	List<POJOModel> selectAll();

	List<SysRole> selectRolesByUserId(Long userId);

	int insert(POJOModel pojoModel);

	int updateById(POJOModel pojoModel);

	int deleteById(Long id);

	List<SysRole> selectRolesByUserIdAndRoleEnabled(
			@Param("userId") Long userId,
			@Param("enabled") Integer enabled
	);

	List<POJOModel> selectByUser(POJOModel pojoModel);

	int updateByIdSelective(POJOModel pojoModel);

	POJOModel selectByIdOrUserName(POJOModel pojoModel);

	List<POJOModel> selectByIdList(List<Long> idList);

	int insertList(List<POJOModel> userList);

	int updateByMap(Map<String, Object> map);
}