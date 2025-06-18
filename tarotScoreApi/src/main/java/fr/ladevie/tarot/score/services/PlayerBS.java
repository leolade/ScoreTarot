package fr.ladevie.tarot.score.services;

import fr.ladevie.tarot.score.business.PlayerEntity;
import fr.ladevie.tarot.score.business.PlayerRepository;
import fr.ladevie.tarot.score.dtos.UserDTO;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerBS {

    private final PlayerRepository playerRepository;

    public PlayerBS(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public PlayerEntity getOrCreatePlayer(@Nullable UserDTO dto) {
        if (dto == null) {
            return null;
        }
        Optional<PlayerEntity> byId = dto.getId() == null ? Optional.empty() : this.playerRepository.findById(dto.getId());
        if (byId.isPresent()) {
            return byId.get();
        } else {
            PlayerEntity entity = new PlayerEntity();
            entity.setName(dto.getName());
            return this.playerRepository.save(entity);
        }
    }

    @Nullable
    public PlayerEntity findPlayerByName(List<PlayerEntity> players, String name) {
        return players.stream().filter(p -> p.getName().equals(name)).findFirst().orElse(null);
    }
}
