package pl.dmcs.mariagryglewska.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dmcs.mariagryglewska.model.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {
    Playlist findById(Long id);
    void deleteById(Long id);
}
