package ski.resorts.service;

import org.springframework.data.jpa.repository.JpaRepository;

import ski.resorts.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, Long> {

}
