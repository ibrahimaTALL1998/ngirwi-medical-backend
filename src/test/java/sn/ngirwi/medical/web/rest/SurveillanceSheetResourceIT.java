package sn.ngirwi.medical.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import sn.ngirwi.medical.IntegrationTest;
import sn.ngirwi.medical.domain.SurveillanceSheet;
import sn.ngirwi.medical.repository.SurveillanceSheetRepository;

/**
 * Integration tests for the {@link SurveillanceSheetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SurveillanceSheetResourceIT {

    private static final String DEFAULT_TA = "AAAAAAAAAA";
    private static final String UPDATED_TA = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPERATURE = "AAAAAAAAAA";
    private static final String UPDATED_TEMPERATURE = "BBBBBBBBBB";

    private static final String DEFAULT_PULSE_RATE = "AAAAAAAAAA";
    private static final String UPDATED_PULSE_RATE = "BBBBBBBBBB";

    private static final String DEFAULT_RESPIRATORY_FREQUENCY = "AAAAAAAAAA";
    private static final String UPDATED_RESPIRATORY_FREQUENCY = "BBBBBBBBBB";

    private static final String DEFAULT_RECOLORATION_TIME = "AAAAAAAAAA";
    private static final String UPDATED_RECOLORATION_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_GLASGOW = "AAAAAAAAAA";
    private static final String UPDATED_GLASGOW = "BBBBBBBBBB";

    private static final String DEFAULT_GRAVITY_CLASS = "AAAAAAAAAA";
    private static final String UPDATED_GRAVITY_CLASS = "BBBBBBBBBB";

    private static final String DEFAULT_HORARY_DIURESIS = "AAAAAAAAAA";
    private static final String UPDATED_HORARY_DIURESIS = "BBBBBBBBBB";

    private static final String DEFAULT_SPO_2 = "AAAAAAAAAA";
    private static final String UPDATED_SPO_2 = "BBBBBBBBBB";

    private static final String DEFAULT_TREATMENT = "AAAAAAAAAA";
    private static final String UPDATED_TREATMENT = "BBBBBBBBBB";

    private static final String DEFAULT_HEALTH_EVOLUTION = "AAAAAAAAAA";
    private static final String UPDATED_HEALTH_EVOLUTION = "BBBBBBBBBB";

    private static final String DEFAULT_SHEET_DATE = "AAAAAAAAAA";
    private static final String UPDATED_SHEET_DATE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/surveillance-sheets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SurveillanceSheetRepository surveillanceSheetRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSurveillanceSheetMockMvc;

    private SurveillanceSheet surveillanceSheet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SurveillanceSheet createEntity(EntityManager em) {
        SurveillanceSheet surveillanceSheet = new SurveillanceSheet()
            .ta(DEFAULT_TA)
            .temperature(DEFAULT_TEMPERATURE)
            .pulseRate(DEFAULT_PULSE_RATE)
            .respiratoryFrequency(DEFAULT_RESPIRATORY_FREQUENCY)
            .recolorationTime(DEFAULT_RECOLORATION_TIME)
            .glasgow(DEFAULT_GLASGOW)
            .gravityClass(DEFAULT_GRAVITY_CLASS)
            .horaryDiuresis(DEFAULT_HORARY_DIURESIS)
            .spo2(DEFAULT_SPO_2)
            .treatment(DEFAULT_TREATMENT)
            .healthEvolution(DEFAULT_HEALTH_EVOLUTION)
            .sheetDate(DEFAULT_SHEET_DATE);
        return surveillanceSheet;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SurveillanceSheet createUpdatedEntity(EntityManager em) {
        SurveillanceSheet surveillanceSheet = new SurveillanceSheet()
            .ta(UPDATED_TA)
            .temperature(UPDATED_TEMPERATURE)
            .pulseRate(UPDATED_PULSE_RATE)
            .respiratoryFrequency(UPDATED_RESPIRATORY_FREQUENCY)
            .recolorationTime(UPDATED_RECOLORATION_TIME)
            .glasgow(UPDATED_GLASGOW)
            .gravityClass(UPDATED_GRAVITY_CLASS)
            .horaryDiuresis(UPDATED_HORARY_DIURESIS)
            .spo2(UPDATED_SPO_2)
            .treatment(UPDATED_TREATMENT)
            .healthEvolution(UPDATED_HEALTH_EVOLUTION)
            .sheetDate(UPDATED_SHEET_DATE);
        return surveillanceSheet;
    }

    @BeforeEach
    public void initTest() {
        surveillanceSheet = createEntity(em);
    }

    @Test
    @Transactional
    void createSurveillanceSheet() throws Exception {
        int databaseSizeBeforeCreate = surveillanceSheetRepository.findAll().size();
        // Create the SurveillanceSheet
        restSurveillanceSheetMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(surveillanceSheet))
            )
            .andExpect(status().isCreated());

        // Validate the SurveillanceSheet in the database
        List<SurveillanceSheet> surveillanceSheetList = surveillanceSheetRepository.findAll();
        assertThat(surveillanceSheetList).hasSize(databaseSizeBeforeCreate + 1);
        SurveillanceSheet testSurveillanceSheet = surveillanceSheetList.get(surveillanceSheetList.size() - 1);
        assertThat(testSurveillanceSheet.getTa()).isEqualTo(DEFAULT_TA);
        assertThat(testSurveillanceSheet.getTemperature()).isEqualTo(DEFAULT_TEMPERATURE);
        assertThat(testSurveillanceSheet.getPulseRate()).isEqualTo(DEFAULT_PULSE_RATE);
        assertThat(testSurveillanceSheet.getRespiratoryFrequency()).isEqualTo(DEFAULT_RESPIRATORY_FREQUENCY);
        assertThat(testSurveillanceSheet.getRecolorationTime()).isEqualTo(DEFAULT_RECOLORATION_TIME);
        assertThat(testSurveillanceSheet.getGlasgow()).isEqualTo(DEFAULT_GLASGOW);
        assertThat(testSurveillanceSheet.getGravityClass()).isEqualTo(DEFAULT_GRAVITY_CLASS);
        assertThat(testSurveillanceSheet.getHoraryDiuresis()).isEqualTo(DEFAULT_HORARY_DIURESIS);
        assertThat(testSurveillanceSheet.getSpo2()).isEqualTo(DEFAULT_SPO_2);
        assertThat(testSurveillanceSheet.getTreatment()).isEqualTo(DEFAULT_TREATMENT);
        assertThat(testSurveillanceSheet.getHealthEvolution()).isEqualTo(DEFAULT_HEALTH_EVOLUTION);
        assertThat(testSurveillanceSheet.getSheetDate()).isEqualTo(DEFAULT_SHEET_DATE);
    }

    @Test
    @Transactional
    void createSurveillanceSheetWithExistingId() throws Exception {
        // Create the SurveillanceSheet with an existing ID
        surveillanceSheet.setId(1L);

        int databaseSizeBeforeCreate = surveillanceSheetRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSurveillanceSheetMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(surveillanceSheet))
            )
            .andExpect(status().isBadRequest());

        // Validate the SurveillanceSheet in the database
        List<SurveillanceSheet> surveillanceSheetList = surveillanceSheetRepository.findAll();
        assertThat(surveillanceSheetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSurveillanceSheets() throws Exception {
        // Initialize the database
        surveillanceSheetRepository.saveAndFlush(surveillanceSheet);

        // Get all the surveillanceSheetList
        restSurveillanceSheetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(surveillanceSheet.getId().intValue())))
            .andExpect(jsonPath("$.[*].ta").value(hasItem(DEFAULT_TA)))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE)))
            .andExpect(jsonPath("$.[*].pulseRate").value(hasItem(DEFAULT_PULSE_RATE)))
            .andExpect(jsonPath("$.[*].respiratoryFrequency").value(hasItem(DEFAULT_RESPIRATORY_FREQUENCY)))
            .andExpect(jsonPath("$.[*].recolorationTime").value(hasItem(DEFAULT_RECOLORATION_TIME)))
            .andExpect(jsonPath("$.[*].glasgow").value(hasItem(DEFAULT_GLASGOW)))
            .andExpect(jsonPath("$.[*].gravityClass").value(hasItem(DEFAULT_GRAVITY_CLASS)))
            .andExpect(jsonPath("$.[*].horaryDiuresis").value(hasItem(DEFAULT_HORARY_DIURESIS)))
            .andExpect(jsonPath("$.[*].spo2").value(hasItem(DEFAULT_SPO_2)))
            .andExpect(jsonPath("$.[*].treatment").value(hasItem(DEFAULT_TREATMENT)))
            .andExpect(jsonPath("$.[*].healthEvolution").value(hasItem(DEFAULT_HEALTH_EVOLUTION)))
            .andExpect(jsonPath("$.[*].sheetDate").value(hasItem(DEFAULT_SHEET_DATE)));
    }

    @Test
    @Transactional
    void getSurveillanceSheet() throws Exception {
        // Initialize the database
        surveillanceSheetRepository.saveAndFlush(surveillanceSheet);

        // Get the surveillanceSheet
        restSurveillanceSheetMockMvc
            .perform(get(ENTITY_API_URL_ID, surveillanceSheet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(surveillanceSheet.getId().intValue()))
            .andExpect(jsonPath("$.ta").value(DEFAULT_TA))
            .andExpect(jsonPath("$.temperature").value(DEFAULT_TEMPERATURE))
            .andExpect(jsonPath("$.pulseRate").value(DEFAULT_PULSE_RATE))
            .andExpect(jsonPath("$.respiratoryFrequency").value(DEFAULT_RESPIRATORY_FREQUENCY))
            .andExpect(jsonPath("$.recolorationTime").value(DEFAULT_RECOLORATION_TIME))
            .andExpect(jsonPath("$.glasgow").value(DEFAULT_GLASGOW))
            .andExpect(jsonPath("$.gravityClass").value(DEFAULT_GRAVITY_CLASS))
            .andExpect(jsonPath("$.horaryDiuresis").value(DEFAULT_HORARY_DIURESIS))
            .andExpect(jsonPath("$.spo2").value(DEFAULT_SPO_2))
            .andExpect(jsonPath("$.treatment").value(DEFAULT_TREATMENT))
            .andExpect(jsonPath("$.healthEvolution").value(DEFAULT_HEALTH_EVOLUTION))
            .andExpect(jsonPath("$.sheetDate").value(DEFAULT_SHEET_DATE));
    }

    @Test
    @Transactional
    void getNonExistingSurveillanceSheet() throws Exception {
        // Get the surveillanceSheet
        restSurveillanceSheetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSurveillanceSheet() throws Exception {
        // Initialize the database
        surveillanceSheetRepository.saveAndFlush(surveillanceSheet);

        int databaseSizeBeforeUpdate = surveillanceSheetRepository.findAll().size();

        // Update the surveillanceSheet
        SurveillanceSheet updatedSurveillanceSheet = surveillanceSheetRepository.findById(surveillanceSheet.getId()).get();
        // Disconnect from session so that the updates on updatedSurveillanceSheet are not directly saved in db
        em.detach(updatedSurveillanceSheet);
        updatedSurveillanceSheet
            .ta(UPDATED_TA)
            .temperature(UPDATED_TEMPERATURE)
            .pulseRate(UPDATED_PULSE_RATE)
            .respiratoryFrequency(UPDATED_RESPIRATORY_FREQUENCY)
            .recolorationTime(UPDATED_RECOLORATION_TIME)
            .glasgow(UPDATED_GLASGOW)
            .gravityClass(UPDATED_GRAVITY_CLASS)
            .horaryDiuresis(UPDATED_HORARY_DIURESIS)
            .spo2(UPDATED_SPO_2)
            .treatment(UPDATED_TREATMENT)
            .healthEvolution(UPDATED_HEALTH_EVOLUTION)
            .sheetDate(UPDATED_SHEET_DATE);

        restSurveillanceSheetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSurveillanceSheet.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSurveillanceSheet))
            )
            .andExpect(status().isOk());

        // Validate the SurveillanceSheet in the database
        List<SurveillanceSheet> surveillanceSheetList = surveillanceSheetRepository.findAll();
        assertThat(surveillanceSheetList).hasSize(databaseSizeBeforeUpdate);
        SurveillanceSheet testSurveillanceSheet = surveillanceSheetList.get(surveillanceSheetList.size() - 1);
        assertThat(testSurveillanceSheet.getTa()).isEqualTo(UPDATED_TA);
        assertThat(testSurveillanceSheet.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
        assertThat(testSurveillanceSheet.getPulseRate()).isEqualTo(UPDATED_PULSE_RATE);
        assertThat(testSurveillanceSheet.getRespiratoryFrequency()).isEqualTo(UPDATED_RESPIRATORY_FREQUENCY);
        assertThat(testSurveillanceSheet.getRecolorationTime()).isEqualTo(UPDATED_RECOLORATION_TIME);
        assertThat(testSurveillanceSheet.getGlasgow()).isEqualTo(UPDATED_GLASGOW);
        assertThat(testSurveillanceSheet.getGravityClass()).isEqualTo(UPDATED_GRAVITY_CLASS);
        assertThat(testSurveillanceSheet.getHoraryDiuresis()).isEqualTo(UPDATED_HORARY_DIURESIS);
        assertThat(testSurveillanceSheet.getSpo2()).isEqualTo(UPDATED_SPO_2);
        assertThat(testSurveillanceSheet.getTreatment()).isEqualTo(UPDATED_TREATMENT);
        assertThat(testSurveillanceSheet.getHealthEvolution()).isEqualTo(UPDATED_HEALTH_EVOLUTION);
        assertThat(testSurveillanceSheet.getSheetDate()).isEqualTo(UPDATED_SHEET_DATE);
    }

    @Test
    @Transactional
    void putNonExistingSurveillanceSheet() throws Exception {
        int databaseSizeBeforeUpdate = surveillanceSheetRepository.findAll().size();
        surveillanceSheet.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSurveillanceSheetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, surveillanceSheet.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(surveillanceSheet))
            )
            .andExpect(status().isBadRequest());

        // Validate the SurveillanceSheet in the database
        List<SurveillanceSheet> surveillanceSheetList = surveillanceSheetRepository.findAll();
        assertThat(surveillanceSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSurveillanceSheet() throws Exception {
        int databaseSizeBeforeUpdate = surveillanceSheetRepository.findAll().size();
        surveillanceSheet.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSurveillanceSheetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(surveillanceSheet))
            )
            .andExpect(status().isBadRequest());

        // Validate the SurveillanceSheet in the database
        List<SurveillanceSheet> surveillanceSheetList = surveillanceSheetRepository.findAll();
        assertThat(surveillanceSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSurveillanceSheet() throws Exception {
        int databaseSizeBeforeUpdate = surveillanceSheetRepository.findAll().size();
        surveillanceSheet.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSurveillanceSheetMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(surveillanceSheet))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SurveillanceSheet in the database
        List<SurveillanceSheet> surveillanceSheetList = surveillanceSheetRepository.findAll();
        assertThat(surveillanceSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSurveillanceSheetWithPatch() throws Exception {
        // Initialize the database
        surveillanceSheetRepository.saveAndFlush(surveillanceSheet);

        int databaseSizeBeforeUpdate = surveillanceSheetRepository.findAll().size();

        // Update the surveillanceSheet using partial update
        SurveillanceSheet partialUpdatedSurveillanceSheet = new SurveillanceSheet();
        partialUpdatedSurveillanceSheet.setId(surveillanceSheet.getId());

        partialUpdatedSurveillanceSheet.gravityClass(UPDATED_GRAVITY_CLASS).spo2(UPDATED_SPO_2).sheetDate(UPDATED_SHEET_DATE);

        restSurveillanceSheetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSurveillanceSheet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSurveillanceSheet))
            )
            .andExpect(status().isOk());

        // Validate the SurveillanceSheet in the database
        List<SurveillanceSheet> surveillanceSheetList = surveillanceSheetRepository.findAll();
        assertThat(surveillanceSheetList).hasSize(databaseSizeBeforeUpdate);
        SurveillanceSheet testSurveillanceSheet = surveillanceSheetList.get(surveillanceSheetList.size() - 1);
        assertThat(testSurveillanceSheet.getTa()).isEqualTo(DEFAULT_TA);
        assertThat(testSurveillanceSheet.getTemperature()).isEqualTo(DEFAULT_TEMPERATURE);
        assertThat(testSurveillanceSheet.getPulseRate()).isEqualTo(DEFAULT_PULSE_RATE);
        assertThat(testSurveillanceSheet.getRespiratoryFrequency()).isEqualTo(DEFAULT_RESPIRATORY_FREQUENCY);
        assertThat(testSurveillanceSheet.getRecolorationTime()).isEqualTo(DEFAULT_RECOLORATION_TIME);
        assertThat(testSurveillanceSheet.getGlasgow()).isEqualTo(DEFAULT_GLASGOW);
        assertThat(testSurveillanceSheet.getGravityClass()).isEqualTo(UPDATED_GRAVITY_CLASS);
        assertThat(testSurveillanceSheet.getHoraryDiuresis()).isEqualTo(DEFAULT_HORARY_DIURESIS);
        assertThat(testSurveillanceSheet.getSpo2()).isEqualTo(UPDATED_SPO_2);
        assertThat(testSurveillanceSheet.getTreatment()).isEqualTo(DEFAULT_TREATMENT);
        assertThat(testSurveillanceSheet.getHealthEvolution()).isEqualTo(DEFAULT_HEALTH_EVOLUTION);
        assertThat(testSurveillanceSheet.getSheetDate()).isEqualTo(UPDATED_SHEET_DATE);
    }

    @Test
    @Transactional
    void fullUpdateSurveillanceSheetWithPatch() throws Exception {
        // Initialize the database
        surveillanceSheetRepository.saveAndFlush(surveillanceSheet);

        int databaseSizeBeforeUpdate = surveillanceSheetRepository.findAll().size();

        // Update the surveillanceSheet using partial update
        SurveillanceSheet partialUpdatedSurveillanceSheet = new SurveillanceSheet();
        partialUpdatedSurveillanceSheet.setId(surveillanceSheet.getId());

        partialUpdatedSurveillanceSheet
            .ta(UPDATED_TA)
            .temperature(UPDATED_TEMPERATURE)
            .pulseRate(UPDATED_PULSE_RATE)
            .respiratoryFrequency(UPDATED_RESPIRATORY_FREQUENCY)
            .recolorationTime(UPDATED_RECOLORATION_TIME)
            .glasgow(UPDATED_GLASGOW)
            .gravityClass(UPDATED_GRAVITY_CLASS)
            .horaryDiuresis(UPDATED_HORARY_DIURESIS)
            .spo2(UPDATED_SPO_2)
            .treatment(UPDATED_TREATMENT)
            .healthEvolution(UPDATED_HEALTH_EVOLUTION)
            .sheetDate(UPDATED_SHEET_DATE);

        restSurveillanceSheetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSurveillanceSheet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSurveillanceSheet))
            )
            .andExpect(status().isOk());

        // Validate the SurveillanceSheet in the database
        List<SurveillanceSheet> surveillanceSheetList = surveillanceSheetRepository.findAll();
        assertThat(surveillanceSheetList).hasSize(databaseSizeBeforeUpdate);
        SurveillanceSheet testSurveillanceSheet = surveillanceSheetList.get(surveillanceSheetList.size() - 1);
        assertThat(testSurveillanceSheet.getTa()).isEqualTo(UPDATED_TA);
        assertThat(testSurveillanceSheet.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
        assertThat(testSurveillanceSheet.getPulseRate()).isEqualTo(UPDATED_PULSE_RATE);
        assertThat(testSurveillanceSheet.getRespiratoryFrequency()).isEqualTo(UPDATED_RESPIRATORY_FREQUENCY);
        assertThat(testSurveillanceSheet.getRecolorationTime()).isEqualTo(UPDATED_RECOLORATION_TIME);
        assertThat(testSurveillanceSheet.getGlasgow()).isEqualTo(UPDATED_GLASGOW);
        assertThat(testSurveillanceSheet.getGravityClass()).isEqualTo(UPDATED_GRAVITY_CLASS);
        assertThat(testSurveillanceSheet.getHoraryDiuresis()).isEqualTo(UPDATED_HORARY_DIURESIS);
        assertThat(testSurveillanceSheet.getSpo2()).isEqualTo(UPDATED_SPO_2);
        assertThat(testSurveillanceSheet.getTreatment()).isEqualTo(UPDATED_TREATMENT);
        assertThat(testSurveillanceSheet.getHealthEvolution()).isEqualTo(UPDATED_HEALTH_EVOLUTION);
        assertThat(testSurveillanceSheet.getSheetDate()).isEqualTo(UPDATED_SHEET_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingSurveillanceSheet() throws Exception {
        int databaseSizeBeforeUpdate = surveillanceSheetRepository.findAll().size();
        surveillanceSheet.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSurveillanceSheetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, surveillanceSheet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(surveillanceSheet))
            )
            .andExpect(status().isBadRequest());

        // Validate the SurveillanceSheet in the database
        List<SurveillanceSheet> surveillanceSheetList = surveillanceSheetRepository.findAll();
        assertThat(surveillanceSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSurveillanceSheet() throws Exception {
        int databaseSizeBeforeUpdate = surveillanceSheetRepository.findAll().size();
        surveillanceSheet.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSurveillanceSheetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(surveillanceSheet))
            )
            .andExpect(status().isBadRequest());

        // Validate the SurveillanceSheet in the database
        List<SurveillanceSheet> surveillanceSheetList = surveillanceSheetRepository.findAll();
        assertThat(surveillanceSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSurveillanceSheet() throws Exception {
        int databaseSizeBeforeUpdate = surveillanceSheetRepository.findAll().size();
        surveillanceSheet.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSurveillanceSheetMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(surveillanceSheet))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SurveillanceSheet in the database
        List<SurveillanceSheet> surveillanceSheetList = surveillanceSheetRepository.findAll();
        assertThat(surveillanceSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSurveillanceSheet() throws Exception {
        // Initialize the database
        surveillanceSheetRepository.saveAndFlush(surveillanceSheet);

        int databaseSizeBeforeDelete = surveillanceSheetRepository.findAll().size();

        // Delete the surveillanceSheet
        restSurveillanceSheetMockMvc
            .perform(delete(ENTITY_API_URL_ID, surveillanceSheet.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SurveillanceSheet> surveillanceSheetList = surveillanceSheetRepository.findAll();
        assertThat(surveillanceSheetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
