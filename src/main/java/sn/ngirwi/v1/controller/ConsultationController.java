package sn.ngirwi.v1.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.ngirwi.v1.model.Consultation;
import sn.ngirwi.v1.repository.ConsulationRepository;
import sn.ngirwi.v1.service.ConsultationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/consultations")
@CrossOrigin("*")
public class ConsultationController {

    private final ConsulationRepository consultationRepository;

    @GetMapping
    public List<Consultation> getConsultations() {
        return consultationRepository.findAll();
    }

    @PostMapping
    public Consultation createConsultation(@RequestBody Consultation consultation) {
        return consultationRepository.save(consultation);
    }

    @GetMapping("{patient}")
    public ResponseEntity<Consultation> getConsultationByPatient(@PathVariable  String patient) throws Exception{
        Consultation consultation = consultationRepository.findByPatient(patient);
                //.orElseThrow(() -> new Exception("Consultation not exist with id:" + patient));
        return ResponseEntity.ok(consultation);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<Consultation> getConsultationById(@PathVariable  Long id) throws Exception{
        Consultation consultation = consultationRepository.findById(id)
        .orElseThrow(() -> new Exception("Consultation not exist with id:" + id));
        return ResponseEntity.ok(consultation);
    }

    @PutMapping("{id}")
    public ResponseEntity<Consultation> updateConsultation(@PathVariable long id,@RequestBody Consultation consultationInfo) throws Exception{
        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(() -> new Exception("Dossier not exist with id: " + id));

        ConsultationService.update(consultation, consultationInfo);

        consultationRepository.save(consultation);

        return ResponseEntity.ok(consultation);
    }
}
