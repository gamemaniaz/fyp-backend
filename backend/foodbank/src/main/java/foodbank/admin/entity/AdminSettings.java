package foodbank.admin.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
public class AdminSettings {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "settings_gen")
	@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "settings_gen", sequenceName = "settings_sequence")
	private Long id;
	
	@Column(name = "window_status")
	@Type(type = "boolean")
	private Boolean windowStatus;
	
	@Temporal(TemporalType.DATE)
	private Date windowStartDateTime;
	
	@Temporal(TemporalType.DATE)
	private Date windowsEndDateTime;
	
	private Double decayRate;
	private Double multiplierRate;
	private String dailyPassword;
	
	@Temporal(TemporalType.DATE)
	private Date lastStartDate;
	
	@Temporal(TemporalType.DATE)
	private Date lastEndDate;
	
	protected AdminSettings() {}

	public AdminSettings(Boolean windowStatus, Date windowStartDateTime, Date windowsEndDateTime, Double decayRate,
			Double multiplierRate, String dailyPassword) {
		this.windowStatus = windowStatus;
		this.windowStartDateTime = windowStartDateTime;
		this.windowsEndDateTime = windowsEndDateTime;
		this.decayRate = decayRate;
		this.multiplierRate = multiplierRate;
		this.dailyPassword = dailyPassword;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getWindowStatus() {
		return windowStatus;
	}

	public void setWindowStatus(Boolean windowStatus) {
		this.windowStatus = windowStatus;
	}

	public Date getWindowStartDateTime() {
		return windowStartDateTime;
	}

	public void setWindowStartDateTime(Date windowStartDateTime) {
		this.windowStartDateTime = windowStartDateTime;
	}

	public Date getWindowsEndDateTime() {
		return windowsEndDateTime;
	}

	public void setWindowsEndDateTime(Date windowsEndDateTime) {
		this.windowsEndDateTime = windowsEndDateTime;
	}

	public Double getDecayRate() {
		return decayRate;
	}

	public void setDecayRate(Double decayRate) {
		this.decayRate = decayRate;
	}

	public Double getMultiplierRate() {
		return multiplierRate;
	}

	public void setMultiplierRate(Double multiplierRate) {
		this.multiplierRate = multiplierRate;
	}

	public String getDailyPassword() {
		return dailyPassword;
	}

	public void setDailyPassword(String dailyPassword) {
		this.dailyPassword = dailyPassword;
	}

	public Date getLastStartDate() {
		return lastStartDate;
	}

	public void setLastStartDate(Date lastStartDate) {
		this.lastStartDate = lastStartDate;
	}

	public Date getLastEndDate() {
		return lastEndDate;
	}

	public void setLastEndDate(Date lastEndDate) {
		this.lastEndDate = lastEndDate;
	}
	
}
