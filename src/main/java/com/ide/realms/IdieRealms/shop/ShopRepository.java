package com.ide.realms.IdieRealms.shop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface ShopRepository extends JpaRepository<Shop, Long>{

    Shop findByHeroId (Long heroId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Item i WHERE i.id IN (SELECT item.id FROM Shop s JOIN s.itemsInOffer item)")
    void deleteAllItemsFromAllShops();

}
