package com.alambiyah.soal.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.alambiyah.soal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SoalxsisDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SoalxsisDTO.class);
        SoalxsisDTO soalxsisDTO1 = new SoalxsisDTO();
        soalxsisDTO1.setId(1L);
        SoalxsisDTO soalxsisDTO2 = new SoalxsisDTO();
        assertThat(soalxsisDTO1).isNotEqualTo(soalxsisDTO2);
        soalxsisDTO2.setId(soalxsisDTO1.getId());
        assertThat(soalxsisDTO1).isEqualTo(soalxsisDTO2);
        soalxsisDTO2.setId(2L);
        assertThat(soalxsisDTO1).isNotEqualTo(soalxsisDTO2);
        soalxsisDTO1.setId(null);
        assertThat(soalxsisDTO1).isNotEqualTo(soalxsisDTO2);
    }
}
