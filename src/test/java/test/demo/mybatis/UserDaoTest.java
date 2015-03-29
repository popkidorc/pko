package test.demo.mybatis;

import java.io.Reader;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.springframework.util.ClassUtils;

import demo.mybatis.dao.IUserDao;
import demo.mybatis.pojo.User;

public class UserDaoTest {

	@Test
	public void userDaoTest() throws Exception {
		// String resource = "mybatis/demo/config/MyBatis-Configuration.xml";
		// Reader reader = Resources.getResourceAsReader(resource);
		// SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		// SqlSessionFactory factory = builder.build(reader);
		//
		// SqlSession session = factory.openSession();
		// IUserDao userDao = session.getMapper(IUserDao.class);
		// //
		// // User user = new User();
		// // user.setUserName("popkidorc2");
		// // user.setPassword("123456");
		// // user.setComment("备注");
		// //
		// // userDao.insert(user);
		// // System.out.println("记录条数：" + userDao.countAll());
		//
		// List<User> users = userDao.selectAll();
		// Iterator<User> iter = users.iterator();
		// while (iter.hasNext()) {
		// User u = iter.next();
		// System.out.println("用户名：" + u.getUserName() + "密码："
		// + u.getPassword());
		// }
		//
		// // user.setComment("comment");
		// // userDao.update(user);
		// // User u = userDao.findByUserName("hongye");
		// // System.out.println(u.getComment());
		// //
		// // userDao.delete("hongye");
		// // System.out.println("记录条数：" + userDao.countAll());
		//
		// session.commit();
		// session.close();
		Class clazz = User.class;
		String clazzName = clazz.getName();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			System.out.println("===" + field.getName());
		}
		// System.out.println(ClassUtils.getShortName(clazz));
	}
}
