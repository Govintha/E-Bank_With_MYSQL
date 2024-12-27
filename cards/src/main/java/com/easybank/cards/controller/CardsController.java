package com.easybank.cards.controller;


import com.easybank.cards.constants.CardsConstants;
import com.easybank.cards.dto.CardsContactInfoDto;
import com.easybank.cards.dto.CardsDTO;
import com.easybank.cards.dto.ResponseDTO;
import com.easybank.cards.service.ICardsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/cards", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CardsController {

     private final ICardsService cardsService;
    public CardsController(ICardsService iCardsService) {
        this.cardsService = iCardsService;
    }

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private CardsContactInfoDto cardsContactInfoDto;


    @PostMapping("/create")
     public ResponseEntity<ResponseDTO> createCard(@Valid @RequestParam
                                                       @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                       String mobileNumber){
        cardsService.createCard(mobileNumber);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(CardsConstants.STATUS_201,CardsConstants.MESSAGE_201));

    }

    @GetMapping("/fetch")
    public ResponseEntity<CardsDTO> getCardsDetails(@RequestParam
                                                        @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                    String mobileNumber){

      CardsDTO card=cardsService.getCardsDetails(mobileNumber);

      return ResponseEntity
              .status(HttpStatus.OK)
              .body(card);

    }
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateCardsDetails(@Valid @RequestBody CardsDTO cardsDTO){

        boolean isUpdated=cardsService.updateCardsDetails(cardsDTO);

        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseDTO(CardsConstants.MESSAGE_200,CardsConstants.MESSAGE_200));
        }else{

            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(CardsConstants.STATUS_417,CardsConstants.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteCardDetails(@RequestParam
                                                         @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                         String mobileNumber) {
        boolean isDelete=cardsService.deleteCard(mobileNumber);

        if(isDelete){

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseDTO(CardsConstants.MESSAGE_200,CardsConstants.MESSAGE_200));

          }else{

            return  ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(CardsConstants.STATUS_417,CardsConstants.MESSAGE_417_DELETE));
        }
    }

    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }

    @GetMapping("/contact-info")
    public ResponseEntity<CardsContactInfoDto> getContactInfo() {
        System.out.println("inside");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardsContactInfoDto);
    }



}
