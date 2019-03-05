package foodbank.beneficiary.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foodbank.beneficiary.dto.BeneficiaryDTO;
import foodbank.beneficiary.entity.Beneficiary;
import foodbank.beneficiary.repository.BeneficiaryRepository;
import foodbank.beneficiary.service.BeneficiaryService;
import foodbank.security.model.Role;
import foodbank.security.model.repository.RoleRepository;
import foodbank.user.entity.User;
import foodbank.util.EntityManager;
import foodbank.util.EntityManager.DTOKey;
import foodbank.util.MessageConstants.ErrorMessages;
import foodbank.util.exceptions.InvalidBeneficiaryException;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {

	@Autowired
	private BeneficiaryRepository beneficiaryRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	public List<BeneficiaryDTO> getAllBeneficiaries() {
		// TODO Auto-generated method stub
		List<Beneficiary> dbBeneficiaries = beneficiaryRepository.findAll();
		List<BeneficiaryDTO> beneficiaryDTOs = new ArrayList<BeneficiaryDTO>();
		for(Beneficiary dbBeneficiary : dbBeneficiaries) {
			beneficiaryDTOs.add((BeneficiaryDTO)EntityManager.convertToDTO(DTOKey.BeneficiaryDTO, dbBeneficiary));
		}
		return beneficiaryDTOs;
	}

	public BeneficiaryDTO getBeneficiaryDetails(String beneficiary) {
		// TODO Auto-generated method stub
		Beneficiary dbBeneficiary = beneficiaryRepository.findByUserUsername(beneficiary);
		if(dbBeneficiary == null) {
			throw new InvalidBeneficiaryException(ErrorMessages.NO_SUCH_BENEFICIARY);
		}
		return (BeneficiaryDTO)EntityManager.convertToDTO(DTOKey.BeneficiaryDTO, dbBeneficiary);
	}

	public void createBeneficiary(BeneficiaryDTO beneficiary) {
		// TODO Auto-generated method stub
		Beneficiary dbBeneficiary = beneficiaryRepository.findByUserUsername(beneficiary.getUsername());
		if(dbBeneficiary != null) {
			throw new InvalidBeneficiaryException(ErrorMessages.BENEFICIARY_ALREADY_EXISTS);
		}
		dbBeneficiary = EntityManager.transformBeneficiaryDTO(beneficiary);
		User dbUser = dbBeneficiary.getUser();
		dbUser.setPassword(BCrypt.hashpw(dbUser.getPassword(), BCrypt.gensalt()));
		dbUser.setRole(roleRepository.findOne(Role.BENEFICIARY));
		beneficiaryRepository.save(dbBeneficiary);
	}

	public void updateBeneficiary(BeneficiaryDTO beneficiary) {
		// TODO Auto-generated method stub
		Beneficiary dbBeneficiary = beneficiaryRepository.findByUserUsername(beneficiary.getUsername());
		if(dbBeneficiary == null) {
			throw new InvalidBeneficiaryException(ErrorMessages.NO_SUCH_BENEFICIARY);
		}
		beneficiary.setScore(dbBeneficiary.getScore());
		Beneficiary newBeneficiaryDetails = EntityManager.transformBeneficiaryDTO(beneficiary);
		User newUserDetails = newBeneficiaryDetails.getUser();
		Map<String, Object> parametersToUpdate = new HashMap<String, Object>();
		parametersToUpdate.put("name", newUserDetails.getName());
		parametersToUpdate.put("email", newUserDetails.getEmail());
		parametersToUpdate.put("numBeneficiaries", newBeneficiaryDetails.getNumBeneficiary());
		parametersToUpdate.put("address", newBeneficiaryDetails.getAddress());
		parametersToUpdate.put("contactPerson", newBeneficiaryDetails.getContactPerson());
		parametersToUpdate.put("contactNumber", newBeneficiaryDetails.getContactNumber());
		parametersToUpdate.put("memberType", newBeneficiaryDetails.getMemberType());
		parametersToUpdate.put("transportationStatus", newBeneficiaryDetails.getTransportationStatus());
		for(Entry<String, Object> entry : parametersToUpdate.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if(key.equals("numBeneficiaries") && EntityManager.checkForUpdateIntent(value)) {
				dbBeneficiary.setNumBeneficiary((Integer)value);
			} else if (key.equals("transportationStatus") && EntityManager.checkForUpdateIntent(value)) {
				dbBeneficiary.setTransportationStatus((Boolean)value);
			} else {
				String stringValue = String.valueOf(value);
				if(!stringValue.isEmpty()) {
					switch(key) {
						case("name"):
							dbBeneficiary.getUser().setName(stringValue);
							break;
						case("email"):
							dbBeneficiary.getUser().setEmail(stringValue);
							break;
						case("address"):
							dbBeneficiary.setAddress(stringValue);
							break;
						case("contactPerson"):
							dbBeneficiary.setContactPerson(stringValue);
							break;
						case("contactNumber"):
							dbBeneficiary.setContactNumber(stringValue);
							break;
						case("memberType"):
							dbBeneficiary.setMemberType(stringValue);
							break;
					}
				}
			}
		}
		beneficiaryRepository.save(dbBeneficiary);
	}
	
}
