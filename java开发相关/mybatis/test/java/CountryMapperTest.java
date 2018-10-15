import com.zxf.zxfbatis.simple.model.Country;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class CountryMapperTest extends BaseMapperTest {


	@Test
	public void testSelectAll() {
		SqlSession sqlSession = getSqlSession();
		try{
			List<Country> countryList = sqlSession.selectList("com.zxf.zxfbatis.simple.mapper.CountryMapper.selectAll");
			printCountryList(countryList);
		}finally {
			sqlSession.close();
		}
	}

	private void printCountryList(List<Country> countries) {
		for(Country country : countries) {
			System.out.printf("%-4d%4s%4s\n", country.getId(), country.getCountryname(), country.getCountrycode());
		}
	}

	@Test
	public void insertById() {
		SqlSession sqlSession = getSqlSession();
		try{
			Country country = new Country();
			country.setId(110L);
			country.setCountrycode("001");
			country.setCountryname("兰尼斯特");
			sqlSession.insert("com.zxf.zxfbatis.simple.mapper.CountryMapper.insertById", country);
		}finally {
			sqlSession.commit();
		}
	}
}
