package uub.logicallayer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import uub.enums.Exceptions;
import uub.enums.UserStatus;
import uub.model.Branch;
import uub.model.Employee;
import uub.model.User;
import uub.persistentinterfaces.IBranchDao;
import uub.staticlayer.CustomBankException;
import uub.staticlayer.EmployeeUtils;
import uub.staticlayer.HashEncoder;
import uub.staticlayer.HelperUtils;

public class AdminHelper extends EmployeeHelper {

	private IBranchDao branchDao;

	public AdminHelper() throws CustomBankException {
		super();
		try {

			Class<?> BranchDao = Class.forName("uub.persistentlayer.BranchDao");
			Constructor<?> branDao = BranchDao.getDeclaredConstructor();

			branchDao = (IBranchDao) branDao.newInstance();

		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

			throw new CustomBankException(Exceptions.DATABASE_CONNECTION_ERROR, e);

		}

	}

	public Map<Integer, Employee> getEmployees(int branchId, int limit, int offSet)
			throws CustomBankException {

		return employeeDao.getEmployeesWithBranch(branchId,UserStatus.ACTIVE, limit, offSet);
	}


	public Map<Integer, Branch> getAllBranches() throws CustomBankException {

		return branchDao.getBranches();
	}

	public void activateUser(int userId) throws CustomBankException {

		User user = new User();

		user.setId(userId);
		user.setStatus(UserStatus.ACTIVE);
		int result = userDao.updateUser(user);

		if (result == 0) {
			throw new CustomBankException(Exceptions.USER_NOT_FOUND);
		}

	}

	public void deActivateUser(int userId) throws CustomBankException {

		User user = new User();

		user.setId(userId);
		user.setStatus(UserStatus.INACTIVE);
		int result = userDao.updateUser(user);

		if (result == 0) {
			throw new CustomBankException(Exceptions.USER_NOT_FOUND);
		}

	}

	public void addEmployee(Employee employee) throws CustomBankException {

		HelperUtils.nullCheck(employee);
		employee.setStatus(UserStatus.ACTIVE);

		try {
			if (EmployeeUtils.validatePhone(employee.getPhone()) && EmployeeUtils.validateEmail(employee.getEmail())
					&& EmployeeUtils.validatePass(employee.getPassword())) {

				String password = employee.getPassword();
				employee.setPassword(HashEncoder.encode(password));

				employeeDao.addEmployee(List.of(employee));
			}
		} catch (Exception e) {
			throw new CustomBankException(Exceptions.SIGNUP_FAILED + e.getMessage());

		}

	}

	public void addBranch(Branch branch) throws CustomBankException {

		HelperUtils.nullCheck(branch);

		int id = branch.getId();

		branch.setId(id);
		String ifsc = EmployeeUtils.generateIFSC(id);
		branch.setiFSC(ifsc);

		branchDao.addBranch(List.of(branch));

	}

}
