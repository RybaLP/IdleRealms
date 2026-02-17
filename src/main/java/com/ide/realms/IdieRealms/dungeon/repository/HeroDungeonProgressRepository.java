package com.ide.realms.IdieRealms.dungeon.repository;

import com.ide.realms.IdieRealms.dungeon.HeroDungeonProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HeroDungeonProgressRepository extends JpaRepository<HeroDungeonProgress,Long>{
    Optional<HeroDungeonProgress> findByHeroIdAndDungeonId(Long heroId, Long dungeonId);
}