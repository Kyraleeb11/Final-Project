package ski.resorts.service;

import org.springframework.data.jpa.repository.JpaRepository;

import ski.resorts.entity.Resort;

public interface ResortDao extends JpaRepository<Resort, Long> {

}
