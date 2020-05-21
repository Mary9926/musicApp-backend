package pl.dmcs.mariagryglewska.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dmcs.mariagryglewska.model.Song;

public interface SongRepository extends JpaRepository<Song, Integer> {
        Song findById(Long id);
        void deleteById(Long id);
}
