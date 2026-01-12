package com.ide.realms.IdieRealms.quest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestTemplateRepository extends JpaRepository<QuestTemplate, Long> {

    @Query(
            value = "SELECT * FROM quest_template ORDER BY RANDOM() LIMIT 1", nativeQuery = true
    )
    Optional<QuestTemplate> findRandomQuestTemplate();
}