package com.social.service.application.service;

import com.social.service.domain.model.Player;
import com.social.service.domain.port.in.RegisterPlayerUseCase;
import com.social.service.domain.port.out.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerService implements RegisterPlayerUseCase {

    private final PlayerRepository playerRepository;

    @Override
    public void register(UUID socialid, String username) {
        if (playerRepository.findBySocialId(socialid).isEmpty()) {
            Player player = new Player(socialid,username);
            playerRepository.save(player);

            log.info("Player {} successfuly registered!");
        }
    }

}