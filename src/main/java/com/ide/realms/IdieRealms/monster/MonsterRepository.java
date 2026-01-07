package com.ide.realms.IdieRealms.monster;

import com.ide.realms.IdieRealms.shared.MonsterType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonsterRepository extends JpaRepository<Monster, Long> {
    List<Monster> findByMonsterType(MonsterType monsterType);
}