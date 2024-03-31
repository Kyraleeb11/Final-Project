package ski.resorts.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import ski.resorts.controller.model.ResortData;
import ski.resorts.controller.model.ResortData.ResortCustomer;
import ski.resorts.controller.model.ResortData.ResortEmployee;
import ski.resorts.service.ResortService;

@RestController
@RequestMapping("/resort")
@Slf4j
public class ResortController {

	@Autowired
	private ResortService resortService;

	@PostMapping("/resort")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResortData postResort(@RequestBody ResortData resortData) {
		log.info("Creating Resort {}", resortData);
		return resortService.saveResort(resortData);
	}

	@PutMapping("/{resortId}")
	public ResortData putResort(@RequestBody ResortData resortData, @PathVariable Long resortId) {
		resortData.setResortId(resortId);
		log.info("Updating Resort {} with {}", resortId, resortData);
		return resortService.saveResort(resortData);

	}

	@PostMapping("/{resortId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResortEmployee insertEmployee(@PathVariable Long resortId, @RequestBody ResortEmployee resortEmployee) {
		log.info("Creating employee {} for resort with ID={}", resortEmployee.getEmployeeId(), resortId);

		return resortService.saveEmployee(resortId, resortEmployee);
	}

	@PostMapping("/{resortId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResortCustomer insertCustomer(@PathVariable Long resortId, @RequestBody ResortCustomer resortCustomer) {
		log.info("Creating customer {} for resort with ID={}", resortCustomer.getCustomerId(), resortId);

		return resortService.saveCustomer(resortId, resortCustomer);
	}

	@GetMapping
	public List<ResortData> listAllResorts() {
		log.info("Listing all resorts.");
		return resortService.retrieveAllResorts();
	}

	@GetMapping("/{resortId}")
	public ResortData getResortById(@PathVariable Long resortId) {
		log.info("Retrieving resort with ID={}", resortId);
		return resortService.retrieveResortById(resortId);
	}

	@DeleteMapping("/{resortId}")
	public Map<String, String> deleteResortById(@PathVariable Long resortId) {
		log.info("Delete resort with ID={}", resortId);
		resortService.deleteResortById(resortId);

		return Map.of("message", "Deletion of resort with ID=" + resortId + " was successful.");
	}

}
