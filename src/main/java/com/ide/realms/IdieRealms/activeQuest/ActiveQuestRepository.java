package com.ide.realms.IdieRealms.activeQuest;

import com.ide.realms.IdieRealms.hero.Hero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActiveQuestRepository extends JpaRepository<ActiveQuest,Long>{
    boolean existsByHero (Hero hero);
    Optional<ActiveQuest> findByHeroId (Long heroId);
}