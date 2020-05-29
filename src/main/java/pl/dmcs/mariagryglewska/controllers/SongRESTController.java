package pl.dmcs.mariagryglewska.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dmcs.mariagryglewska.model.Song;
import pl.dmcs.mariagryglewska.repository.SongRepository;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/songs")
public class SongRESTController {
    private SongRepository SongRepository;

    @Autowired
    public SongRESTController(SongRepository SongRepository) {
        this.SongRepository = SongRepository;
    }

    @GetMapping("")
    public List<Song> getAllSongs() {
        return SongRepository.findAll();
    }

    @GetMapping("/{id}")
    public Song getSongById(@PathVariable("id") Long id) {
        return SongRepository.findById(id);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Song> deleteSongById(@PathVariable("id") long id) {
        Song contact = SongRepository.findById(id);
        if (contact == null) {
            System.out.println("Contact not found!");
            return new ResponseEntity<Song>(HttpStatus.NOT_FOUND);
        }
        SongRepository.deleteById(id);
        return new ResponseEntity<Song>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteAllSongs() {
        SongRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("")
    public ResponseEntity<Song> addSong(@RequestBody Song song){
        SongRepository.save(song);
        return new ResponseEntity<Song>(HttpStatus.CREATED);
    }

}
