package sn.ngirwi.medical.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import sn.ngirwi.medical.IntegrationTest;
import sn.ngirwi.medical.domain.Consultation;
import sn.ngirwi.medical.repository.ConsultationRepository;
import sn.ngirwi.medical.service.ConsultationService;
import sn.ngirwi.medical.service.dto.ConsultationDTO;
import sn.ngirwi.medical.service.mapper.ConsultationMapper;

/**
 * Integration tests for the {@link ConsultationResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ConsultationResourceIT {

    private static final Instant DEFAULT_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_TEMPERATURE = 1D;
    private static final Double UPDATED_TEMPERATURE = 2D;

    private static final Double DEFAULT_WEIGHT = 1D;
    private static final Double UPDATED_WEIGHT = 2D;

    private static final String DEFAULT_TENSION = "AAAAAAAAAA";
    private static final String UPDATED_TENSION = "BBBBBBBBBB";

    private static final String DEFAULT_GLYCEMIE = "AAAAAAAAAA";
    private static final String UPDATED_GLYCEMIE = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_HYPOTHESIS = "AAAAAAAAAA";
    private static final String UPDATED_HYPOTHESIS = "BBBBBBBBBB";

    private static final String DEFAULT_EXAMS = "AAAAAAAAAA";
    private static final String UPDATED_EXAMS = "BBBBBBBBBB";

    private static final String DEFAULT_TREATMENT = "AAAAAAAAAA";
    private static final String UPDATED_TREATMENT = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/consultations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConsultationRepository consultationRepository;

    @Mock
    private ConsultationRepository consultationRepositoryMock;

    @Autowired
    private ConsultationMapper consultationMapper;

    @Mock
    private ConsultationService consultationServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConsultationMockMvc;

    private Consultation consultation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consultation createEntity(EntityManager em) {
        Consultation consultation = new Consultation()
            .dateTime(DEFAULT_DATE_TIME)
            .temperature(DEFAULT_TEMPERATURE)
            .weight(DEFAULT_WEIGHT)
            .tension(DEFAULT_TENSION)
            .glycemie(DEFAULT_GLYCEMIE)
            .comment(DEFAULT_COMMENT)
            .hypothesis(DEFAULT_HYPOTHESIS)
            .exams(DEFAULT_EXAMS)
            .treatment(DEFAULT_TREATMENT)
            .author(DEFAULT_AUTHOR);
        return consultation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consultation createUpdatedEntity(EntityManager em) {
        Consultation consultation = new Consultation()
            .dateTime(UPDATED_DATE_TIME)
            .temperature(UPDATED_TEMPERATURE)
            .weight(UPDATED_WEIGHT)
            .tension(UPDATED_TENSION)
            .glycemie(UPDATED_GLYCEMIE)
            .comment(UPDATED_COMMENT)
            .hypothesis(UPDATED_HYPOTHESIS)
            .exams(UPDATED_EXAMS)
            .treatment(UPDATED_TREATMENT)
            .author(UPDATED_AUTHOR);
        return consultation;
    }

    @BeforeEach
    public void initTest() {
        consultation = createEntity(em);
    }

    @Test
    @Transactional
    void createConsultation() throws Exception {
        int databaseSizeBeforeCreate = consultationRepository.findAll().size();
        // Create the Consultation
        ConsultationDTO consultationDTO = consultationMapper.toDto(consultation);
        restConsultationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(consultationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Consultation in the database
        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeCreate + 1);
        Consultation testConsultation = consultationList.get(consultationList.size() - 1);
        assertThat(testConsultation.getDateTime()).isEqualTo(DEFAULT_DATE_TIME);
        assertThat(testConsultation.getTemperature()).isEqualTo(DEFAULT_TEMPERATURE);
        assertThat(testConsultation.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testConsultation.getTension()).isEqualTo(DEFAULT_TENSION);
        assertThat(testConsultation.getGlycemie()).isEqualTo(DEFAULT_GLYCEMIE);
        assertThat(testConsultation.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testConsultation.getHypothesis()).isEqualTo(DEFAULT_HYPOTHESIS);
        assertThat(testConsultation.getExams()).isEqualTo(DEFAULT_EXAMS);
        assertThat(testConsultation.getTreatment()).isEqualTo(DEFAULT_TREATMENT);
        assertThat(testConsultation.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
    }

    @Test
    @Transactional
    void createConsultationWithExistingId() throws Exception {
        // Create the Consultation with an existing ID
        consultation.setId(1L);
        ConsultationDTO consultationDTO = consultationMapper.toDto(consultation);

        int databaseSizeBeforeCreate = consultationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsultationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(consultationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Consultation in the database
        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTemperatureIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultationRepository.findAll().size();
        // set the field null
        consultation.setTemperature(null);

        // Create the Consultation, which fails.
        ConsultationDTO consultationDTO = consultationMapper.toDto(consultation);

        restConsultationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(consultationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultationRepository.findAll().size();
        // set the field null
        consultation.setWeight(null);

        // Create the Consultation, which fails.
        ConsultationDTO consultationDTO = consultationMapper.toDto(consultation);

        restConsultationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(consultationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTensionIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultationRepository.findAll().size();
        // set the field null
        consultation.setTension(null);

        // Create the Consultation, which fails.
        ConsultationDTO consultationDTO = consultationMapper.toDto(consultation);

        restConsultationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(consultationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHypothesisIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultationRepository.findAll().size();
        // set the field null
        consultation.setHypothesis(null);

        // Create the Consultation, which fails.
        ConsultationDTO consultationDTO = consultationMapper.toDto(consultation);

        restConsultationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(consultationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkExamsIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultationRepository.findAll().size();
        // set the field null
        consultation.setExams(null);

        // Create the Consultation, which fails.
        ConsultationDTO consultationDTO = consultationMapper.toDto(consultation);

        restConsultationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(consultationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTreatmentIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultationRepository.findAll().size();
        // set the field null
        consultation.setTreatment(null);

        // Create the Consultation, which fails.
        ConsultationDTO consultationDTO = consultationMapper.toDto(consultation);

        restConsultationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(consultationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllConsultations() throws Exception {
        // Initialize the database
        consultationRepository.saveAndFlush(consultation);

        // Get all the consultationList
        restConsultationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consultation.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateTime").value(hasItem(DEFAULT_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE.doubleValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].tension").value(hasItem(DEFAULT_TENSION)))
            .andExpect(jsonPath("$.[*].glycemie").value(hasItem(DEFAULT_GLYCEMIE)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].hypothesis").value(hasItem(DEFAULT_HYPOTHESIS)))
            .andExpect(jsonPath("$.[*].exams").value(hasItem(DEFAULT_EXAMS)))
            .andExpect(jsonPath("$.[*].treatment").value(hasItem(DEFAULT_TREATMENT)))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllConsultationsWithEagerRelationshipsIsEnabled() throws Exception {
        when(consultationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restConsultationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(consultationServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllConsultationsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(consultationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restConsultationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(consultationRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getConsultation() throws Exception {
        // Initialize the database
        consultationRepository.saveAndFlush(consultation);

        // Get the consultation
        restConsultationMockMvc
            .perform(get(ENTITY_API_URL_ID, consultation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(consultation.getId().intValue()))
            .andExpect(jsonPath("$.dateTime").value(DEFAULT_DATE_TIME.toString()))
            .andExpect(jsonPath("$.temperature").value(DEFAULT_TEMPERATURE.doubleValue()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.tension").value(DEFAULT_TENSION))
            .andExpect(jsonPath("$.glycemie").value(DEFAULT_GLYCEMIE))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.hypothesis").value(DEFAULT_HYPOTHESIS))
            .andExpect(jsonPath("$.exams").value(DEFAULT_EXAMS))
            .andExpect(jsonPath("$.treatment").value(DEFAULT_TREATMENT))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR));
    }

    @Test
    @Transactional
    void getNonExistingConsultation() throws Exception {
        // Get the consultation
        restConsultationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingConsultation() throws Exception {
        // Initialize the database
        consultationRepository.saveAndFlush(consultation);

        int databaseSizeBeforeUpdate = consultationRepository.findAll().size();

        // Update the consultation
        Consultation updatedConsultation = consultationRepository.findById(consultation.getId()).get();
        // Disconnect from session so that the updates on updatedConsultation are not directly saved in db
        em.detach(updatedConsultation);
        updatedConsultation
            .dateTime(UPDATED_DATE_TIME)
            .temperature(UPDATED_TEMPERATURE)
            .weight(UPDATED_WEIGHT)
            .tension(UPDATED_TENSION)
            .glycemie(UPDATED_GLYCEMIE)
            .comment(UPDATED_COMMENT)
            .hypothesis(UPDATED_HYPOTHESIS)
            .exams(UPDATED_EXAMS)
            .treatment(UPDATED_TREATMENT)
            .author(UPDATED_AUTHOR);
        ConsultationDTO consultationDTO = consultationMapper.toDto(updatedConsultation);

        restConsultationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, consultationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(consultationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Consultation in the database
        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeUpdate);
        Consultation testConsultation = consultationList.get(consultationList.size() - 1);
        assertThat(testConsultation.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
        assertThat(testConsultation.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
        assertThat(testConsultation.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testConsultation.getTension()).isEqualTo(UPDATED_TENSION);
        assertThat(testConsultation.getGlycemie()).isEqualTo(UPDATED_GLYCEMIE);
        assertThat(testConsultation.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testConsultation.getHypothesis()).isEqualTo(UPDATED_HYPOTHESIS);
        assertThat(testConsultation.getExams()).isEqualTo(UPDATED_EXAMS);
        assertThat(testConsultation.getTreatment()).isEqualTo(UPDATED_TREATMENT);
        assertThat(testConsultation.getAuthor()).isEqualTo(UPDATED_AUTHOR);
    }

    @Test
    @Transactional
    void putNonExistingConsultation() throws Exception {
        int databaseSizeBeforeUpdate = consultationRepository.findAll().size();
        consultation.setId(count.incrementAndGet());

        // Create the Consultation
        ConsultationDTO consultationDTO = consultationMapper.toDto(consultation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsultationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, consultationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(consultationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Consultation in the database
        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConsultation() throws Exception {
        int databaseSizeBeforeUpdate = consultationRepository.findAll().size();
        consultation.setId(count.incrementAndGet());

        // Create the Consultation
        ConsultationDTO consultationDTO = consultationMapper.toDto(consultation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsultationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(consultationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Consultation in the database
        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConsultation() throws Exception {
        int databaseSizeBeforeUpdate = consultationRepository.findAll().size();
        consultation.setId(count.incrementAndGet());

        // Create the Consultation
        ConsultationDTO consultationDTO = consultationMapper.toDto(consultation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsultationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(consultationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Consultation in the database
        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConsultationWithPatch() throws Exception {
        // Initialize the database
        consultationRepository.saveAndFlush(consultation);

        int databaseSizeBeforeUpdate = consultationRepository.findAll().size();

        // Update the consultation using partial update
        Consultation partialUpdatedConsultation = new Consultation();
        partialUpdatedConsultation.setId(consultation.getId());

        partialUpdatedConsultation
            .temperature(UPDATED_TEMPERATURE)
            .tension(UPDATED_TENSION)
            .glycemie(UPDATED_GLYCEMIE)
            .exams(UPDATED_EXAMS)
            .treatment(UPDATED_TREATMENT)
            .author(UPDATED_AUTHOR);

        restConsultationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConsultation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConsultation))
            )
            .andExpect(status().isOk());

        // Validate the Consultation in the database
        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeUpdate);
        Consultation testConsultation = consultationList.get(consultationList.size() - 1);
        assertThat(testConsultation.getDateTime()).isEqualTo(DEFAULT_DATE_TIME);
        assertThat(testConsultation.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
        assertThat(testConsultation.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testConsultation.getTension()).isEqualTo(UPDATED_TENSION);
        assertThat(testConsultation.getGlycemie()).isEqualTo(UPDATED_GLYCEMIE);
        assertThat(testConsultation.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testConsultation.getHypothesis()).isEqualTo(DEFAULT_HYPOTHESIS);
        assertThat(testConsultation.getExams()).isEqualTo(UPDATED_EXAMS);
        assertThat(testConsultation.getTreatment()).isEqualTo(UPDATED_TREATMENT);
        assertThat(testConsultation.getAuthor()).isEqualTo(UPDATED_AUTHOR);
    }

    @Test
    @Transactional
    void fullUpdateConsultationWithPatch() throws Exception {
        // Initialize the database
        consultationRepository.saveAndFlush(consultation);

        int databaseSizeBeforeUpdate = consultationRepository.findAll().size();

        // Update the consultation using partial update
        Consultation partialUpdatedConsultation = new Consultation();
        partialUpdatedConsultation.setId(consultation.getId());

        partialUpdatedConsultation
            .dateTime(UPDATED_DATE_TIME)
            .temperature(UPDATED_TEMPERATURE)
            .weight(UPDATED_WEIGHT)
            .tension(UPDATED_TENSION)
            .glycemie(UPDATED_GLYCEMIE)
            .comment(UPDATED_COMMENT)
            .hypothesis(UPDATED_HYPOTHESIS)
            .exams(UPDATED_EXAMS)
            .treatment(UPDATED_TREATMENT)
            .author(UPDATED_AUTHOR);

        restConsultationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConsultation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConsultation))
            )
            .andExpect(status().isOk());

        // Validate the Consultation in the database
        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeUpdate);
        Consultation testConsultation = consultationList.get(consultationList.size() - 1);
        assertThat(testConsultation.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
        assertThat(testConsultation.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
        assertThat(testConsultation.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testConsultation.getTension()).isEqualTo(UPDATED_TENSION);
        assertThat(testConsultation.getGlycemie()).isEqualTo(UPDATED_GLYCEMIE);
        assertThat(testConsultation.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testConsultation.getHypothesis()).isEqualTo(UPDATED_HYPOTHESIS);
        assertThat(testConsultation.getExams()).isEqualTo(UPDATED_EXAMS);
        assertThat(testConsultation.getTreatment()).isEqualTo(UPDATED_TREATMENT);
        assertThat(testConsultation.getAuthor()).isEqualTo(UPDATED_AUTHOR);
    }

    @Test
    @Transactional
    void patchNonExistingConsultation() throws Exception {
        int databaseSizeBeforeUpdate = consultationRepository.findAll().size();
        consultation.setId(count.incrementAndGet());

        // Create the Consultation
        ConsultationDTO consultationDTO = consultationMapper.toDto(consultation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsultationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, consultationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(consultationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Consultation in the database
        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConsultation() throws Exception {
        int databaseSizeBeforeUpdate = consultationRepository.findAll().size();
        consultation.setId(count.incrementAndGet());

        // Create the Consultation
        ConsultationDTO consultationDTO = consultationMapper.toDto(consultation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsultationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(consultationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Consultation in the database
        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConsultation() throws Exception {
        int databaseSizeBeforeUpdate = consultationRepository.findAll().size();
        consultation.setId(count.incrementAndGet());

        // Create the Consultation
        ConsultationDTO consultationDTO = consultationMapper.toDto(consultation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsultationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(consultationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Consultation in the database
        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConsultation() throws Exception {
        // Initialize the database
        consultationRepository.saveAndFlush(consultation);

        int databaseSizeBeforeDelete = consultationRepository.findAll().size();

        // Delete the consultation
        restConsultationMockMvc
            .perform(delete(ENTITY_API_URL_ID, consultation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
