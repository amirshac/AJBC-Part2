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
@ToString(exclude = {"picture"})

// hibernate / javax persistance part
@Entity
@Table(name="categories")
public class Category {

	@Id // tells hibernate its a primary key
	@GeneratedValue(strategy=GenerationType.IDENTITY) // tells hibernate its an identity field
	private int categoryId;
	@Column(name="categoryName")
	private String catName;
	private String description;
	private byte[] picture;
}
