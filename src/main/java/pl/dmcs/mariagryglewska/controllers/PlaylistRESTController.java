package pl.dmcs.mariagryglewska.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dmcs.mariagryglewska.model.Playlist;
import pl.dmcs.mariagryglewska.repository.PlaylistRepository;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/playlists")
public class PlaylistRESTController {
    private PlaylistRepository playlistRepository;

    @Autowired
    public PlaylistRESTController(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    @GetMapping("")
    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    @GetMapping("/{id}")
    public Playlist getPlaylistById(@PathVariable("id") Long id) {
        return playlistRepository.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlaylistById(@PathVariable("id") Long id) {
        playlistRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
