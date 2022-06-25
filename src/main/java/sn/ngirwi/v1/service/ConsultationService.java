package sn.ngirwi.v1.service;

import sn.ngirwi.v1.model.Consultation;

public class ConsultationService {

    public static void update(Consultation consultationObject, Consultation consultation){
        consultationObject.setTime(consultation.getTime());
        consultationObject.setDate(consultation.getDate());
        consultationObject.setTemperature(consultation.getTemperature());
        consultationObject.setWeight(consultation.getWeight());
        consultationObject.setTension(consultation.getTension());
        consultationObject.setGlycemie(consultation.getGlycemie());
        consultationObject.setComment(consultation.getComment());
        consultationObject.setHypothesis(consultation.getHypothesis());
        consultationObject.setExams(consultation.getExams());
        consultationObject.setTreatment(consultation.getTreatment());

    }
}
