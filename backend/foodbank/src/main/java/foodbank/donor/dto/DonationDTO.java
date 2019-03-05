package foodbank.donor.dto;

import java.util.List;

public class DonationDTO {
	
	public DonationDTO(List<AnnualDonationDTO> yearlyDonations) {
		this.yearlyDonations = yearlyDonations;
	}

	private List<AnnualDonationDTO> yearlyDonations;

}
