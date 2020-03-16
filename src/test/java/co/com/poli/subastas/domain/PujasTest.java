package co.com.poli.subastas.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.com.poli.subastas.web.rest.TestUtil;

public class PujasTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pujas.class);
        Pujas pujas1 = new Pujas();
        pujas1.setId(1L);
        Pujas pujas2 = new Pujas();
        pujas2.setId(pujas1.getId());
        assertThat(pujas1).isEqualTo(pujas2);
        pujas2.setId(2L);
        assertThat(pujas1).isNotEqualTo(pujas2);
        pujas1.setId(null);
        assertThat(pujas1).isNotEqualTo(pujas2);
    }
}
