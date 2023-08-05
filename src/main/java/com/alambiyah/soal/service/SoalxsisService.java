package com.alambiyah.soal.service;

import com.alambiyah.soal.service.dto.SoalxsisDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.alambiyah.soal.domain.Soalxsis}.
 */
public interface SoalxsisService {
    /**
     * Save a soalxsis.
     *
     * @param soalxsisDTO the entity to save.
     * @return the persisted entity.
     */
    SoalxsisDTO save(SoalxsisDTO soalxsisDTO);

    /**
     * Updates a soalxsis.
     *
     * @param soalxsisDTO the entity to update.
     * @return the persisted entity.
     */
    SoalxsisDTO update(SoalxsisDTO soalxsisDTO);

    /**
     * Partially updates a soalxsis.
     *
     * @param soalxsisDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SoalxsisDTO> partialUpdate(SoalxsisDTO soalxsisDTO);

    /**
     * Get all the soalxses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SoalxsisDTO> findAll(Pageable pageable);

    /**
     * Get the "id" soalxsis.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SoalxsisDTO> findOne(Long id);

    /**
     * Delete the "id" soalxsis.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
