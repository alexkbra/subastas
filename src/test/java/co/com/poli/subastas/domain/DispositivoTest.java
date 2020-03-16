package co.com.poli.subastas.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.com.poli.subastas.web.rest.TestUtil;

public class DispositivoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dispositivo.class);
        Dispositivo dispositivo1 = new Dispositivo();
        dispositivo1.setId(1L);
        Dispositivo dispositivo2 = new Dispositivo();
        dispositivo2.setId(dispositivo1.getId());
        assertThat(dispositivo1).isEqualTo(dispositivo2);
        dispositivo2.setId(2L);
        assertThat(dispositivo1).isNotEqualTo(dispositivo2);
        dispositivo1.setId(null);
        assertThat(dispositivo1).isNotEqualTo(dispositivo2);
    }
}
