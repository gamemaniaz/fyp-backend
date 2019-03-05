package foodbank.admin.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdminSettingsDTO {
	
	@NotNull
	@JsonProperty("windowStartDate")
	private String startDate;
	
	@NotNull
	@JsonProperty("windowEndDate")
	private String endDate;
	
	@NotNull
	@JsonProperty("multiplierRate")
	private Double multiplierRate;
	
	@NotNull
	@JsonProperty("decayRate")
	private Double decayRate;

	protected AdminSettingsDTO() {}
	
	public AdminSettingsDTO(String startDate, String endDate, Double multiplierRate, Double decayRate) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.multiplierRate = multiplierRate;
		this.decayRate = decayRate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Double getMultiplierRate() {
		return multiplierRate;
	}

	public void setMultiplierRate(Double multiplierRate) {
		this.multiplierRate = multiplierRate;
	}

	public Double getDecayRate() {
		return decayRate;
	}

	public void setDecayRate(Double decayRate) {
		this.decayRate = decayRate;
	}

}
