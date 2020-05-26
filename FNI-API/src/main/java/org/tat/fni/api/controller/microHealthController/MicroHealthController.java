package org.tat.fni.api.controller.microHealthController;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.fni.api.domain.MedicalProposal;
import org.tat.fni.api.domain.services.MicroHealthProposalService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.microHealthDTO.MicroHealthDTO;
import org.tat.fni.api.dto.microHealthDTO.MicroHealthReponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping("/microHealth")
@Api(tags = "MicroHealth Proposal")
public class MicroHealthController {

  @Autowired
  private MicroHealthProposalService medicalProposalService;

  @Autowired
  private ModelMapper mapper;

  @PostMapping("/submitproposal")
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Something went wrong"),
      @ApiResponse(code = 403, message = "Access denied"),
      @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
  @ApiOperation(value = "${MicroHealthController.submitproposal}")
  public ResponseDTO<Object> submitproposal(
      @ApiParam("Submit MicroHealth Proposal") @Valid @RequestBody MicroHealthDTO microHealthDTO) {
    List<MedicalProposal> proposallist = new ArrayList<>();
    MicroHealthDTO a = mapper.map(microHealthDTO, MicroHealthDTO.class);

    // create shortTermEndowmentlife proposal
    proposallist = medicalProposalService.createMicroHealthDtoToProposal(a);

    // create response object
    List<MicroHealthReponseDTO> responseList = new ArrayList<>();

    proposallist.forEach(proposal -> {
      MicroHealthReponseDTO dto = MicroHealthReponseDTO.builder().proposalID(proposal.getId())
          .proposalNo(proposal.getProposalNo()).proposedPremium(proposal.getTotalPremium()).build();
      responseList.add(dto);
    });

    ResponseDTO<Object> responseDTO =
        ResponseDTO.builder().status("Success!").responseBody(responseList).build();

    return responseDTO;
  }


}
