package co.com.poli.subastas.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.com.poli.subastas.web.rest.TestUtil;

public class EstadoClienteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstadoCliente.class);
        EstadoCliente estadoCliente1 = new EstadoCliente();
        estadoCliente1.setId(1L);
        EstadoCliente estadoCliente2 = new EstadoCliente();
        estadoCliente2.setId(estadoCliente1.getId());
        assertThat(estadoCliente1).isEqualTo(estadoCliente2);
        estadoCliente2.setId(2L);
        assertThat(estadoCliente1).isNotEqualTo(estadoCliente2);
        estadoCliente1.setId(null);
        assertThat(estadoCliente1).isNotEqualTo(estadoCliente2);
    }
}
