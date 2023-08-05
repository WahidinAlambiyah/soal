package com.alambiyah.soal.service.impl;

import com.alambiyah.soal.domain.Soalxsis;
import com.alambiyah.soal.repository.SoalxsisRepository;
import com.alambiyah.soal.service.SoalxsisService;
import com.alambiyah.soal.service.dto.SoalxsisDTO;
import com.alambiyah.soal.service.mapper.SoalxsisMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Soalxsis}.
 */
@Service
@Transactional
public class SoalxsisServiceImpl implements SoalxsisService {

    private final Logger log = LoggerFactory.getLogger(SoalxsisServiceImpl.class);

    private final SoalxsisRepository soalxsisRepository;

    private final SoalxsisMapper soalxsisMapper;

    public SoalxsisServiceImpl(SoalxsisRepository soalxsisRepository, SoalxsisMapper soalxsisMapper) {
        this.soalxsisRepository = soalxsisRepository;
        this.soalxsisMapper = soalxsisMapper;
    }

    @Override
    public SoalxsisDTO save(SoalxsisDTO soalxsisDTO) {
        log.debug("Request to save Soalxsis : {}", soalxsisDTO);
        Soalxsis soalxsis = soalxsisMapper.toEntity(soalxsisDTO);
        soalxsis = soalxsisRepository.save(soalxsis);
        return soalxsisMapper.toDto(soalxsis);
    }

    @Override
    public SoalxsisDTO update(SoalxsisDTO soalxsisDTO) {
        log.debug("Request to update Soalxsis : {}", soalxsisDTO);
        Soalxsis soalxsis = soalxsisMapper.toEntity(soalxsisDTO);
        soalxsis = soalxsisRepository.save(soalxsis);
        return soalxsisMapper.toDto(soalxsis);
    }

    @Override
    public Optional<SoalxsisDTO> partialUpdate(SoalxsisDTO soalxsisDTO) {
        log.debug("Request to partially update Soalxsis : {}", soalxsisDTO);

        return soalxsisRepository
            .findById(soalxsisDTO.getId())
            .map(existingSoalxsis -> {
                soalxsisMapper.partialUpdate(existingSoalxsis, soalxsisDTO);

                return existingSoalxsis;
            })
            .map(soalxsisRepository::save)
            .map(soalxsisMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SoalxsisDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Soalxses");
        return soalxsisRepository.findAll(pageable).map(soalxsisMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SoalxsisDTO> findOne(Long id) {
        log.debug("Request to get Soalxsis : {}", id);
        return soalxsisRepository.findById(id).map(soalxsisMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Soalxsis : {}", id);
        soalxsisRepository.deleteById(id);
    }
}
