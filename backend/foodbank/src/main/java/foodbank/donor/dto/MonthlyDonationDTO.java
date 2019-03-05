package foodbank.donor.dto;

import java.util.Date;
import java.util.List;

public class MonthlyDonationDTO {
	
	public MonthlyDonationDTO(String monthString, List<DonationDetailsDTO> donations) {
		this.monthString = monthString;
		this.donations = donations;
	}

	private String monthString;
	
	private List<DonationDetailsDTO> donations;

	public String getMonthString() {
		return monthString;
	}

	public void setMonthString(String monthString) {
		this.monthString = monthString;
	}

	public List<DonationDetailsDTO> getDonations() {
		return donations;
	}

	public void setDonations(List<DonationDetailsDTO> donations) {
		this.donations = donations;
	}

}
