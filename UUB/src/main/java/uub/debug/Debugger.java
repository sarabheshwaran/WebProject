package uub.debug;

import java.util.ArrayList;

import uub.cachelayer.Cache;
import uub.cachelayer.RedisCache;
import uub.enums.TransferType;
import uub.logicallayer.CustomerHelper;
import uub.logicallayer.EmployeeHelper;
import uub.model.Account;
import uub.model.Customer;
import uub.model.Transaction;
import uub.staticlayer.CustomBankException;
import uub.staticlayer.HashEncoder;

class Checker implements Runnable{



	@Override
	public void run() {

		Transaction transaction = new Transaction();

		transaction.setId(""+Math.random()*100);
		transaction.setAccNo(3);
		transaction.setAmount(100);
		transaction.setUserId(1);
		transaction.setType(TransferType.DEPOSIT);
		transaction.setDesc("dsfd");
		transaction.setTime(0);
		transaction.setStatus(0);


		try {
			CustomerHelper a = new CustomerHelper();
			a.makeTransaction(transaction, "sara");
		} catch (CustomBankException e) {
			e.printStackTrace();
		}

	}



}


class Checker2 implements Runnable{



	@Override
	public void run() {

		Customer c = new  Customer();

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
			a.addCustomer(c);
		} catch (CustomBankException e) {
			e.printStackTrace();
		}

	}



}


class Checker3 implements Runnable{

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

		Customer a = new Customer();
		Customer b = new Customer();
//		
		a.setEmail("dfg");
		b.setEmail("dfg");
		
		
		
		System.out.println(a.toString().equals(b.toString()));
	}

}
