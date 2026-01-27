package com.ide.realms.IdieRealms.shop;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long>{

    Shop findByHeroId (Long heroId);

}
