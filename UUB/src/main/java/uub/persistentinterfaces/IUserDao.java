package uub.persistentinterfaces;


import java.util.List;

import uub.enums.UserStatus;
import uub.model.User;
import uub.staticlayer.CustomBankException;

public interface IUserDao {


	int updateUser(User user) throws CustomBankException;

	List<User> getUser(int userId, UserStatus status) throws CustomBankException;

}
