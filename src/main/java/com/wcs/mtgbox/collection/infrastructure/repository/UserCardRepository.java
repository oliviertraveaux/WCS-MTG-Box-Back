package com.wcs.mtgbox.collection.infrastructure.repository;

import com.wcs.mtgbox.collection.domain.entity.Card;
import com.wcs.mtgbox.collection.domain.entity.UserCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCardRepository extends JpaRepository<UserCard, Long> {
    List<UserCard> findAllByUserId(Long userId);
    List<UserCard> findAllByCardId(Long cardId);
    void deleteById(Long userCardId);
    void flush();
}
