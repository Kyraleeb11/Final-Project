package ski.resorts.service;

import org.springframework.data.jpa.repository.JpaRepository;

import ski.resorts.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Long> {

}
