package uub.persistentinterfaces;

import java.util.List;
import java.util.Map;

import uub.enums.UserStatus;
import uub.model.Employee;
import uub.staticlayer.CustomBankException;

public interface IEmployeeDao {

	public void addEmployee(List<Employee> employees) throws CustomBankException;

	void updateEmployee(Employee employee) throws CustomBankException;

	List<Employee> getEmployees(int id) throws CustomBankException;

	Map<Integer, Employee> getEmployeesWithBranch(int branchId, UserStatus status, int limit, int offSet)
			throws CustomBankException;


}