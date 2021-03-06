package com.ender.tablettop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ender.tablettop.domain.Boots;
import com.ender.tablettop.service.BootsService;
import com.ender.tablettop.web.rest.errors.BadRequestAlertException;
import com.ender.tablettop.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Boots.
 */
@RestController
@RequestMapping("/api")
public class BootsResource {

    private final Logger log = LoggerFactory.getLogger(BootsResource.class);

    private static final String ENTITY_NAME = "boots";

    private final BootsService bootsService;

    public BootsResource(BootsService bootsService) {
        this.bootsService = bootsService;
    }

    /**
     * POST  /boots : Create a new boots.
     *
     * @param boots the boots to create
     * @return the ResponseEntity with status 201 (Created) and with body the new boots, or with status 400 (Bad Request) if the boots has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/boots")
    @Timed
    public ResponseEntity<Boots> createBoots(@RequestBody Boots boots) throws URISyntaxException {
        log.debug("REST request to save Boots : {}", boots);
        if (boots.getId() != null) {
            throw new BadRequestAlertException("A new boots cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Boots result = bootsService.save(boots);
        return ResponseEntity.created(new URI("/api/boots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /boots : Updates an existing boots.
     *
     * @param boots the boots to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated boots,
     * or with status 400 (Bad Request) if the boots is not valid,
     * or with status 500 (Internal Server Error) if the boots couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/boots")
    @Timed
    public ResponseEntity<Boots> updateBoots(@RequestBody Boots boots) throws URISyntaxException {
        log.debug("REST request to update Boots : {}", boots);
        if (boots.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Boots result = bootsService.save(boots);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, boots.getId().toString()))
            .body(result);
    }

    /**
     * GET  /boots : get all the boots.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of boots in body
     */
    @GetMapping("/boots")
    @Timed
    public List<Boots> getAllBoots(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Boots");
        return bootsService.findAll();
    }

    /**
     * GET  /boots/:id : get the "id" boots.
     *
     * @param id the id of the boots to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the boots, or with status 404 (Not Found)
     */
    @GetMapping("/boots/{id}")
    @Timed
    public ResponseEntity<Boots> getBoots(@PathVariable Long id) {
        log.debug("REST request to get Boots : {}", id);
        Optional<Boots> boots = bootsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(boots);
    }

    /**
     * DELETE  /boots/:id : delete the "id" boots.
     *
     * @param id the id of the boots to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/boots/{id}")
    @Timed
    public ResponseEntity<Void> deleteBoots(@PathVariable Long id) {
        log.debug("REST request to delete Boots : {}", id);
        bootsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
