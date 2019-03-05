package foodbank.beneficiary.service;

import java.util.List;

import foodbank.beneficiary.dto.BeneficiaryDTO;
import foodbank.beneficiary.entity.Beneficiary;

public interface BeneficiaryService {
	
	List<BeneficiaryDTO> getAllBeneficiaries();
	
	BeneficiaryDTO getBeneficiaryDetails(final String beneficiary);
	
	void createBeneficiary(final BeneficiaryDTO beneficiary);

	void updateBeneficiary(final BeneficiaryDTO beneficiary);
	
}
