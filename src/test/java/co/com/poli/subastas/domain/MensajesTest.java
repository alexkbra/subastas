package co.com.poli.subastas.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.com.poli.subastas.web.rest.TestUtil;

public class MensajesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mensajes.class);
        Mensajes mensajes1 = new Mensajes();
        mensajes1.setId(1L);
        Mensajes mensajes2 = new Mensajes();
        mensajes2.setId(mensajes1.getId());
        assertThat(mensajes1).isEqualTo(mensajes2);
        mensajes2.setId(2L);
        assertThat(mensajes1).isNotEqualTo(mensajes2);
        mensajes1.setId(null);
        assertThat(mensajes1).isNotEqualTo(mensajes2);
    }
}
