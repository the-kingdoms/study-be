package templates;

import java.util.List;

import org.apache.catalina.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
	 List<User> getAllUsers();
}
