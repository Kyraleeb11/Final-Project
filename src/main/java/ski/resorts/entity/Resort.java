package ski.resorts.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Resort {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long resortId;
	private String resortName;
	private String resortAddress;
	private String resortState;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude

	@ManyToMany(cascade = CascadeType.PERSIST)

	@JoinTable(name = "resort_customer", joinColumns = @JoinColumn(name = "resort_id"), inverseJoinColumns = @JoinColumn(name = "customer_id"))
	private Set<Customer> customers = new HashSet<>();

	@EqualsAndHashCode.Exclude
	@ToString.Exclude

	@OneToMany(mappedBy = "resort", cascade = CascadeType.ALL, orphanRemoval = true)

	private Set<Employee> employees = new HashSet<>();

}
