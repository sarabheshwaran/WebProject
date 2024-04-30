package uub.debug;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import uub.logicallayer.CustomerHelper;
import uub.staticlayer.CustomBankException;

 class NamedPermitSemaphore {
    private Semaphore[] semaphores;
BlockingQueue<String> a =null;
    public NamedPermitSemaphore(int numPermits) {
        semaphores = new Semaphore[numPermits];
        for (int i = 0; i < numPermits; i++) {
            semaphores[i] = new Semaphore(1); // Initialize with one permit each
        }
    }

    public void acquire(String permitName) throws InterruptedException {
        for (Semaphore semaphore : semaphores) {
            if (semaphore.tryAcquire()) {
                System.out.println("Thread acquired permit: " + permitName);
                return;
            }
        }
        throw new IllegalStateException("No available permits for: " + permitName);
    }

    public void release(String permitName) {
        for (Semaphore semaphore : semaphores) {
            if (semaphore.availablePermits() < 1) {
                semaphore.release();
                System.out.println("Thread released permit: " + permitName);
                return;
            }
        }
        throw new IllegalStateException("No permit to release for: " + permitName);
    }
}

class Checker implements Runnable {
	public static NamedPermitSemaphore s = new NamedPermitSemaphore(5);

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
            // Acquire a permit from the semaphore

            s.acquire("1");
//            System.out.println("Worker " + Thread.currentThread().getName() + " acquired permit");
            Thread.sleep(2000); // Simulate work
//            System.out.println("Worker " + Thread.currentThread().getName() + " finished work");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Release the permit back to the semaphore
            s.release("1");
//            System.out.println("Worker " + Thread.currentThread().getName() + " released permit");
        }
		

	}

}

class Checker2 implements Runnable {

	@Override
	public void run() {

//		Customer c = new Customer();
//
//		c.setAadhar("12345645367890");
//		c.setPAN("dsfdg");
//		c.setDOB(0);
//		c.setEmail("adsf");
//		c.setGender("adsf");
//		c.setName("adsf");
//		c.setPassword("sads");
//		c.setPhone("dsf");
//		c.setStatus(0);
//		c.setAddress("dsf");
		
		
		
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

public class Debugger {
	
	public static void foo() {

	Semaphore s = new Semaphore(2);
	
	try {
		s.acquire();
		System.out.println(Thread.currentThread().getName()  + " in");
		
		Thread.sleep(5000);
		
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	finally {
		s.release();
		System.out.println(Thread.currentThread().getName()  +" out");
		
	}
	
	}
	public static void main(String[] args) throws CustomBankException{


//		Thread[] threadPool = new Thread[10];
//		for (int i = 0; i < 10; i++) {
//			threadPool[i] = new Thread(new Checker());
//		}
//		
//		for(Thread t : threadPool) {
//			t.start();
//		}
		
		Semaphore s = new Semaphore(2);
		
		s.acquireUninterruptibly();
		try {
			s.acquire();
			s.acquire();
			s.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		



	}

}
