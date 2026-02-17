package com.ide.realms.IdieRealms.dungeon.repository;

import com.ide.realms.IdieRealms.dungeon.DungeonFloor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DungeonFloorRepository extends JpaRepository<DungeonFloor,Long> {

    Optional<DungeonFloor> findByDungeonIdAndFloorNumber(Long dungeonId, int currentFloorNumber);
}