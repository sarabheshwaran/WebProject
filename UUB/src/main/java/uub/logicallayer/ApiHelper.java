package uub.logicallayer;

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

	public ApiAuth getApiAuth(String key) throws CustomBankException {

		ApiAuth apiAuth = apiAuthDao.getApiAuth(key);

		if (apiAuth != null) {
			
			validateApiKey(apiAuth);
			return apiAuth;
		} else {
			throw new CustomBankException(Exceptions.INVALID_API_KEY);

		}
	}
	
	public void validateApiKey(ApiAuth apiAuth) throws CustomBankException{
		
		HelperUtils.nullCheck(apiAuth);
		
		long createdAt = apiAuth.getCreatedTime();
		
		int validity = apiAuth.getValidity();
		
		long today = DateUtils.getTime();
		
		long difference = (today - createdAt)/(24*60*60*1000);
		
		if(difference < validity) {
			deleteApiAuth(apiAuth.getApiKey());
			throw new CustomBankException(Exceptions.INVALID_API_KEY);
		}
		
	}
	
	public void addApiAuth(ApiAuth apiAuth) throws CustomBankException{
		
		HelperUtils.nullCheck(apiAuth);
		
		apiAuth.setCreatedTime(DateUtils.getTime());
		
		apiAuthDao.addAPIAuth(List.of(apiAuth));
	}
	
	public void deleteApiAuth(String apiKey) throws CustomBankException{
		
		apiAuthDao.removeApiKey(apiKey);
	}
	
	

}
