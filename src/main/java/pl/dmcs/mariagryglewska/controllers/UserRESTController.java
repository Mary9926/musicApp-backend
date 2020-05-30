package pl.dmcs.mariagryglewska.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import pl.dmcs.mariagryglewska.model.Playlist;
import pl.dmcs.mariagryglewska.model.Song;
import pl.dmcs.mariagryglewska.model.User;
import pl.dmcs.mariagryglewska.repository.PlaylistRepository;
import pl.dmcs.mariagryglewska.repository.SongRepository;
import pl.dmcs.mariagryglewska.repository.UserRepository;

import java.util.ArrayList;
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

    @GetMapping("/{username}/playlists")
    public List<Playlist> getPlaylistsFromUser(@PathVariable("username") String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with -> username: " + username));
        return user.getPlaylists();
    }

    @GetMapping("/{username}/playlists/{playlistId}")
    public Playlist getPlaylistById(@PathVariable("playlistId") Long playlistId) {
        return playlistRepository.findById(playlistId);
    }

    @PostMapping("/{username}/playlists")
    public ResponseEntity<?> addPlaylistToUser(@RequestBody Playlist playlist,
                                           @PathVariable("username") String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with -> username: " + username));
        user.getPlaylists().add(playlist);
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{username}/playlists/{playlistId}/songs/{songId}")
    public ResponseEntity<?> addSongToPlaylist(@PathVariable("songId") Long songId,
                                              @PathVariable("playlistId") Long playlistId) {
        Song song = songRepository.findById(songId);
        Playlist playlist = playlistRepository.findById(playlistId);
        if (!playlist.getSongs().contains(song)) {
            playlist.getSongs().add(song);
            playlistRepository.save(playlist);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{username}/playlists/{playlistId}")
    public ResponseEntity<?> deletePlaylistById(@PathVariable("username") String username,
                                            @PathVariable("playlistId") Long playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId);
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with -> username: " + username));
        user.getPlaylists().remove(playlist);
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{username}/playlists/{playlistId}/songs/{songId}")
    public ResponseEntity<?> deleteSongById(@PathVariable("playlistId") Long playlistId,
                                               @PathVariable("songId") Long songId) {
        Song song = songRepository.findById(songId);
        Playlist playlist = playlistRepository.findById(playlistId);
        playlist.getSongs().remove(song);
        playlistRepository.save(playlist);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
