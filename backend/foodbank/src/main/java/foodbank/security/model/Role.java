package foodbank.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="app_role")
public class Role {
	
	public static final Long ADMIN_USER = 1L;
	public static final Long VOLUNTEER = 2L;
	public static final Long BENEFICIARY = 3L;
	
	private static final String BENEFICIARY_DESCRIPTION = "Beneficiary, mainly involved in request function";
	private static final String ADMIN_DESCRIPTION = "System administrator, allowed to use all functions";
	private static final String VOLUNTEER_DESCRIPTION = "Volunteers, mainly involved in stocktaking and packing functions";
	
	private static final String ADMIN_ROLE = "ADMIN_USER";
	private static final String BENEFICIARY_ROLE = "BENEFICIARY";
	private static final String VOLUNTEER_ROLE = "VOLUNTEER";
	
	protected Role() {}
	
	public Role(final long id) {
		this.id = id;
		if(id == ADMIN_USER) {
			this.roleName = ADMIN_ROLE;
			this.description = ADMIN_DESCRIPTION;
		} else if (id == VOLUNTEER) {
			this.roleName = VOLUNTEER_ROLE;
			this.description = VOLUNTEER_DESCRIPTION;
		} else if (id == BENEFICIARY) {
			this.roleName = BENEFICIARY_ROLE;
			this.description = BENEFICIARY_DESCRIPTION;
		}
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "role_name")
	private String roleName;
	
	@Column(name = "description")
	private String description;
	
	/*
	@OneToMany(mappedBy = "roles",
			cascade = CascadeType.ALL,
			fetch = FetchType.EAGER,
			targetEntity = User.class)
	private List<User> users;
	*/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
