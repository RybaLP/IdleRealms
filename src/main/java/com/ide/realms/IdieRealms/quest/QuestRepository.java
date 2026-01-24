package com.ide.realms.IdieRealms.quest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestRepository extends JpaRepository <Quest, Long>{
    List<Quest> findByHeroId (Long heroId);

    @Modifying
    void deleteByHeroId (Long heroId);
}