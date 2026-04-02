package com.social.service.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Guild {

    private final UUID id;
    private final String name;
    private final UUID ownerSocialId;
    private final List<UUID> memberSocialIds;
    private int totalGold;
    private int coachLevel;

    public List<UUID> getMemberSocialIds() {
        return memberSocialIds;
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public UUID getOwnerSocialId() {
        return ownerSocialId;
    }

    public int getTotalGold() {
        return totalGold;
    }

    public void setTotalGold(int totalGold) {
        this.totalGold = totalGold;
    }

    public int getCoachLevel() {
        return coachLevel;
    }

    public void setCoachLevel(int coachLevel) {
        this.coachLevel = coachLevel;
    }

    public Guild(UUID id, String name, UUID ownerSocialId, List<UUID> memberSocialIds, int totalGold, int coachLevel) {
        this.id = id;
        this.name = name;
        this.ownerSocialId = ownerSocialId;
        this.memberSocialIds = new ArrayList<>(memberSocialIds);
        this.totalGold = totalGold;
        this.coachLevel = coachLevel;
    }

    public static Guild createNew (String name, UUID ownerSocialId) {
        return new Guild(
                UUID.randomUUID(),
                name,
                ownerSocialId,
                new ArrayList<>(List.of(ownerSocialId)),
                0,
                0
        );
    }

    public void addMember (UUID playerSocialId) {
        if (memberSocialIds.size() >= 50) {
            throw new IllegalArgumentException("Guild is full already");
        }

        if (!memberSocialIds.contains(playerSocialId)) {
            this.memberSocialIds.add(playerSocialId);
        }
    }

}