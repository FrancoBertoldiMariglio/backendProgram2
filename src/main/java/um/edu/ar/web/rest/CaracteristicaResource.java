package um.edu.ar.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import um.edu.ar.repository.CaracteristicaRepository;
import um.edu.ar.service.CaracteristicaService;
import um.edu.ar.service.dto.CaracteristicaDTO;
import um.edu.ar.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link um.edu.ar.domain.Caracteristica}.
 */
@RestController
@RequestMapping("/api/caracteristicas")
public class CaracteristicaResource {

    private static final Logger LOG = LoggerFactory.getLogger(CaracteristicaResource.class);

    private static final String ENTITY_NAME = "caracteristica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CaracteristicaService caracteristicaService;

    private final CaracteristicaRepository caracteristicaRepository;

    public CaracteristicaResource(CaracteristicaService caracteristicaService, CaracteristicaRepository caracteristicaRepository) {
        this.caracteristicaService = caracteristicaService;
        this.caracteristicaRepository = caracteristicaRepository;
    }

    /**
     * {@code POST  /caracteristicas} : Create a new caracteristica.
     *
     * @param caracteristicaDTO the caracteristicaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new caracteristicaDTO, or with status {@code 400 (Bad Request)} if the caracteristica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CaracteristicaDTO> createCaracteristica(@Valid @RequestBody CaracteristicaDTO caracteristicaDTO)
        throws URISyntaxException {
        LOG.debug("REST request to create new Characteristic: {}", caracteristicaDTO);
        LOG.debug("Validating characteristic data");
        if (caracteristicaDTO.getId() != null) {
            LOG.error("Attempt to create characteristic with existing ID: {}", caracteristicaDTO.getId());
            throw new BadRequestAlertException("A new characteristic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LOG.debug("Saving new characteristic");
        caracteristicaDTO = caracteristicaService.save(caracteristicaDTO);
        LOG.info("Characteristic created successfully with ID: {}", caracteristicaDTO.getId());
        return ResponseEntity.created(new URI("/api/caracteristicas/" + caracteristicaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, caracteristicaDTO.getId().toString()))
            .body(caracteristicaDTO);
    }

    /**
     * {@code PUT  /caracteristicas/:id} : Updates an existing caracteristica.
     *
     * @param id the id of the caracteristicaDTO to save.
     * @param caracteristicaDTO the caracteristicaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated caracteristicaDTO,
     * or with status {@code 400 (Bad Request)} if the caracteristicaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the caracteristicaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CaracteristicaDTO> updateCaracteristica(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CaracteristicaDTO caracteristicaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Characteristic. ID: {}, Data: {}", id, caracteristicaDTO);
        LOG.debug("Validating characteristic ID");
        if (caracteristicaDTO.getId() == null) {
            LOG.error("Attempt to update characteristic without ID");
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, caracteristicaDTO.getId())) {
            LOG.error("Path ID ({}) does not match DTO ID ({})", id, caracteristicaDTO.getId());
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        LOG.debug("Checking if characteristic exists with ID: {}", id);
        if (!caracteristicaRepository.existsById(id)) {
            LOG.error("Characteristic not found with ID: {}", id);
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LOG.debug("Updating characteristic through service");
        caracteristicaDTO = caracteristicaService.update(caracteristicaDTO);
        LOG.info("Characteristic updated successfully with ID: {}", caracteristicaDTO.getId());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, caracteristicaDTO.getId().toString()))
            .body(caracteristicaDTO);
    }

    /**
     * {@code PATCH  /caracteristicas/:id} : Partial updates given fields of an existing caracteristica, field will ignore if it is null
     *
     * @param id the id of the caracteristicaDTO to save.
     * @param caracteristicaDTO the caracteristicaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated caracteristicaDTO,
     * or with status {@code 400 (Bad Request)} if the caracteristicaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the caracteristicaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the caracteristicaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CaracteristicaDTO> partialUpdateCaracteristica(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CaracteristicaDTO caracteristicaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partially update Characteristic. ID: {}, Data: {}", id, caracteristicaDTO);
        LOG.debug("Validating characteristic data");
        if (caracteristicaDTO.getId() == null) {
            LOG.error("Attempt to partially update without ID");
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, caracteristicaDTO.getId())) {
            LOG.error("Path ID ({}) does not match DTO ID ({})", id, caracteristicaDTO.getId());
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        LOG.debug("Checking if characteristic exists with ID: {}", id);
        if (!caracteristicaRepository.existsById(id)) {
            LOG.error("Characteristic not found with ID: {}", id);
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LOG.debug("Processing partial update");
        Optional<CaracteristicaDTO> result = caracteristicaService.partialUpdate(caracteristicaDTO);
        LOG.info("Partial update completed for characteristic ID: {}", id);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, caracteristicaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /caracteristicas} : get all the caracteristicas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of caracteristicas in body.
     */
    @GetMapping("")
    public List<CaracteristicaDTO> getAllCaracteristicas() {
        LOG.debug("REST request to get all Characteristics");
        LOG.debug("Retrieving all characteristics");
        List<CaracteristicaDTO> result = caracteristicaService.findAll();
        LOG.info("Retrieved {} characteristics", result.size());
        return result;
    }

    /**
     * {@code GET  /caracteristicas/:id} : get the "id" caracteristica.
     *
     * @param id the id of the caracteristicaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the caracteristicaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CaracteristicaDTO> getCaracteristica(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Characteristic with ID: {}", id);
        LOG.debug("Looking up characteristic in service");
        Optional<CaracteristicaDTO> caracteristicaDTO = caracteristicaService.findOne(id);
        if (caracteristicaDTO.isPresent()) {
            LOG.info("Characteristic found with ID: {}", id);
        } else {
            LOG.warn("Characteristic not found with ID: {}", id);
        }
        return ResponseUtil.wrapOrNotFound(caracteristicaDTO);
    }

    /**
     * {@code DELETE  /caracteristicas/:id} : delete the "id" caracteristica.
     *
     * @param id the id of the caracteristicaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCaracteristica(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Characteristic with ID: {}", id);
        LOG.debug("Starting deletion process");
        caracteristicaService.delete(id);
        LOG.info("Characteristic successfully deleted with ID: {}", id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
