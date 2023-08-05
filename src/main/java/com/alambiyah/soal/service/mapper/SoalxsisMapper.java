package com.alambiyah.soal.service.mapper;

import com.alambiyah.soal.domain.Soalxsis;
import com.alambiyah.soal.service.dto.SoalxsisDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Soalxsis} and its DTO {@link SoalxsisDTO}.
 */
@Mapper(componentModel = "spring")
public interface SoalxsisMapper extends EntityMapper<SoalxsisDTO, Soalxsis> {}
