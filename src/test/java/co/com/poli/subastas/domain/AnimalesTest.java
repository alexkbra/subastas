package co.com.poli.subastas.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.com.poli.subastas.web.rest.TestUtil;

public class AnimalesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Animales.class);
        Animales animales1 = new Animales();
        animales1.setId(1L);
        Animales animales2 = new Animales();
        animales2.setId(animales1.getId());
        assertThat(animales1).isEqualTo(animales2);
        animales2.setId(2L);
        assertThat(animales1).isNotEqualTo(animales2);
        animales1.setId(null);
        assertThat(animales1).isNotEqualTo(animales2);
    }
}
