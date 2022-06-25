package sn.ngirwi.v1.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.ngirwi.v1.model.Patient;
import sn.ngirwi.v1.model.Patient;
import sn.ngirwi.v1.repository.PatientRepository;
import sn.ngirwi.v1.service.PatientService;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin("*")
@RequiredArgsConstructor
public class PatientController {

    private final PatientRepository patientRepository;


    @GetMapping
    public List<Patient> fetchPatients(){
        return patientRepository.findAll();
    }

    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        return patientRepository.save(patient);
    }

    @GetMapping("{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable  long id) throws Exception{
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new Exception("Patient not exist with id:" + id));
        return ResponseEntity.ok(patient);
    }



    @PutMapping("{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable long id,@RequestBody Patient patient) throws Exception{
        Patient updatePatient = patientRepository.findById(id)
                .orElseThrow(() -> new Exception("Patient not exist with id: " + id));

        PatientService.update(updatePatient ,patient);

        patientRepository.save(updatePatient);

        return ResponseEntity.ok(updatePatient);
    }
}
