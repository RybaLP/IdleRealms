package com.ide.realms.IdieRealms.monster;

import com.ide.realms.IdieRealms.shared.MonsterType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MonsterRepository extends JpaRepository<Monster, Long> {
    List<Monster> findByMonsterType(MonsterType monsterType);

    @Query(value = "SELECT * FROM monster WHERE monster_type = 'TAVERN' ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<Monster> findRandomMonster ();

}