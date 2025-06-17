package fr.ladevie.tarot.score.dtos;

import fr.ladevie.tarot.score.business.PlayerEntity;

import java.util.UUID;

public class UserDTO {
    private UUID id; // null si nouveau joueur
    private String name;

    public UserDTO() {}

    public UserDTO(PlayerEntity player) {
        this.id = player.getId();
        this.name = player.getName();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
