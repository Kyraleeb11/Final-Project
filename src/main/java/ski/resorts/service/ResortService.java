package ski.resorts.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ski.resorts.controller.model.ResortData;
import ski.resorts.controller.model.ResortData.ResortCustomer;
import ski.resorts.controller.model.ResortData.ResortEmployee;
import ski.resorts.entity.Customer;
import ski.resorts.entity.Employee;
import ski.resorts.entity.Resort;

@Service
public class ResortService {

	@Autowired
	private ResortDao resortDao;

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private EmployeeDao employeeDao;

	@Transactional(readOnly = false)
	public ResortData saveResort(ResortData resortData) {
		Long resortId = resortData.getResortId();
		Resort resort = findOrCreateResort(resortId);

		copyResortFields(resort, resortData);

		Resort dbResort = resortDao.save(resort);
		return new ResortData(dbResort);
	}

	private void copyResortFields(Resort resort, ResortData resortData) {
		resort.setResortId(resortData.getResortId());
		resort.setResortName(resortData.getResortName());
		resort.setResortAddress(resortData.getResortAddress());
		resort.setResortState(resortData.getResortState());
	}

	private Resort findOrCreateResort(Long resortId) {
		Resort resort;

		if (Objects.isNull(resortId)) {
			resort = new Resort();
		} else {
			resort = findResortById(resortId);
		}
		return resort;
	}

	private Resort findResortById(Long resortId) {
		return resortDao.findById(resortId)
				.orElseThrow(() -> new NoSuchElementException("Resort with ID=" + resortId + " does not exist."));
	}

	@Transactional(readOnly = false)
	public ResortEmployee saveEmployee(Long resortId, ResortEmployee resortEmployee) {
		Resort resort = findResortById(resortId);
		Employee employee = findOrCreateEmployee(resortId, resortEmployee.getEmployeeId());

		copyEmployeeFields(employee, resortEmployee);
		employee.setResort(resort);
		resort.getEmployees().add(employee);

		return new ResortEmployee(employeeDao.save(employee));
	}

	private void copyEmployeeFields(Employee employee, ResortEmployee resortEmployee) {
		employee.setEmployeeId(resortEmployee.getEmployeeId());
		employee.setEmployeeFirstName(resortEmployee.getEmployeeFirstName());
		employee.setEmployeeLastName(resortEmployee.getEmployeeLastName());
		employee.setEmployeePhoneNumber(resortEmployee.getEmployeePhone());
		employee.setEmployeePosition(resortEmployee.getEmployeePosition());
	}

	private Employee findOrCreateEmployee(Long resortId, Long employeeId) {
		if (Objects.isNull(employeeId)) {
			return new Employee();
		} else {
			return findEmployeeById(resortId, employeeId);
		}
	}

	private Employee findEmployeeById(Long resortId, Long employeeId) {
		Employee employee = employeeDao.findById(employeeId)
				.orElseThrow(() -> new NoSuchElementException("Employee with ID=" + employeeId + " does not exist."));

		if (employee.getResort().getResortId() != resortId) {
			throw new IllegalArgumentException(
					"Employee with ID=" + employeeId + " does not work at pet store with ID=" + resortId + ".");
		}
		return employee;
	}

	@Transactional(readOnly = false)
	public ResortCustomer saveCustomer(Long resortId, ResortCustomer resortCustomer) {
		Resort resort = findResortById(resortId);
		Customer customer = findOrCreateCustomer(resortId, resortCustomer.getCustomerId());

		copyCustomerFields(customer, resortCustomer);

		customer.getResort().add(resort);
		// Add customer to Set in PetStore
		resort.getCustomers().add(customer);

		return new ResortCustomer(customerDao.save(customer));
	}

	private void copyCustomerFields(Customer customer, ResortCustomer resortCustomer) {
		customer.setCustomerId(resortCustomer.getCustomerId());
		customer.setCustomerFirstName(resortCustomer.getCustomerFirstName());
		customer.setCustomerLastName(resortCustomer.getCustomerLastName());
		customer.setCustomerEmail(resortCustomer.getCustomerEmail());
	}

	private Customer findOrCreateCustomer(Long resortId, Long customerId) {
		if (Objects.isNull(customerId)) {
			return new Customer();
		} else {
			return findCustomerById(resortId, customerId);
		}
	}

	private Customer findCustomerById(Long resortId, Long customerId) {
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new NoSuchElementException("Customer with ID=" + customerId + " does not exist."));
		boolean found = false;

		for (Resort resort : customer.getResort()) {
			if (resort.getResortId() == resortId) {
				found = true;
				break;
			}
		}
		if (!found) {
			throw new IllegalArgumentException(
					"Customer with ID=" + customerId + " does not ski at resort with ID=" + resortId + ".");
		}
		return customer;
	}

	@Transactional(readOnly = true)
	public List<ResortData> retrieveAllResorts() {
		List<ResortData> resortData = new LinkedList<>();

		for (Resort resort : resortDao.findAll()) {
			ResortData psd = new ResortData(resort);

			// Removes customer and employee data from data list before returning.
			psd.getCustomers().clear();
			psd.getEmployees().clear();

			resortData.add(psd);
		}

		return resortData;
	}

	@Transactional(readOnly = true)
	public ResortData retrieveResortById(Long resortId) {
		return new ResortData(findResortById(resortId));
	}

	public void deleteResortById(Long resortId) {
		Resort resort = findResortById(resortId);

		resortDao.delete(resort);
	}

}
