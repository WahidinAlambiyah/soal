package com.alambiyah.soal.repository;

import com.alambiyah.soal.domain.Soalxsis;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Soalxsis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SoalxsisRepository extends JpaRepository<Soalxsis, Long>, JpaSpecificationExecutor<Soalxsis> {}
