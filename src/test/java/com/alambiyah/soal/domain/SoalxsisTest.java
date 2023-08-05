package com.alambiyah.soal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.alambiyah.soal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SoalxsisTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Soalxsis.class);
        Soalxsis soalxsis1 = new Soalxsis();
        soalxsis1.setId(1L);
        Soalxsis soalxsis2 = new Soalxsis();
        soalxsis2.setId(soalxsis1.getId());
        assertThat(soalxsis1).isEqualTo(soalxsis2);
        soalxsis2.setId(2L);
        assertThat(soalxsis1).isNotEqualTo(soalxsis2);
        soalxsis1.setId(null);
        assertThat(soalxsis1).isNotEqualTo(soalxsis2);
    }
}
