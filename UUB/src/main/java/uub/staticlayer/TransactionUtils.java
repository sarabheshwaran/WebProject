package uub.staticlayer;

import uub.enums.AccountStatus;
import uub.enums.Exceptions;
import uub.model.Account;
import uub.model.Transaction;

public class TransactionUtils {

    public static String generateUniqueId(int accNO, int transactionAccNo) {
        long currentTimeMillis = System.currentTimeMillis();

        String uniqueIdString = String.format("%014d",currentTimeMillis) + String.format("%04d",accNO%10000) + String.format("%04d",transactionAccNo%10000) ;

        return uniqueIdString;
    }



	public static void validateAccount(Account account) throws CustomBankException {

		if (account.getStatus()==AccountStatus.INACTIVE) {
			throw new CustomBankException(Exceptions.INVALID_ACCOUNT);
		}
	}


	public static void validateTransaction(Transaction transaction) throws CustomBankException {

		if (transaction.getAccNo() == transaction.getTransactionAcc()) {
			throw new CustomBankException(Exceptions.INVALID_RECEIVER);
		}
		if (transaction.getAmount() <= 0) {
			throw new CustomBankException(Exceptions.INVALID_AMOUNT);

		}
	}

}

