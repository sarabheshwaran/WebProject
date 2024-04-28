package uub.logicallayer;

import java.security.SecureRandom;
import java.util.List;

import uub.enums.Exceptions;
import uub.model.ApiAuth;
import uub.persistentinterfaces.IApiAuthDao;
import uub.persistentlayer.ApiAuthDao;
import uub.staticlayer.CustomBankException;
import uub.staticlayer.DateUtils;
import uub.staticlayer.HelperUtils;

public class ApiHelper {

	public IApiAuthDao apiAuthDao;

	public ApiHelper() {
		apiAuthDao = new ApiAuthDao();

	}

	public List<ApiAuth> getAllApiAuth(int userId) throws CustomBankException{
		
		return apiAuthDao.getApiKeyOfUsers(userId);
	}
	
	public ApiAuth getApiAuth(String key) throws CustomBankException {

		ApiAuth apiAuth = apiAuthDao.getApiAuth(key);

		if (apiAuth == null || !apiAuth.isValid()) {
			throw new CustomBankException(Exceptions.INVALID_API_KEY);
		} else {
			return apiAuth;
		}
	}
	
	public void validateApiKey(ApiAuth apiAuth) throws CustomBankException{
		
		HelperUtils.nullCheck(apiAuth);
		
		long createdAt = apiAuth.getCreatedTime();
		
		int validity = apiAuth.getValidity();
		
		long today = DateUtils.getTime();
		
		long difference = (today - createdAt)/(24*60*60*1000);
		
		if(difference >= validity) {
			throw new CustomBankException(Exceptions.INVALID_API_KEY);
		}
		
	}
	
	public void addApiAuth(ApiAuth apiAuth) throws CustomBankException{
		HelperUtils.nullCheck(apiAuth);
		
		apiAuth.setApiKey(generateKey());
		apiAuth.setCreatedTime(DateUtils.getTime());
		
		apiAuthDao.addAPIAuth(List.of(apiAuth));
	}
	
	public void deleteApiAuth(String apiKey) throws CustomBankException{
		
		apiAuthDao.removeApiKey(apiKey);
	}
	
	public String generateKey() {
		
		String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
		
		int length = 32;
		
		 SecureRandom random = new SecureRandom();
	     StringBuilder apiKey = new StringBuilder(length);
	     
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allowedCharacters.length());
            apiKey.append(allowedCharacters.charAt(randomIndex));
        }

        return apiKey.toString();
		
	}
	

}
