package co.com.poli.subastas.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.com.poli.subastas.web.rest.TestUtil;

public class RazasTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Razas.class);
        Razas razas1 = new Razas();
        razas1.setId(1L);
        Razas razas2 = new Razas();
        razas2.setId(razas1.getId());
        assertThat(razas1).isEqualTo(razas2);
        razas2.setId(2L);
        assertThat(razas1).isNotEqualTo(razas2);
        razas1.setId(null);
        assertThat(razas1).isNotEqualTo(razas2);
    }
}
