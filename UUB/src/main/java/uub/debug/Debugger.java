package uub.debug;

import java.io.Serializable;

import uub.cachelayer.Cache;
import uub.cachelayer.RedisCache;
import uub.logicallayer.CustomerHelper;
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
//
//		try {
//			EmployeeHelper a = new EmployeeHelper();
//			System.out.println(a.addCustomer(c));
//		} catch (CustomBankException e) {
//			e.printStackTrace();
//		}

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
 class MyClass implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Singleton instance
    private static final MyClass INSTANCE = new MyClass();
    
    // Private constructor to prevent instantiation
    private MyClass() {
        // Initialization code here
    }
    
    // Public method to access the singleton instance
    public static MyClass getInstance() {
        return INSTANCE;
    }
    
    // Override readResolve method to ensure singleton behavior during deserialization
    protected Object readResolve() {
        // Return the singleton instance to ensure only one instance is used
        return INSTANCE;
    }
}
class Lock implements Serializable{
	
	private static final long serialVersionUID = 1L;
	protected Lock readResolve(){
		return this;
	}
}

public class Debugger {

	public static void main(String[] args) throws CustomBankException {
		
		Cache<String,MyClass> c = new RedisCache<String, MyClass>(6380);
		
		MyClass lock1 = MyClass.getInstance();
		
		c.set("a", lock1);
		
		MyClass lock2 = c.get("a");
		
		System.out.println(lock1==lock2);


	}

}
