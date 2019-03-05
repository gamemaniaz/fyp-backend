package foodbank.admin.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WindowDataDTO {
	
	@NotNull
	@JsonProperty("windowStatus")
	private Boolean windowStatus;
	
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
	
	@NotNull
	@JsonProperty("dailyPassword")
	private String dailyPassword;
	
	@NotNull
	@JsonProperty("uniqueBeneficiaryCount")
	private Integer uniqueBeneficiaryCount;

	protected WindowDataDTO() {}
	
	public WindowDataDTO(Boolean windowStatus, String startDate, String endDate, Double multiplierRate,
			Double decayRate, String dailyPassword, Integer uniqueBeneficiaryCount) {
		this.windowStatus = windowStatus;
		this.startDate = startDate;
		this.endDate = endDate;
		this.multiplierRate = multiplierRate;
		this.decayRate = decayRate;
		this.dailyPassword = dailyPassword;
		this.uniqueBeneficiaryCount = uniqueBeneficiaryCount;
	}

}
