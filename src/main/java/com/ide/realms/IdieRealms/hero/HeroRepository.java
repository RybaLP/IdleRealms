package com.ide.realms.IdieRealms.hero;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HeroRepository extends JpaRepository<Hero, Long> {

    @Query("SELECT h.level FROM Hero h WHERE h.id = :heroId")
    Optional<Integer> findLevelById(Long heroId);
}