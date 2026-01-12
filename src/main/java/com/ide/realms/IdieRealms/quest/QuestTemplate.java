package com.ide.realms.IdieRealms.quest;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class QuestTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 500)
    private String description;

    private String imageUrl;
}