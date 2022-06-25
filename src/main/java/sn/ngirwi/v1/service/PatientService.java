package sn.ngirwi.v1.service;

import lombok.RequiredArgsConstructor;
import sn.ngirwi.v1.model.Patient;

@RequiredArgsConstructor
public class PatientService {


    public static void update(Patient patientObject, Patient patient){

        patientObject.setName(patient.getName());
        patientObject.setSurname(patient.getSurname());
        patientObject.setBirthday(patient.getBirthday());
        patientObject.setGender(patient.getGender());
        patientObject.setCni(patient.getCni());
        patientObject.setPhone(patient.getPhone());
        patientObject.setJob(patient.getJob());
        patientObject.setMaritalStatus(patient.getMaritalStatus());
        patientObject.setAddress(patient.getAddress());
    }
}
