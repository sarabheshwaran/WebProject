package uub.persistentinterfaces;

import java.util.List;

import uub.model.ApiAuth;
import uub.staticlayer.CustomBankException;

public interface IApiAuthDao {

	ApiAuth getApiAuth(String apiKey) throws CustomBankException;

	void addAPIAuth(List<ApiAuth> s) throws CustomBankException;

	List<ApiAuth> getApiKeyOfUsers(int userId) throws CustomBankException;

	void removeApiKey(String key) throws CustomBankException;

	
	
}
