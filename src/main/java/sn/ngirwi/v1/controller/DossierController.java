package sn.ngirwi.v1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.ngirwi.v1.model.DossierMedical;
import sn.ngirwi.v1.repository.DossierRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dossiers")
@CrossOrigin("*")
public class DossierController {

    private final DossierRepository dossierRepository;

    @GetMapping
    public List<DossierMedical> getDossiers() {
        return dossierRepository.findAll();
    }

    @PostMapping
    public DossierMedical createDossier(@RequestBody DossierMedical dossierMedical) {
        return dossierRepository.save(dossierMedical);
    }

    @GetMapping("{id}")
    public ResponseEntity<DossierMedical> getDossierById(@PathVariable  long id) throws Exception{
        DossierMedical dossierMedical = dossierRepository.findById(id)
                .orElseThrow(() -> new Exception("Dossier not exist with id:" + id));
        return ResponseEntity.ok(dossierMedical);
    }

    @PutMapping("{id}")
    public ResponseEntity<DossierMedical> updateDossier(@PathVariable long id,@RequestBody DossierMedical dossierMedical) throws Exception{
        DossierMedical dossier = dossierRepository.findById(id)
                .orElseThrow(() -> new Exception("Dossier not exist with id: " + id));

        dossierRepository.save(dossier);

        return ResponseEntity.ok(dossier);
    }


}
