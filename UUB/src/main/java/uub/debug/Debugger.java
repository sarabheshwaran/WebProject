package uub.debug;

import uub.logicallayer.ApiHelper;
import uub.logicallayer.CustomerHelper;
import uub.logicallayer.EmployeeHelper;
import uub.logicallayer.UserHelper;
import uub.model.Customer;
import uub.staticlayer.CustomBankException;

class Checker implements Runnable {

	@Override
	public void run() {

//		Transaction transaction = new Transaction();
//
//		transaction.setId(""+Math.random()*100);
//		transaction.setAccNo(3);
//		transaction.setAmount(100);
//		transaction.setUserId(1);
//		transaction.setType(TransferType.DEPOSIT);
//		transaction.setDesc("dsfd");
//		transaction.setTime(0);
//		transaction.setStatus(0);

		try {
			CustomerHelper a = new CustomerHelper();
//			a.makeTransaction(transaction, "sara");

			System.out.println(a.getCustomer(1));
		} catch (CustomBankException e) {
			e.printStackTrace();
		}

	}

}

class Checker2 implements Runnable {

	@Override
	public void run() {

		Customer c = new Customer();

		c.setAadhar("12345645367890");
		c.setPAN("dsfdg");
		c.setDOB(0);
		c.setEmail("adsf");
		c.setGender("adsf");
		c.setName("adsf");
		c.setPassword("sads");
		c.setPhone("dsf");
		c.setStatus(0);
		c.setAddress("dsf");
//		c.setUserType(1);

		try {
			EmployeeHelper a = new EmployeeHelper();
//			System.out.println(a.addCustomer(c));
		} catch (CustomBankException e) {
			e.printStackTrace();
		}

	}

}

class Checker3 implements Runnable {

	public int x;

	public Checker3(int x) {
		this.x = x;
	}

	@Override
	public void run() {

		try {
			CustomerHelper a = new CustomerHelper();
			System.out.println(a.getCustomer(x));
		} catch (CustomBankException e) {
			e.printStackTrace();
		}

	}

}

public class Debugger {

	public static void main(String[] args) throws CustomBankException {

//		TransactionHelper a = new TransactionHelper();

//

//	

		Thread[] threadPool = new Thread[10];
		for (int i = 0; i < 10; i++) {
			threadPool[i] = new Thread(new Checker());
		}
		
		for(Thread t : threadPool) {
			t.start();
		}


	}

}
