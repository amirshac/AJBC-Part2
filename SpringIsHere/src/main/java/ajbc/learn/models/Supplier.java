package ajbc.learn.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor

// hibernate / javax persistance part
@Entity
@Table(name="suppliers")
public class Supplier {

	@Id // tells hibernate its a primary key
	@GeneratedValue(strategy=GenerationType.IDENTITY) // tells hibernate its an identity field
	private int supplierId;
	private String companyName;
	private String contactName;
	private String contactTitle;
	private String address;
	private String city;
	private String postalCode;
	private String region;
	private String country;
	private String phone;
	private String fax;
	private String homePage;
}
