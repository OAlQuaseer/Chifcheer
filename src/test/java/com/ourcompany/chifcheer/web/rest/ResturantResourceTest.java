package com.ourcompany.chifcheer.web.rest;

import com.ourcompany.chifcheer.Application;
import com.ourcompany.chifcheer.domain.Resturant;
import com.ourcompany.chifcheer.repository.ResturantRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ResturantResource REST controller.
 *
 * @see ResturantResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ResturantResourceTest {

    private static final String DEFAULT_RES_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_RES_NAME = "UPDATED_TEXT";

    @Inject
    private ResturantRepository resturantRepository;

    private MockMvc restResturantMockMvc;

    private Resturant resturant;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ResturantResource resturantResource = new ResturantResource();
        ReflectionTestUtils.setField(resturantResource, "resturantRepository", resturantRepository);
        this.restResturantMockMvc = MockMvcBuilders.standaloneSetup(resturantResource).build();
    }

    @Before
    public void initTest() {
        resturantRepository.deleteAll();
        resturant = new Resturant();
        resturant.setResName(DEFAULT_RES_NAME);
    }

    @Test
    public void createResturant() throws Exception {
        int databaseSizeBeforeCreate = resturantRepository.findAll().size();

        // Create the Resturant
        restResturantMockMvc.perform(post("/api/resturants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resturant)))
                .andExpect(status().isCreated());

        // Validate the Resturant in the database
        List<Resturant> resturants = resturantRepository.findAll();
        assertThat(resturants).hasSize(databaseSizeBeforeCreate + 1);
        Resturant testResturant = resturants.get(resturants.size() - 1);
        assertThat(testResturant.getResName()).isEqualTo(DEFAULT_RES_NAME);
    }

    @Test
    public void checkResNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(resturantRepository.findAll()).hasSize(0);
        // set the field null
        resturant.setResName(null);

        // Create the Resturant, which fails.
        restResturantMockMvc.perform(post("/api/resturants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resturant)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Resturant> resturants = resturantRepository.findAll();
        assertThat(resturants).hasSize(0);
    }

    @Test
    public void getAllResturants() throws Exception {
        // Initialize the database
        resturantRepository.save(resturant);

        // Get all the resturants
        restResturantMockMvc.perform(get("/api/resturants"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(resturant.getId())))
                .andExpect(jsonPath("$.[*].resName").value(hasItem(DEFAULT_RES_NAME.toString())));
    }

    @Test
    public void getResturant() throws Exception {
        // Initialize the database
        resturantRepository.save(resturant);

        // Get the resturant
        restResturantMockMvc.perform(get("/api/resturants/{id}", resturant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(resturant.getId()))
            .andExpect(jsonPath("$.resName").value(DEFAULT_RES_NAME.toString()));
    }

    @Test
    public void getNonExistingResturant() throws Exception {
        // Get the resturant
        restResturantMockMvc.perform(get("/api/resturants/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateResturant() throws Exception {
        // Initialize the database
        resturantRepository.save(resturant);

		int databaseSizeBeforeUpdate = resturantRepository.findAll().size();

        // Update the resturant
        resturant.setResName(UPDATED_RES_NAME);
        restResturantMockMvc.perform(put("/api/resturants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resturant)))
                .andExpect(status().isOk());

        // Validate the Resturant in the database
        List<Resturant> resturants = resturantRepository.findAll();
        assertThat(resturants).hasSize(databaseSizeBeforeUpdate);
        Resturant testResturant = resturants.get(resturants.size() - 1);
        assertThat(testResturant.getResName()).isEqualTo(UPDATED_RES_NAME);
    }

    @Test
    public void deleteResturant() throws Exception {
        // Initialize the database
        resturantRepository.save(resturant);

		int databaseSizeBeforeDelete = resturantRepository.findAll().size();

        // Get the resturant
        restResturantMockMvc.perform(delete("/api/resturants/{id}", resturant.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Resturant> resturants = resturantRepository.findAll();
        assertThat(resturants).hasSize(databaseSizeBeforeDelete - 1);
    }
}
