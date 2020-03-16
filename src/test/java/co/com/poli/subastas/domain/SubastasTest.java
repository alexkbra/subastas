package co.com.poli.subastas.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.com.poli.subastas.web.rest.TestUtil;

public class SubastasTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subastas.class);
        Subastas subastas1 = new Subastas();
        subastas1.setId(1L);
        Subastas subastas2 = new Subastas();
        subastas2.setId(subastas1.getId());
        assertThat(subastas1).isEqualTo(subastas2);
        subastas2.setId(2L);
        assertThat(subastas1).isNotEqualTo(subastas2);
        subastas1.setId(null);
        assertThat(subastas1).isNotEqualTo(subastas2);
    }
}
