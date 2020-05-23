package org.tat.fni.api.domain.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tat.fni.api.common.Gender;
import org.tat.fni.api.common.Name;
import org.tat.fni.api.common.ResidentAddress;
import org.tat.fni.api.common.emumdata.IdType;
import org.tat.fni.api.common.emumdata.ProposalType;
import org.tat.fni.api.domain.Agent;
import org.tat.fni.api.domain.Branch;
import org.tat.fni.api.domain.Customer;
import org.tat.fni.api.domain.DateUtils;
import org.tat.fni.api.domain.InsuredPersonBeneficiaries;
import org.tat.fni.api.domain.Occupation;
import org.tat.fni.api.domain.Organization;
import org.tat.fni.api.domain.PaymentType;
import org.tat.fni.api.domain.Product;
import org.tat.fni.api.domain.ProposalInsuredPerson;
import org.tat.fni.api.domain.RelationShip;
import org.tat.fni.api.domain.Township;
import org.tat.fni.api.domain.lifeproposal.LifeProposal;
import org.tat.fni.api.domain.repository.CustomerRepository;
import org.tat.fni.api.domain.repository.LifeProposalRepository;
import org.tat.fni.api.dto.shortTermEndowmentLifeDTO.ShortTermEndowmentLifeDTO;
import org.tat.fni.api.dto.shortTermEndowmentLifeDTO.ShortTermProposalInsuredPersonBeneficiariesDTO;
import org.tat.fni.api.dto.shortTermEndowmentLifeDTO.ShortTermProposalInsuredPersonDTO;
import org.tat.fni.api.exception.DAOException;
import org.tat.fni.api.exception.SystemException;


@Service
public class ShortTermLifeProposalService {

  Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private LifeProposalRepository lifeProposalRepo;

  @Autowired
  private BranchService branchService;

  @Autowired
  private CustomerRepository customerRepo;

  @Autowired
  private CustomerService customerService;

  @Autowired
  private OrganizationService organizationService;

  @Autowired
  private PaymentTypeService paymentTypeService;

  @Autowired
  private AgentService agentService;


  @Autowired
  private ProductService productService;

  @Autowired
  private TownShipService townShipService;

  @Autowired
  private OccupationService occupationService;

  @Autowired
  private RelationshipService relationshipService;

  @Autowired
  private ICustomIdGenerator customIdRepo;

  @Value("${shorttermLifeProductId}")
  private String shorttermLifeProductId;



  @Transactional(propagation = Propagation.REQUIRED)
  public List<org.tat.fni.api.domain.lifeproposal.LifeProposal> createShortTermEndowmentLifeDtoToProposal(
      ShortTermEndowmentLifeDTO shortTermEndowmentLifeDto) {
    try {
      // convert shortTermEndowmentlifeProposalDTO to lifeproposal
      List<LifeProposal> shortTermEndowmentLifeProposalList =
          convertShortTermEndowmentLifeProposalDTOToProposal(shortTermEndowmentLifeDto);
      return shortTermEndowmentLifeProposalList;
    } catch (Exception e) {
      logger.error("JOEERROR:" + e.getMessage(), e);
      throw e;
    }
  }

  // ForshortTermEndowmentlifeDto to proposal
  public List<LifeProposal> convertShortTermEndowmentLifeProposalDTOToProposal(
      ShortTermEndowmentLifeDTO shortTermEndowmentLifeDto) {
    List<LifeProposal> lifeProposalList = new ArrayList<>();
    try {
      Optional<Branch> branchOptional =
          branchService.findById(shortTermEndowmentLifeDto.getBranchId());
      Optional<Organization> organizationOptional =
          organizationService.findById(shortTermEndowmentLifeDto.getOrganizationId());
      Optional<Customer> customerOptional =
          customerService.findById(shortTermEndowmentLifeDto.getCustomerId());
      Optional<PaymentType> paymentTypeOptional =
          paymentTypeService.findById(shortTermEndowmentLifeDto.getPaymentTypeId());
      Optional<Agent> agentOptional = agentService.findById(shortTermEndowmentLifeDto.getAgentId());


      shortTermEndowmentLifeDto.getProposalInsuredPersonList().forEach(insuredPerson -> {
        LifeProposal lifeProposal = new LifeProposal();

        lifeProposal.getProposalInsuredPersonList()
            .add(createInsuredPersonForShortTerm(insuredPerson));
        lifeProposal.setComplete(true);
        lifeProposal.setProposalType(ProposalType.UNDERWRITING);
        lifeProposal.setSubmittedDate(shortTermEndowmentLifeDto.getSubmittedDate());


        if (organizationOptional.isPresent()) {
          lifeProposal.setOrganization(organizationOptional.get());
        }

        if (agentOptional.isPresent()) {
          lifeProposal.setAgent(agentOptional.get());
        }

        if (paymentTypeOptional.isPresent()) {
          lifeProposal.setPaymentType(paymentTypeOptional.get());
        }

        String proposalNo = customIdRepo.getNextId("SHORT_ENDOWMENT_PROPOSAL_ID_GEN", null);
        lifeProposal.setProposalNo(proposalNo);
        lifeProposalList.add(lifeProposal);
      });
    } catch (DAOException e) {
      throw new SystemException(e.getErrorCode(), e.getMessage());
    }
    return lifeProposalList;
  }


  private ProposalInsuredPerson createInsuredPersonForShortTerm(
      ShortTermProposalInsuredPersonDTO dto) {
    try {
      Optional<Product> productOptional = productService.findById(shorttermLifeProductId);
      Optional<Township> townshipOptional = townShipService.findById(dto.getTownshipId());
      Optional<Occupation> occupationOptional = occupationService.findById(dto.getOccupationID());
      Optional<Customer> customerOptional = customerService.findById(dto.getCustomerID());

      ResidentAddress residentAddress = new ResidentAddress();
      residentAddress.setResidentAddress(dto.getResidentAddress());


      Name name = new Name();
      name.setFirstName(dto.getFirstName());
      name.setMiddleName(dto.getMiddleName());
      name.setLastName(dto.getLastName());

      ProposalInsuredPerson insuredPerson = new ProposalInsuredPerson();

      insuredPerson.setProduct(productOptional.get());
      insuredPerson.setInitialId(dto.getInitialId());
      insuredPerson.setProposedSumInsured(dto.getProposedSumInsured());
      insuredPerson.setProposedPremium(dto.getProposedPremium());
      insuredPerson.setApprovedSumInsured(dto.getApprovedSumInsured());
      insuredPerson.setBasicTermPremium(dto.getBasicTermPremium());
      insuredPerson.setIdType(IdType.valueOf(dto.getIdType()));
      insuredPerson.setIdNo(dto.getIdNo());
      insuredPerson.setFatherName(dto.getFatherName());
      insuredPerson.setDateOfBirth(dto.getDateOfBirth());
      insuredPerson.setAge(DateUtils.getAgeForNextYear(dto.getDateOfBirth()));
      insuredPerson.setGender(Gender.valueOf(dto.getGender()));
      insuredPerson.setResidentAddress(residentAddress);
      insuredPerson.setName(name);
      if (occupationOptional.isPresent()) {
        insuredPerson.setOccupation(occupationOptional.get());
      }
      if (customerOptional.isPresent()) {
        insuredPerson.setCustomer(customerOptional.get());
      } else {
        insuredPerson.setCustomer(createNewCustomer(insuredPerson));

      }

      String insPersonCodeNo = customIdRepo.getNextId("LIFE_INSUREDPERSON_CODENO_ID_GEN", null);
      insuredPerson.setInsPersonCodeNo(insPersonCodeNo);
      dto.getInsuredPersonBeneficiariesList().forEach(beneficiary -> {
        insuredPerson.getInsuredPersonBeneficiariesList()
            .add(createInsuredPersonBeneficiareis(beneficiary));
      });
      return insuredPerson;
    } catch (DAOException e) {
      throw new SystemException(e.getErrorCode(), e.getMessage());
    }
  }

  private Customer createNewCustomer(ProposalInsuredPerson dto) {
    Customer customer = new Customer();
    try {
      customer.setInitialId(dto.getInitialId());
      customer.setFatherName(dto.getFatherName());
      customer.setIdNo(dto.getIdNo());
      customer.setDateOfBirth(dto.getDateOfBirth());
      customer.setGender(dto.getGender());
      customer.setIdType(dto.getIdType());
      customer.setResidentAddress(dto.getResidentAddress());
      customer.setName(dto.getName());
      customer.setOccupation(dto.getOccupation());
      customer.setRecorder(dto.getRecorder());
      customer = customerRepo.save(customer);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return customer;
  }


  private InsuredPersonBeneficiaries createInsuredPersonBeneficiareis(
      ShortTermProposalInsuredPersonBeneficiariesDTO dto) {
    try {
      Optional<Township> townshipOptional = townShipService.findById(dto.getTownshipId());
      Optional<RelationShip> relationshipOptional =
          relationshipService.findById(dto.getRelationshipID());
      ResidentAddress residentAddress = new ResidentAddress();
      residentAddress.setResidentAddress(dto.getResidentAddress());
      Name name = new Name();
      name.setFirstName(dto.getFirstName());
      name.setMiddleName(dto.getMiddleName());
      name.setLastName(dto.getLastName());

      InsuredPersonBeneficiaries beneficiary = new InsuredPersonBeneficiaries();
      beneficiary.setInitialId(dto.getInitialId());
      beneficiary.setDateOfBirth(dto.getDob());
      beneficiary.setPercentage(dto.getPercentage());
      beneficiary.setIdType(IdType.valueOf(dto.getIdType()));
      beneficiary.setIdNo(dto.getIdNo());
      beneficiary.setGender(Gender.valueOf(dto.getGender()));
      beneficiary.setResidentAddress(residentAddress);
      beneficiary.setName(name);
      if (relationshipOptional.isPresent()) {
        beneficiary.setRelationship(relationshipOptional.get());
      }
      String beneficiaryNo = customIdRepo.getNextId("LIFE_BENEFICIARY_ID_GEN", null);
      beneficiary.setBeneficiaryNo(beneficiaryNo);
      return beneficiary;
    } catch (DAOException e) {
      throw new SystemException(e.getErrorCode(), e.getMessage());
    }
  }

}