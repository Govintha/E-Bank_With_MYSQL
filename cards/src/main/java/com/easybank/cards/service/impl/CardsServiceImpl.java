package com.easybank.cards.service.impl;

import com.easybank.cards.dto.CardsDTO;
import com.easybank.cards.entity.Cards;
import com.easybank.cards.exception.CardAlreadyExistException;
import com.easybank.cards.exception.ResourseNotFoundException;
import com.easybank.cards.mapper.CardsMapper;
import com.easybank.cards.repository.ICardsRepository;
import com.easybank.cards.service.ICardsService;
import com.easybank.cards.constants.CardsConstants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {

    ICardsRepository cardsRepository;

    @Override
    public void createCard(String mobileNumber) {



       Optional<Cards> cards=cardsRepository.findByMobileNumber(mobileNumber);

       if(cards.isPresent()){
           throw  new CardAlreadyExistException("This Mobile Number Card is already Exist");
       }

       cardsRepository.save(createNewCard(mobileNumber));

    }

    @Override
    public CardsDTO getCardsDetails(String mobileNumber) {

        Cards card=cardsRepository.findByMobileNumber(mobileNumber)
                   .orElseThrow(()->new ResourseNotFoundException("Card Not Found for this"+mobileNumber+" number"));

       return CardsMapper.mapToCardsDto(card,new CardsDTO());
    }

    @Override
    public boolean updateCardsDetails(CardsDTO cardsDTO) {
         Cards cards=cardsRepository.findByCardNumber(cardsDTO.getCardNumber())
                 .orElseThrow(()->new ResourseNotFoundException("Cards not Found"));
         CardsMapper.mapToCards(cardsDTO,cards);
         cardsRepository.save(cards);
         return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {

        Cards cards = cardsRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourseNotFoundException("Card not found given mobile number"));
        cardsRepository.deleteById(cards.getId());
        return true;
    }
    private Cards createNewCard(String mobileNumber) {

        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(String.valueOf(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCard;
    }
}
