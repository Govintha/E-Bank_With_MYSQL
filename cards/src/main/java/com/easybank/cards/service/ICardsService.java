package com.easybank.cards.service;

import com.easybank.cards.dto.CardsDTO;

public interface ICardsService {

    void createCard(String mobileNumber);
    CardsDTO getCardsDetails(String mobileNumber);
    boolean updateCardsDetails(CardsDTO cardsDTO);
    boolean deleteCard(String mobileNumber);

}
