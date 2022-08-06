package sn.ngirwi.v1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sn.ngirwi.v1.model.Ordonance;
import sn.ngirwi.v1.repository.OrdonanceRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ordonances")
@CrossOrigin("*")
public class OrdonanceController {

    private final OrdonanceRepository ordonanceRepository;

    @GetMapping
    public List<Ordonance> getOrdonances() {
        return ordonanceRepository.findAll();
    }

    @PostMapping
    public Ordonance createOrdonance(@RequestBody Ordonance ordonance) {
        return ordonanceRepository.save(ordonance);
    }
}
