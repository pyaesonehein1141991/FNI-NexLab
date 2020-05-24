package org.tat.fni.api.domain.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tat.fni.api.common.CommonCreateAndUpateMarks;
import org.tat.fni.api.common.emumdata.ProposalType;
import org.tat.fni.api.domain.Agent;
import org.tat.fni.api.domain.Branch;
import org.tat.fni.api.domain.Organization;
import org.tat.fni.api.domain.PaymentType;
import org.tat.fni.api.domain.SalesPoints;
import org.tat.fni.api.domain.lifeproposal.LifeProposal;
//import org.tat.fni.api.domain.repository.FarmerRepository;
import org.tat.fni.api.dto.farmerDTO.FarmerProposalDTO;
import org.tat.fni.api.exception.DAOException;
import org.tat.fni.api.exception.SystemException;


@Service
public class FarmerLifeProposalService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PaymentTypeService paymentTypeService;
	
	@Autowired
	private AgentService agentService;
	
	@Autowired
	private BranchService branchService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private SalePointService salePointService;
	
//	@Autowired
//	private ICustomIdGenerator customIdRepo;
//	
//	@Autowired
//	private FarmerRepository farmerRepository;
	
	public List<LifeProposal> farmerProposal(
			FarmerProposalDTO farmerProposalDTO){
		try {
			
//			FarmerProposal farmerProposal = creatFarmerProposal(farmerProposalDTO);
//			farmerProposal = farmerRepository.save(farmerProposal);
			
			List<LifeProposal> farmerProposalList = 
					convertFarmerProposalDTOToProposal(farmerProposalDTO);
			
			return farmerProposalList;
			
		}catch (DAOException e) {
	    	
		 logger.error("JOEERROR:" + e.getMessage(), e);
		 throw new SystemException(e.getErrorCode(), e.getMessage());
		}
	}
	
//	private FarmerProposal creatFarmerProposal(FarmerProposalDTO farmerProposalDTO) {
//	    Optional<Branch> branchOptional = branchService.findById(farmerProposalDTO.getBranchId());
//	    Optional<PaymentType> paymentTypeOptional =
//	        paymentTypeService.findById(farmerProposalDTO.getPaymentTypeId());
//	    Optional<Agent> agentOptional = agentService.findById(farmerProposalDTO.getAgentId());
//	    Optional<Organization> organizationOptional =
//	        organizationService.findById(farmerProposalDTO.getOrganizationId());
//	    Optional<SalesPoints> salePointOptional =
//	        salePointService.findById(farmerProposalDTO.getSalesPointsId());
//	    FarmerProposal farmerProposal = new FarmerProposal();
//	    try {
//	      farmerProposal.setSubmittedDate(farmerProposalDTO.getSubmittedDate());
//	      farmerProposal.setProposalType(ProposalType.UNDERWRITING);
//	      farmerProposal.setEndDate(farmerProposalDTO.getEndDate());
//	      farmerProposal.setPaymentComplete(true);
//	      farmerProposal.setProcessComplete(true);
//	      double totalPremium = (farmerProposal.getTotalSI() / 100) * 1;
//	      farmerProposal.setPremium(totalPremium);
//	      if (branchOptional.isPresent()) {
//	        farmerProposal.setBranch(branchOptional.get());
//	      }
//
//	      if (agentOptional.isPresent()) {
//	        farmerProposal.setAgent(agentOptional.get());
//	      }
//	      farmerProposal.setPaymentType(paymentTypeOptional.get());
//
//	      if (organizationOptional.isPresent()) {
//	        farmerProposal.setOrganization(organizationOptional.get());
//	      }
//	      if (salePointOptional.isPresent()) {
//	        farmerProposal.setSalePoint(salePointOptional.get());
//	      }
//	      CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
//	      recorder.setCreatedDate(new Date());
//	      farmerProposal.setCommonCreateAndUpateMarks(recorder);
//	      String proposalNo = customIdRepo.getNextId("GROUPFARMER_LIFE_PROPOSAL_NO", null);
//	      farmerProposal.setProposalNo(proposalNo);
//
//	    } catch (DAOException e) {
//	    	logger.error("JOEERROR:" + e.getMessage());
//	      throw new SystemException(e.getErrorCode(), e.getMessage());
//	    } 
//	    return farmerProposal;
//	  }
	
	private List<LifeProposal> convertFarmerProposalDTOToProposal(
			FarmerProposalDTO farmerProposalDTO) {

		    Optional<Branch> branchOptional = branchService.findById(farmerProposalDTO.getBranchId());
		    Optional<Organization> organizationOptional =
		        organizationService.findById(farmerProposalDTO.getOrganizationId());
		    Optional<PaymentType> paymentTypeOptional =
		        paymentTypeService.findById(farmerProposalDTO.getPaymentTypeId());
		    Optional<Agent> agentOptional = agentService.findById(farmerProposalDTO.getAgentId());
		    Optional<SalesPoints> salePointOptional =
		        salePointService.findById(farmerProposalDTO.getSalesPointsId());
		    List<LifeProposal> lifeProposalList = new ArrayList<>();
		    try {
		    	farmerProposalDTO.getProposalInsuredPersonList().forEach(insuredPerson -> {
		        LifeProposal lifeProposal = new LifeProposal();
		        
		        lifeProposal.setComplete(true);

		        lifeProposal.setProposalType(ProposalType.UNDERWRITING);
		        lifeProposal.setSubmittedDate(farmerProposalDTO.getSubmittedDate());
		        if (organizationOptional.isPresent()) {
		          lifeProposal.setOrganization(organizationOptional.get());
		        }
		        if (paymentTypeOptional.isPresent()) {
		          lifeProposal.setPaymentType(paymentTypeOptional.get());
		        }

		        if (agentOptional.isPresent()) {

		          lifeProposal.setAgent(agentOptional.get());
		        }
		   
		        lifeProposal.setSalesPoints(salePointOptional.get());
		        lifeProposalList.add(lifeProposal);

		      });

		    } catch (DAOException e) {
		      throw new SystemException(e.getErrorCode(), e.getMessage());
		    }

		    return lifeProposalList;
		  }

}
