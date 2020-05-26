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
import org.tat.fni.api.common.Name;
import org.tat.fni.api.common.ResidentAddress;
import org.tat.fni.api.common.emumdata.IdType;
import org.tat.fni.api.common.emumdata.ProposalType;
import org.tat.fni.api.domain.Agent;
import org.tat.fni.api.domain.Branch;
import org.tat.fni.api.domain.Customer;
import org.tat.fni.api.domain.MedicalProposal;
import org.tat.fni.api.domain.MedicalProposalInsuredPerson;
import org.tat.fni.api.domain.MedicalProposalInsuredPersonBeneficiaries;
import org.tat.fni.api.domain.Organization;
import org.tat.fni.api.domain.PaymentType;
import org.tat.fni.api.domain.Product;
import org.tat.fni.api.domain.RelationShip;
import org.tat.fni.api.domain.SalesPoints;
import org.tat.fni.api.domain.Township;
import org.tat.fni.api.domain.repository.CustomerRepository;
import org.tat.fni.api.domain.repository.LifeProposalRepository;
import org.tat.fni.api.dto.microHealthDTO.MicroHealthDTO;
import org.tat.fni.api.dto.microHealthDTO.MicroHealthProposalInsuredPersonBeneficiariesDTO;
import org.tat.fni.api.dto.microHealthDTO.MicroHealthProposalInsuredPersonDTO;
import org.tat.fni.api.exception.DAOException;
import org.tat.fni.api.exception.SystemException;

@Service
public class MicroHealthProposalService {


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
  private SalePointService salePointService;

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



  @Value("${microHealthProductId}")
  private String microHealthProductId;

  @Transactional(propagation = Propagation.REQUIRED)
  public List<MedicalProposal> createMicroHealthDtoToProposal(
      MicroHealthDTO microHealthInsuranceDTO) {
    try {
      // convert MicroHealthProposalDTO to lifeproposal
      List<MedicalProposal> microHealthProposalList =
          convertMicroHealthProposalDTOToProposal(microHealthInsuranceDTO);
      return microHealthProposalList;
    } catch (Exception e) {
      logger.error("JOEERROR:" + e.getMessage(), e);
      throw e;
    }
  }


  public List<MedicalProposal> convertMicroHealthProposalDTOToProposal(
      MicroHealthDTO microHealthInsuranceDTO) {
    List<MedicalProposal> medicalProposalList = new ArrayList<>();
    try {
      Optional<Branch> branchOptional =
          branchService.findById(microHealthInsuranceDTO.getBranchId());
      Optional<Organization> organizationOptional =
          organizationService.findById(microHealthInsuranceDTO.getOrganizationId());
      Optional<Customer> customerOptional =
          customerService.findById(microHealthInsuranceDTO.getCustomerId());
      Optional<PaymentType> paymentTypeOptional =
          paymentTypeService.findById(microHealthInsuranceDTO.getPaymentTypeId());
      Optional<Agent> agentOptional = agentService.findById(microHealthInsuranceDTO.getAgentId());
      Optional<SalesPoints> salesPointsOptional =
          salePointService.findById(microHealthInsuranceDTO.getSalesPointsId());

      microHealthInsuranceDTO.getMicrohealthproposalInsuredPersonList().forEach(insuredPerson -> {
        MedicalProposal medicalProposal = new MedicalProposal();

        medicalProposal.getMedicalProposalInsuredPersonList()
            .add(createInsuredPersonForMicroHealth(insuredPerson));
        medicalProposal.setComplete(true);
        medicalProposal.setProposalType(ProposalType.UNDERWRITING);
        medicalProposal.setSubmittedDate(microHealthInsuranceDTO.getSubmittedDate());


        if (organizationOptional.isPresent()) {
          medicalProposal.setOrganization(organizationOptional.get());
        }

        if (agentOptional.isPresent()) {
          medicalProposal.setAgent(agentOptional.get());
        }

        if (paymentTypeOptional.isPresent()) {
          medicalProposal.setPaymentType(paymentTypeOptional.get());
        }


        String proposalNo = customIdRepo.getNextId("HEALTH_PROPOSAL_NO", null);
        medicalProposal.setStartDate(microHealthInsuranceDTO.getStartDate());
        medicalProposal.setEndDate(microHealthInsuranceDTO.getEndDate());
        medicalProposal.setProposalNo(proposalNo);
        medicalProposalList.add(medicalProposal);
      });
    } catch (DAOException e) {
      throw new SystemException(e.getErrorCode(), e.getMessage());
    }
    return medicalProposalList;
  }


  private MedicalProposalInsuredPerson createInsuredPersonForMicroHealth(
      MicroHealthProposalInsuredPersonDTO dto) {
    try {
      Optional<Product> productOptional = productService.findById(microHealthProductId);
      Optional<Customer> customerOptional = customerService.findById(dto.getCustomerID());
      Optional<RelationShip> relationShipOptional =
          relationshipService.findById(dto.getRelationshipId());

      MedicalProposalInsuredPerson insuredPerson = new MedicalProposalInsuredPerson();

      insuredPerson.setProduct(productOptional.get());
      insuredPerson.setPremium(dto.getPremium());
      insuredPerson.setUnit(dto.getUnit());
      insuredPerson.setNeedMedicalCheckup(dto.isNeedMedicalCheckup());
      insuredPerson.setCustomer(customerOptional.get());


      String insPersonCodeNo = customIdRepo.getNextId("HEALTH_INSUPERSON_CODE_NO", null);
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

  /*
   * private Customer createNewCustomer(MedicalProposalInsuredPerson dto) { Customer customer = new
   * Customer(); try { customer.setInitialId(dto.getInitialId());
   * customer.setFatherName(dto.getFatherName()); customer.setIdNo(dto.getIdNo());
   * customer.setDateOfBirth(dto.getDateOfBirth()); customer.setGender(dto.getGender());
   * customer.setIdType(dto.getIdType()); customer.setResidentAddress(dto.getResidentAddress());
   * customer.setName(dto.getName()); customer.setOccupation(dto.getOccupation());
   * customer.setRecorder(dto.getRecorder()); customer = customerRepo.save(customer); } catch
   * (Exception e) { e.printStackTrace(); } return customer; }
   */


  private MedicalProposalInsuredPersonBeneficiaries createInsuredPersonBeneficiareis(
      MicroHealthProposalInsuredPersonBeneficiariesDTO dto) {
    try {
      Optional<Township> townshipOptional = townShipService.findById(dto.getTownshipId());
      Optional<RelationShip> relationshipOptional =
          relationshipService.findById(dto.getRelationshipId());
      ResidentAddress residentAddress = new ResidentAddress();
      residentAddress.setResidentAddress(dto.getResidentAddress());
      Name name = new Name();
      name.setFirstName(dto.getFirstName());
      name.setMiddleName(dto.getMiddleName());
      name.setLastName(dto.getLastName());

      MedicalProposalInsuredPersonBeneficiaries beneficiary =
          new MedicalProposalInsuredPersonBeneficiaries();
      beneficiary.setInitialId(dto.getInitialId());
      beneficiary.setPercentage(dto.getPercentage());
      beneficiary.setIdType(IdType.valueOf(dto.getIdType()));
      beneficiary.setIdNo(dto.getIdNo());
      beneficiary.setResidentAddress(residentAddress);
      beneficiary.setName(name);
      if (relationshipOptional.isPresent()) {
        beneficiary.setRelationship(relationshipOptional.get());
      }
      String beneficiaryNo = customIdRepo.getNextId("HEALTH_BENEFICIARY_NO", null);
      beneficiary.setBeneficiaryNo(beneficiaryNo);
      return beneficiary;
    } catch (DAOException e) {
      throw new SystemException(e.getErrorCode(), e.getMessage());
    }
  }


}
