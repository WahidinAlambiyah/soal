package com.alambiyah.soal.service;

import com.alambiyah.soal.domain.*; // for static metamodels
import com.alambiyah.soal.domain.Soalxsis;
import com.alambiyah.soal.repository.SoalxsisRepository;
import com.alambiyah.soal.service.criteria.SoalxsisCriteria;
import com.alambiyah.soal.service.dto.SoalxsisDTO;
import com.alambiyah.soal.service.mapper.SoalxsisMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Soalxsis} entities in the database.
 * The main input is a {@link SoalxsisCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SoalxsisDTO} or a {@link Page} of {@link SoalxsisDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SoalxsisQueryService extends QueryService<Soalxsis> {

    private final Logger log = LoggerFactory.getLogger(SoalxsisQueryService.class);

    private final SoalxsisRepository soalxsisRepository;

    private final SoalxsisMapper soalxsisMapper;

    public SoalxsisQueryService(SoalxsisRepository soalxsisRepository, SoalxsisMapper soalxsisMapper) {
        this.soalxsisRepository = soalxsisRepository;
        this.soalxsisMapper = soalxsisMapper;
    }

    /**
     * Return a {@link List} of {@link SoalxsisDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SoalxsisDTO> findByCriteria(SoalxsisCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Soalxsis> specification = createSpecification(criteria);
        return soalxsisMapper.toDto(soalxsisRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SoalxsisDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SoalxsisDTO> findByCriteria(SoalxsisCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Soalxsis> specification = createSpecification(criteria);
        return soalxsisRepository.findAll(specification, page).map(soalxsisMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SoalxsisCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Soalxsis> specification = createSpecification(criteria);
        return soalxsisRepository.count(specification);
    }

    /**
     * Function to convert {@link SoalxsisCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Soalxsis> createSpecification(SoalxsisCriteria criteria) {
        Specification<Soalxsis> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Soalxsis_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Soalxsis_.title));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Soalxsis_.description));
            }
            if (criteria.getRating() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRating(), Soalxsis_.rating));
            }
            if (criteria.getImage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImage(), Soalxsis_.image));
            }
            if (criteria.getCreated_at() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated_at(), Soalxsis_.created_at));
            }
            if (criteria.getUpdated_at() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated_at(), Soalxsis_.updated_at));
            }
        }
        return specification;
    }
}
