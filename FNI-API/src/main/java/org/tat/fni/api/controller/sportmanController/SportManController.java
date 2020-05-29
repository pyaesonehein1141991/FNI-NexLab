package org.tat.fni.api.controller.sportmanController;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.fni.api.domain.lifeproposal.LifeProposal;
import org.tat.fni.api.domain.services.SportManProposalService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.sportManDTO.SportManDTO;
import org.tat.fni.api.dto.sportManDTO.SportManReponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/sportman")
@Api(tags = "SportMan Proposal")
public class SportManController {

  @Autowired
  private SportManProposalService lifeProposalService;

  @Autowired
  private ModelMapper mapper;

  @PostMapping("/submitproposal")
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Something went wrong"),
      @ApiResponse(code = 403, message = "Access denied"),
      @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
  @ApiOperation(value = "${SportManController.submitproposal}")
  public ResponseDTO<Object> submitproposal(
      @ApiParam("Submit SportMan Proposal") @Valid @RequestBody SportManDTO sportManDTO) {

    List<LifeProposal> proposallist = new ArrayList<>();

    SportManDTO sportmandto = mapper.map(sportManDTO, SportManDTO.class);

    // create PersonalAccident proposal
    proposallist = lifeProposalService.createSportManDTOToProposal(sportmandto);

    // create response object
    List<SportManReponseDTO> responseList = new ArrayList<>();

    proposallist.forEach(proposal -> {
      SportManReponseDTO dto = SportManReponseDTO.builder().proposalID(proposal.getId())
          .proposalNo(proposal.getProposalNo()).proposedPremium(proposal.getPremium()).build();
      responseList.add(dto);
    });

    ResponseDTO<Object> responseDTO =
        ResponseDTO.builder().status("Success!").responseBody(responseList).build();

    return responseDTO;
  }

}


