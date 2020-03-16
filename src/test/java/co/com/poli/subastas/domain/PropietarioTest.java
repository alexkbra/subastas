package co.com.poli.subastas.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.com.poli.subastas.web.rest.TestUtil;

public class PropietarioTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Propietario.class);
        Propietario propietario1 = new Propietario();
        propietario1.setId(1L);
        Propietario propietario2 = new Propietario();
        propietario2.setId(propietario1.getId());
        assertThat(propietario1).isEqualTo(propietario2);
        propietario2.setId(2L);
        assertThat(propietario1).isNotEqualTo(propietario2);
        propietario1.setId(null);
        assertThat(propietario1).isNotEqualTo(propietario2);
    }
}
