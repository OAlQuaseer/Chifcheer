package com.ourcompany.chifcheer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ourcompany.chifcheer.domain.Resturant;
import com.ourcompany.chifcheer.repository.ResturantRepository;
import com.ourcompany.chifcheer.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Resturant.
 */
@RestController
@RequestMapping("/api")
public class ResturantResource {

    private final Logger log = LoggerFactory.getLogger(ResturantResource.class);

    @Inject
    private ResturantRepository resturantRepository;

    /**
     * POST  /resturants -> Create a new resturant.
     */
    @RequestMapping(value = "/resturants",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Resturant resturant) throws URISyntaxException {
        log.debug("REST request to save Resturant : {}", resturant);
        if (resturant.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new resturant cannot already have an ID").build();
        }
        resturantRepository.save(resturant);
        return ResponseEntity.created(new URI("/api/resturants/" + resturant.getId())).build();
    }

    /**
     * PUT  /resturants -> Updates an existing resturant.
     */
    @RequestMapping(value = "/resturants",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Resturant resturant) throws URISyntaxException {
        log.debug("REST request to update Resturant : {}", resturant);
        if (resturant.getId() == null) {
            return create(resturant);
        }
        resturantRepository.save(resturant);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /resturants -> get all the resturants.
     */
    @RequestMapping(value = "/resturants",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Resturant>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Resturant> page = resturantRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/resturants", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /resturants/:id -> get the "id" resturant.
     */
    @RequestMapping(value = "/resturants/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Resturant> get(@PathVariable String id) {
        log.debug("REST request to get Resturant : {}", id);
        return Optional.ofNullable(resturantRepository.findOne(id))
            .map(resturant -> new ResponseEntity<>(
                resturant,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /resturants/:id -> delete the "id" resturant.
     */
    @RequestMapping(value = "/resturants/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable String id) {
        log.debug("REST request to delete Resturant : {}", id);
        resturantRepository.delete(id);
    }
}
