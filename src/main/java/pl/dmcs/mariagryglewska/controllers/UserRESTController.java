package pl.dmcs.mariagryglewska.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dmcs.mariagryglewska.model.Playlist;
import pl.dmcs.mariagryglewska.model.Song;
import pl.dmcs.mariagryglewska.model.User;
import pl.dmcs.mariagryglewska.repository.PlaylistRepository;
import pl.dmcs.mariagryglewska.repository.SongRepository;
import pl.dmcs.mariagryglewska.repository.UserRepository;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/users")
public class UserRESTController {
    private UserRepository userRepository;
    private PlaylistRepository playlistRepository;
    private SongRepository songRepository;

    @Autowired
    public UserRESTController(UserRepository userRepository,
                              PlaylistRepository playlistRepository,
                              SongRepository songRepository) {
        this.userRepository = userRepository;
        this.playlistRepository = playlistRepository;
        this.songRepository = songRepository;
    }

    @GetMapping("")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}/playlists")
    public List<Playlist> getPlaylistsFromUser(@PathVariable("id") Long id) {
        return userRepository.findById(id).getPlaylists();
    }

    @GetMapping("/{userId}/playlists/{playlistId}")
    public Playlist getPlaylistById(@PathVariable("playlistId") Long playlistId) {
        return playlistRepository.findById(playlistId);
    }

    @PostMapping("/{userId}/playlists")
    public ResponseEntity<?> addPlaylistToUser(@RequestBody Playlist playlist,
                                           @PathVariable("userId") Long userId) {
        User user = userRepository.findById(userId);
        user.getPlaylists().add(playlist);
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{userId}/playlists/{playlistId}/songs/{songId}")
    public ResponseEntity<?> addSongToPlaylist(@PathVariable("songId") Long songId,
                                              @PathVariable("playlistId") Long playlistId) {
        Song song = songRepository.findById(songId);
        Playlist playlist = playlistRepository.findById(playlistId);
        playlist.getSongs().add(song);
        playlistRepository.save(playlist);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/playlists/{playlistId}")
    public ResponseEntity<?> deletePlaylistById(@PathVariable("id") Long id,
                                            @PathVariable("playlistId") Long playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId);
        User user = userRepository.findById(id);
        user.getPlaylists().remove(playlist);
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}/playlist/{playlistId}/songs/{songId}")
    public ResponseEntity<?> deleteSongById(@PathVariable("playlistId") Long playlistId,
                                               @PathVariable("songId") Long songId) {
        Song song = songRepository.findById(songId);
        Playlist playlist = playlistRepository.findById(playlistId);
        playlist.getSongs().remove(song);
        playlistRepository.save(playlist);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
