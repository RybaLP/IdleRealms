package com.ide.realms.IdieRealms.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemTemplateRepository extends JpaRepository <ItemTemplate, Long>{

    @Query(value = "SELECT * FROM item_template WHERE hero_class = :heroClass ORDER BY RANDOM() LIMIT 6", nativeQuery = true)
    Optional<List<ItemTemplate>> find6RandomTemplates(@Param("heroClass") String heroClass);
}