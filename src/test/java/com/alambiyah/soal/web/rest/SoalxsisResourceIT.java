package com.alambiyah.soal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.alambiyah.soal.IntegrationTest;
import com.alambiyah.soal.domain.Soalxsis;
import com.alambiyah.soal.repository.SoalxsisRepository;
import com.alambiyah.soal.service.criteria.SoalxsisCriteria;
import com.alambiyah.soal.service.dto.SoalxsisDTO;
import com.alambiyah.soal.service.mapper.SoalxsisMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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

/**
 * Integration tests for the {@link SoalxsisResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SoalxsisResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Float DEFAULT_RATING = 1F;
    private static final Float UPDATED_RATING = 2F;
    private static final Float SMALLER_RATING = 1F - 1F;

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CREATED_AT = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_UPDATED_AT = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/soalxses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SoalxsisRepository soalxsisRepository;

    @Autowired
    private SoalxsisMapper soalxsisMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSoalxsisMockMvc;

    private Soalxsis soalxsis;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Soalxsis createEntity(EntityManager em) {
        Soalxsis soalxsis = new Soalxsis()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .rating(DEFAULT_RATING)
            .image(DEFAULT_IMAGE)
            .created_at(DEFAULT_CREATED_AT)
            .updated_at(DEFAULT_UPDATED_AT);
        return soalxsis;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Soalxsis createUpdatedEntity(EntityManager em) {
        Soalxsis soalxsis = new Soalxsis()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .rating(UPDATED_RATING)
            .image(UPDATED_IMAGE)
            .created_at(UPDATED_CREATED_AT)
            .updated_at(UPDATED_UPDATED_AT);
        return soalxsis;
    }

    @BeforeEach
    public void initTest() {
        soalxsis = createEntity(em);
    }

    @Test
    @Transactional
    void createSoalxsis() throws Exception {
        int databaseSizeBeforeCreate = soalxsisRepository.findAll().size();
        // Create the Soalxsis
        SoalxsisDTO soalxsisDTO = soalxsisMapper.toDto(soalxsis);
        restSoalxsisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soalxsisDTO)))
            .andExpect(status().isCreated());

        // Validate the Soalxsis in the database
        List<Soalxsis> soalxsisList = soalxsisRepository.findAll();
        assertThat(soalxsisList).hasSize(databaseSizeBeforeCreate + 1);
        Soalxsis testSoalxsis = soalxsisList.get(soalxsisList.size() - 1);
        assertThat(testSoalxsis.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testSoalxsis.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSoalxsis.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testSoalxsis.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testSoalxsis.getCreated_at()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testSoalxsis.getUpdated_at()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createSoalxsisWithExistingId() throws Exception {
        // Create the Soalxsis with an existing ID
        soalxsis.setId(1L);
        SoalxsisDTO soalxsisDTO = soalxsisMapper.toDto(soalxsis);

        int databaseSizeBeforeCreate = soalxsisRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSoalxsisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soalxsisDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Soalxsis in the database
        List<Soalxsis> soalxsisList = soalxsisRepository.findAll();
        assertThat(soalxsisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = soalxsisRepository.findAll().size();
        // set the field null
        soalxsis.setTitle(null);

        // Create the Soalxsis, which fails.
        SoalxsisDTO soalxsisDTO = soalxsisMapper.toDto(soalxsis);

        restSoalxsisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soalxsisDTO)))
            .andExpect(status().isBadRequest());

        List<Soalxsis> soalxsisList = soalxsisRepository.findAll();
        assertThat(soalxsisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSoalxses() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList
        restSoalxsisMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(soalxsis.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.doubleValue())))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.[*].created_at").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updated_at").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getSoalxsis() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get the soalxsis
        restSoalxsisMockMvc
            .perform(get(ENTITY_API_URL_ID, soalxsis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(soalxsis.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING.doubleValue()))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE))
            .andExpect(jsonPath("$.created_at").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updated_at").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getSoalxsesByIdFiltering() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        Long id = soalxsis.getId();

        defaultSoalxsisShouldBeFound("id.equals=" + id);
        defaultSoalxsisShouldNotBeFound("id.notEquals=" + id);

        defaultSoalxsisShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSoalxsisShouldNotBeFound("id.greaterThan=" + id);

        defaultSoalxsisShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSoalxsisShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSoalxsesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where title equals to DEFAULT_TITLE
        defaultSoalxsisShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the soalxsisList where title equals to UPDATED_TITLE
        defaultSoalxsisShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllSoalxsesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultSoalxsisShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the soalxsisList where title equals to UPDATED_TITLE
        defaultSoalxsisShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllSoalxsesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where title is not null
        defaultSoalxsisShouldBeFound("title.specified=true");

        // Get all the soalxsisList where title is null
        defaultSoalxsisShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllSoalxsesByTitleContainsSomething() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where title contains DEFAULT_TITLE
        defaultSoalxsisShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the soalxsisList where title contains UPDATED_TITLE
        defaultSoalxsisShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllSoalxsesByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where title does not contain DEFAULT_TITLE
        defaultSoalxsisShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the soalxsisList where title does not contain UPDATED_TITLE
        defaultSoalxsisShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllSoalxsesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where description equals to DEFAULT_DESCRIPTION
        defaultSoalxsisShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the soalxsisList where description equals to UPDATED_DESCRIPTION
        defaultSoalxsisShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllSoalxsesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultSoalxsisShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the soalxsisList where description equals to UPDATED_DESCRIPTION
        defaultSoalxsisShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllSoalxsesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where description is not null
        defaultSoalxsisShouldBeFound("description.specified=true");

        // Get all the soalxsisList where description is null
        defaultSoalxsisShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllSoalxsesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where description contains DEFAULT_DESCRIPTION
        defaultSoalxsisShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the soalxsisList where description contains UPDATED_DESCRIPTION
        defaultSoalxsisShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllSoalxsesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where description does not contain DEFAULT_DESCRIPTION
        defaultSoalxsisShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the soalxsisList where description does not contain UPDATED_DESCRIPTION
        defaultSoalxsisShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllSoalxsesByRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where rating equals to DEFAULT_RATING
        defaultSoalxsisShouldBeFound("rating.equals=" + DEFAULT_RATING);

        // Get all the soalxsisList where rating equals to UPDATED_RATING
        defaultSoalxsisShouldNotBeFound("rating.equals=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    void getAllSoalxsesByRatingIsInShouldWork() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where rating in DEFAULT_RATING or UPDATED_RATING
        defaultSoalxsisShouldBeFound("rating.in=" + DEFAULT_RATING + "," + UPDATED_RATING);

        // Get all the soalxsisList where rating equals to UPDATED_RATING
        defaultSoalxsisShouldNotBeFound("rating.in=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    void getAllSoalxsesByRatingIsNullOrNotNull() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where rating is not null
        defaultSoalxsisShouldBeFound("rating.specified=true");

        // Get all the soalxsisList where rating is null
        defaultSoalxsisShouldNotBeFound("rating.specified=false");
    }

    @Test
    @Transactional
    void getAllSoalxsesByRatingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where rating is greater than or equal to DEFAULT_RATING
        defaultSoalxsisShouldBeFound("rating.greaterThanOrEqual=" + DEFAULT_RATING);

        // Get all the soalxsisList where rating is greater than or equal to UPDATED_RATING
        defaultSoalxsisShouldNotBeFound("rating.greaterThanOrEqual=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    void getAllSoalxsesByRatingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where rating is less than or equal to DEFAULT_RATING
        defaultSoalxsisShouldBeFound("rating.lessThanOrEqual=" + DEFAULT_RATING);

        // Get all the soalxsisList where rating is less than or equal to SMALLER_RATING
        defaultSoalxsisShouldNotBeFound("rating.lessThanOrEqual=" + SMALLER_RATING);
    }

    @Test
    @Transactional
    void getAllSoalxsesByRatingIsLessThanSomething() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where rating is less than DEFAULT_RATING
        defaultSoalxsisShouldNotBeFound("rating.lessThan=" + DEFAULT_RATING);

        // Get all the soalxsisList where rating is less than UPDATED_RATING
        defaultSoalxsisShouldBeFound("rating.lessThan=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    void getAllSoalxsesByRatingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where rating is greater than DEFAULT_RATING
        defaultSoalxsisShouldNotBeFound("rating.greaterThan=" + DEFAULT_RATING);

        // Get all the soalxsisList where rating is greater than SMALLER_RATING
        defaultSoalxsisShouldBeFound("rating.greaterThan=" + SMALLER_RATING);
    }

    @Test
    @Transactional
    void getAllSoalxsesByImageIsEqualToSomething() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where image equals to DEFAULT_IMAGE
        defaultSoalxsisShouldBeFound("image.equals=" + DEFAULT_IMAGE);

        // Get all the soalxsisList where image equals to UPDATED_IMAGE
        defaultSoalxsisShouldNotBeFound("image.equals=" + UPDATED_IMAGE);
    }

    @Test
    @Transactional
    void getAllSoalxsesByImageIsInShouldWork() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where image in DEFAULT_IMAGE or UPDATED_IMAGE
        defaultSoalxsisShouldBeFound("image.in=" + DEFAULT_IMAGE + "," + UPDATED_IMAGE);

        // Get all the soalxsisList where image equals to UPDATED_IMAGE
        defaultSoalxsisShouldNotBeFound("image.in=" + UPDATED_IMAGE);
    }

    @Test
    @Transactional
    void getAllSoalxsesByImageIsNullOrNotNull() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where image is not null
        defaultSoalxsisShouldBeFound("image.specified=true");

        // Get all the soalxsisList where image is null
        defaultSoalxsisShouldNotBeFound("image.specified=false");
    }

    @Test
    @Transactional
    void getAllSoalxsesByImageContainsSomething() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where image contains DEFAULT_IMAGE
        defaultSoalxsisShouldBeFound("image.contains=" + DEFAULT_IMAGE);

        // Get all the soalxsisList where image contains UPDATED_IMAGE
        defaultSoalxsisShouldNotBeFound("image.contains=" + UPDATED_IMAGE);
    }

    @Test
    @Transactional
    void getAllSoalxsesByImageNotContainsSomething() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where image does not contain DEFAULT_IMAGE
        defaultSoalxsisShouldNotBeFound("image.doesNotContain=" + DEFAULT_IMAGE);

        // Get all the soalxsisList where image does not contain UPDATED_IMAGE
        defaultSoalxsisShouldBeFound("image.doesNotContain=" + UPDATED_IMAGE);
    }

    @Test
    @Transactional
    void getAllSoalxsesByCreated_atIsEqualToSomething() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where created_at equals to DEFAULT_CREATED_AT
        defaultSoalxsisShouldBeFound("created_at.equals=" + DEFAULT_CREATED_AT);

        // Get all the soalxsisList where created_at equals to UPDATED_CREATED_AT
        defaultSoalxsisShouldNotBeFound("created_at.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllSoalxsesByCreated_atIsInShouldWork() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where created_at in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultSoalxsisShouldBeFound("created_at.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the soalxsisList where created_at equals to UPDATED_CREATED_AT
        defaultSoalxsisShouldNotBeFound("created_at.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllSoalxsesByCreated_atIsNullOrNotNull() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where created_at is not null
        defaultSoalxsisShouldBeFound("created_at.specified=true");

        // Get all the soalxsisList where created_at is null
        defaultSoalxsisShouldNotBeFound("created_at.specified=false");
    }

    @Test
    @Transactional
    void getAllSoalxsesByCreated_atIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where created_at is greater than or equal to DEFAULT_CREATED_AT
        defaultSoalxsisShouldBeFound("created_at.greaterThanOrEqual=" + DEFAULT_CREATED_AT);

        // Get all the soalxsisList where created_at is greater than or equal to UPDATED_CREATED_AT
        defaultSoalxsisShouldNotBeFound("created_at.greaterThanOrEqual=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllSoalxsesByCreated_atIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where created_at is less than or equal to DEFAULT_CREATED_AT
        defaultSoalxsisShouldBeFound("created_at.lessThanOrEqual=" + DEFAULT_CREATED_AT);

        // Get all the soalxsisList where created_at is less than or equal to SMALLER_CREATED_AT
        defaultSoalxsisShouldNotBeFound("created_at.lessThanOrEqual=" + SMALLER_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllSoalxsesByCreated_atIsLessThanSomething() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where created_at is less than DEFAULT_CREATED_AT
        defaultSoalxsisShouldNotBeFound("created_at.lessThan=" + DEFAULT_CREATED_AT);

        // Get all the soalxsisList where created_at is less than UPDATED_CREATED_AT
        defaultSoalxsisShouldBeFound("created_at.lessThan=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllSoalxsesByCreated_atIsGreaterThanSomething() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where created_at is greater than DEFAULT_CREATED_AT
        defaultSoalxsisShouldNotBeFound("created_at.greaterThan=" + DEFAULT_CREATED_AT);

        // Get all the soalxsisList where created_at is greater than SMALLER_CREATED_AT
        defaultSoalxsisShouldBeFound("created_at.greaterThan=" + SMALLER_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllSoalxsesByUpdated_atIsEqualToSomething() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where updated_at equals to DEFAULT_UPDATED_AT
        defaultSoalxsisShouldBeFound("updated_at.equals=" + DEFAULT_UPDATED_AT);

        // Get all the soalxsisList where updated_at equals to UPDATED_UPDATED_AT
        defaultSoalxsisShouldNotBeFound("updated_at.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllSoalxsesByUpdated_atIsInShouldWork() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where updated_at in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultSoalxsisShouldBeFound("updated_at.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the soalxsisList where updated_at equals to UPDATED_UPDATED_AT
        defaultSoalxsisShouldNotBeFound("updated_at.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllSoalxsesByUpdated_atIsNullOrNotNull() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where updated_at is not null
        defaultSoalxsisShouldBeFound("updated_at.specified=true");

        // Get all the soalxsisList where updated_at is null
        defaultSoalxsisShouldNotBeFound("updated_at.specified=false");
    }

    @Test
    @Transactional
    void getAllSoalxsesByUpdated_atIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where updated_at is greater than or equal to DEFAULT_UPDATED_AT
        defaultSoalxsisShouldBeFound("updated_at.greaterThanOrEqual=" + DEFAULT_UPDATED_AT);

        // Get all the soalxsisList where updated_at is greater than or equal to UPDATED_UPDATED_AT
        defaultSoalxsisShouldNotBeFound("updated_at.greaterThanOrEqual=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllSoalxsesByUpdated_atIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where updated_at is less than or equal to DEFAULT_UPDATED_AT
        defaultSoalxsisShouldBeFound("updated_at.lessThanOrEqual=" + DEFAULT_UPDATED_AT);

        // Get all the soalxsisList where updated_at is less than or equal to SMALLER_UPDATED_AT
        defaultSoalxsisShouldNotBeFound("updated_at.lessThanOrEqual=" + SMALLER_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllSoalxsesByUpdated_atIsLessThanSomething() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where updated_at is less than DEFAULT_UPDATED_AT
        defaultSoalxsisShouldNotBeFound("updated_at.lessThan=" + DEFAULT_UPDATED_AT);

        // Get all the soalxsisList where updated_at is less than UPDATED_UPDATED_AT
        defaultSoalxsisShouldBeFound("updated_at.lessThan=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllSoalxsesByUpdated_atIsGreaterThanSomething() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        // Get all the soalxsisList where updated_at is greater than DEFAULT_UPDATED_AT
        defaultSoalxsisShouldNotBeFound("updated_at.greaterThan=" + DEFAULT_UPDATED_AT);

        // Get all the soalxsisList where updated_at is greater than SMALLER_UPDATED_AT
        defaultSoalxsisShouldBeFound("updated_at.greaterThan=" + SMALLER_UPDATED_AT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSoalxsisShouldBeFound(String filter) throws Exception {
        restSoalxsisMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(soalxsis.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.doubleValue())))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.[*].created_at").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updated_at").value(hasItem(DEFAULT_UPDATED_AT.toString())));

        // Check, that the count call also returns 1
        restSoalxsisMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSoalxsisShouldNotBeFound(String filter) throws Exception {
        restSoalxsisMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSoalxsisMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSoalxsis() throws Exception {
        // Get the soalxsis
        restSoalxsisMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSoalxsis() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        int databaseSizeBeforeUpdate = soalxsisRepository.findAll().size();

        // Update the soalxsis
        Soalxsis updatedSoalxsis = soalxsisRepository.findById(soalxsis.getId()).get();
        // Disconnect from session so that the updates on updatedSoalxsis are not directly saved in db
        em.detach(updatedSoalxsis);
        updatedSoalxsis
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .rating(UPDATED_RATING)
            .image(UPDATED_IMAGE)
            .created_at(UPDATED_CREATED_AT)
            .updated_at(UPDATED_UPDATED_AT);
        SoalxsisDTO soalxsisDTO = soalxsisMapper.toDto(updatedSoalxsis);

        restSoalxsisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, soalxsisDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(soalxsisDTO))
            )
            .andExpect(status().isOk());

        // Validate the Soalxsis in the database
        List<Soalxsis> soalxsisList = soalxsisRepository.findAll();
        assertThat(soalxsisList).hasSize(databaseSizeBeforeUpdate);
        Soalxsis testSoalxsis = soalxsisList.get(soalxsisList.size() - 1);
        assertThat(testSoalxsis.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSoalxsis.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSoalxsis.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testSoalxsis.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testSoalxsis.getCreated_at()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testSoalxsis.getUpdated_at()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingSoalxsis() throws Exception {
        int databaseSizeBeforeUpdate = soalxsisRepository.findAll().size();
        soalxsis.setId(count.incrementAndGet());

        // Create the Soalxsis
        SoalxsisDTO soalxsisDTO = soalxsisMapper.toDto(soalxsis);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoalxsisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, soalxsisDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(soalxsisDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soalxsis in the database
        List<Soalxsis> soalxsisList = soalxsisRepository.findAll();
        assertThat(soalxsisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSoalxsis() throws Exception {
        int databaseSizeBeforeUpdate = soalxsisRepository.findAll().size();
        soalxsis.setId(count.incrementAndGet());

        // Create the Soalxsis
        SoalxsisDTO soalxsisDTO = soalxsisMapper.toDto(soalxsis);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoalxsisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(soalxsisDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soalxsis in the database
        List<Soalxsis> soalxsisList = soalxsisRepository.findAll();
        assertThat(soalxsisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSoalxsis() throws Exception {
        int databaseSizeBeforeUpdate = soalxsisRepository.findAll().size();
        soalxsis.setId(count.incrementAndGet());

        // Create the Soalxsis
        SoalxsisDTO soalxsisDTO = soalxsisMapper.toDto(soalxsis);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoalxsisMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soalxsisDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Soalxsis in the database
        List<Soalxsis> soalxsisList = soalxsisRepository.findAll();
        assertThat(soalxsisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSoalxsisWithPatch() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        int databaseSizeBeforeUpdate = soalxsisRepository.findAll().size();

        // Update the soalxsis using partial update
        Soalxsis partialUpdatedSoalxsis = new Soalxsis();
        partialUpdatedSoalxsis.setId(soalxsis.getId());

        partialUpdatedSoalxsis.title(UPDATED_TITLE).rating(UPDATED_RATING).image(UPDATED_IMAGE).updated_at(UPDATED_UPDATED_AT);

        restSoalxsisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSoalxsis.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSoalxsis))
            )
            .andExpect(status().isOk());

        // Validate the Soalxsis in the database
        List<Soalxsis> soalxsisList = soalxsisRepository.findAll();
        assertThat(soalxsisList).hasSize(databaseSizeBeforeUpdate);
        Soalxsis testSoalxsis = soalxsisList.get(soalxsisList.size() - 1);
        assertThat(testSoalxsis.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSoalxsis.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSoalxsis.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testSoalxsis.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testSoalxsis.getCreated_at()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testSoalxsis.getUpdated_at()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateSoalxsisWithPatch() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        int databaseSizeBeforeUpdate = soalxsisRepository.findAll().size();

        // Update the soalxsis using partial update
        Soalxsis partialUpdatedSoalxsis = new Soalxsis();
        partialUpdatedSoalxsis.setId(soalxsis.getId());

        partialUpdatedSoalxsis
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .rating(UPDATED_RATING)
            .image(UPDATED_IMAGE)
            .created_at(UPDATED_CREATED_AT)
            .updated_at(UPDATED_UPDATED_AT);

        restSoalxsisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSoalxsis.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSoalxsis))
            )
            .andExpect(status().isOk());

        // Validate the Soalxsis in the database
        List<Soalxsis> soalxsisList = soalxsisRepository.findAll();
        assertThat(soalxsisList).hasSize(databaseSizeBeforeUpdate);
        Soalxsis testSoalxsis = soalxsisList.get(soalxsisList.size() - 1);
        assertThat(testSoalxsis.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSoalxsis.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSoalxsis.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testSoalxsis.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testSoalxsis.getCreated_at()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testSoalxsis.getUpdated_at()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingSoalxsis() throws Exception {
        int databaseSizeBeforeUpdate = soalxsisRepository.findAll().size();
        soalxsis.setId(count.incrementAndGet());

        // Create the Soalxsis
        SoalxsisDTO soalxsisDTO = soalxsisMapper.toDto(soalxsis);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoalxsisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, soalxsisDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(soalxsisDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soalxsis in the database
        List<Soalxsis> soalxsisList = soalxsisRepository.findAll();
        assertThat(soalxsisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSoalxsis() throws Exception {
        int databaseSizeBeforeUpdate = soalxsisRepository.findAll().size();
        soalxsis.setId(count.incrementAndGet());

        // Create the Soalxsis
        SoalxsisDTO soalxsisDTO = soalxsisMapper.toDto(soalxsis);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoalxsisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(soalxsisDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soalxsis in the database
        List<Soalxsis> soalxsisList = soalxsisRepository.findAll();
        assertThat(soalxsisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSoalxsis() throws Exception {
        int databaseSizeBeforeUpdate = soalxsisRepository.findAll().size();
        soalxsis.setId(count.incrementAndGet());

        // Create the Soalxsis
        SoalxsisDTO soalxsisDTO = soalxsisMapper.toDto(soalxsis);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoalxsisMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(soalxsisDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Soalxsis in the database
        List<Soalxsis> soalxsisList = soalxsisRepository.findAll();
        assertThat(soalxsisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSoalxsis() throws Exception {
        // Initialize the database
        soalxsisRepository.saveAndFlush(soalxsis);

        int databaseSizeBeforeDelete = soalxsisRepository.findAll().size();

        // Delete the soalxsis
        restSoalxsisMockMvc
            .perform(delete(ENTITY_API_URL_ID, soalxsis.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Soalxsis> soalxsisList = soalxsisRepository.findAll();
        assertThat(soalxsisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
