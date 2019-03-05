package foodbank.donor.dto;

import java.util.List;

public class AnnualDonationDTO {
	
	public AnnualDonationDTO(String yearString, List<MonthlyDonationDTO> monthlyDonations) {
		this.yearString = yearString;
		this.monthlyDonations = monthlyDonations;
	}

	private String yearString;
	
	private List<MonthlyDonationDTO> monthlyDonations;

}
