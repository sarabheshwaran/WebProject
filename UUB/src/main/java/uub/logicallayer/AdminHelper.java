package uub.logicallayer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import uub.enums.Exceptions;
import uub.enums.UserStatus;
import uub.model.Branch;
import uub.model.Employee;
import uub.persistentinterfaces.IBranchDao;
import uub.staticlayer.CustomBankException;
import uub.staticlayer.DateUtils;
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

	public Map<Integer, Employee> getEmployees(int branchId, int limit, int offSet) throws CustomBankException {

		return employeeDao.getEmployeesWithBranch(branchId, UserStatus.ACTIVE, limit, offSet);
	}

	public Map<Integer, Branch> getAllBranches() throws CustomBankException {

		return branchDao.getBranches();
	}

	public void addEmployee(Employee employee) throws CustomBankException {

		HelperUtils.nullCheck(employee);
		employee.setStatus(UserStatus.ACTIVE);

		try {
			if (EmployeeUtils.validatePhone(employee.getPhone()) && EmployeeUtils.validateEmail(employee.getEmail())
					&& EmployeeUtils.validatePass(employee.getPassword())) {

				String password = employee.getPassword();
				employee.setPassword(HashEncoder.encode(password));
				employee.setLastModifiedTime(DateUtils.getTime());

				employeeDao.addEmployee(List.of(employee));
			}
		} catch (Exception e) {
			throw new CustomBankException(Exceptions.SIGNUP_FAILED + e.getMessage());

		}

	}

	public void editEmployee(int id, Employee employee) throws CustomBankException {

		HelperUtils.nullCheck(employee);

		Employee compareObject = getEmployee(id);

		int count = 0;
		employee.setId(id);
		try {
			if (EmployeeUtils.validatePhone(employee.getPhone()) && EmployeeUtils.validateEmail(employee.getEmail())) {
				if (employee.getName().equals(compareObject.getName())) {
					employee.setName(null);
					count++;
				}
				if (employee.getEmail().equals(compareObject.getEmail())) {
					employee.setEmail(null);
					count++;
				}
				if (employee.getPhone().equals(compareObject.getPhone())) {
					employee.setPhone(null);
					count++;
				}
				if (employee.getDOB() == compareObject.getDOB()) {
					employee.setDOB(0l);
					count++;
				}
				if (employee.getGender().equals(compareObject.getGender())) {
					employee.setGender(null);
					count++;
				}
				if (employee.getRole().equals(compareObject.getRole())) {
					employee.setRole(null);
					count++;
				}
				if (employee.getBranchId() == compareObject.getBranchId()) {
					employee.setBranchId(0);
					count++;
				}

				if (count < 7) {

					employee.setLastModifiedTime(DateUtils.getTime());
					employeeDao.updateEmployee(employee);
					customerCache.rem(id);
				}
			}
		} catch (CustomBankException e) {
			throw new CustomBankException(Exceptions.UPDATE_FAILED + e.getMessage());
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
