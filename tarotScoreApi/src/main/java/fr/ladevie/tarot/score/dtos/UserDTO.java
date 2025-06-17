package fr.ladevie.tarot.score.dtos;

import java.util.UUID;

public class UserDTO {
    private UUID id; // null si nouveau joueur
    private String name;

    // Constructeurs, getters, setters

    public UserDTO() {}

    public UserDTO(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}