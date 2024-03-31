package ski.resorts.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import ski.resorts.entity.Customer;
import ski.resorts.entity.Employee;
import ski.resorts.entity.Resort;

@Data
@NoArgsConstructor

public class ResortData {

	private Long resortId;
	private String resortName;
	private String resortAddress;
	private String resortState;

	private Set<ResortCustomer> customers = new HashSet<>();

	private Set<ResortEmployee> employees = new HashSet<>();

	public ResortData(Resort resort) {
		resortId = resort.getResortId();
		resortName = resort.getResortName();
		resortAddress = resort.getResortAddress();
		resortState = resort.getResortState();

		for (Customer customer : resort.getCustomers()) {
			customers.add(new ResortCustomer(customer));
		}
		for (Employee employee : resort.getEmployees()) {
			employees.add(new ResortEmployee(employee));
		}

	}

	@Data
	@NoArgsConstructor
	public static class ResortCustomer {
		private Long customerId;
		private String customerFirstName;
		private String customerLastName;
		private String customerEmail;

		public ResortCustomer(Customer customer) {
			customerId = customer.getCustomerId();
			customerFirstName = customer.getCustomerFirstName();
			customerLastName = customer.getCustomerLastName();
			customerEmail = customer.getCustomerEmail();
		}

	}

	@Data
	@NoArgsConstructor
	public static class ResortEmployee {

		private Long employeeId;
		private String employeeFirstName;
		private String employeeLastName;
		private String employeePhone;
		private String employeeEmail;
		private String employeePosition;

		public ResortEmployee(Employee employee) {

			employeeId = employee.getEmployeeId();
			employeeFirstName = employee.getEmployeeFirstName();
			employeeLastName = employee.getEmployeeLastName();
			employeePhone = employee.getEmployeePhoneNumber();
			employeeEmail = employee.getEmployeeEmail();
			employeePosition = employee.getEmployeePosition();
		}
	}

}
