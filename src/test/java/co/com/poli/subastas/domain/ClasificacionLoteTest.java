package co.com.poli.subastas.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.com.poli.subastas.web.rest.TestUtil;

public class ClasificacionLoteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClasificacionLote.class);
        ClasificacionLote clasificacionLote1 = new ClasificacionLote();
        clasificacionLote1.setId(1L);
        ClasificacionLote clasificacionLote2 = new ClasificacionLote();
        clasificacionLote2.setId(clasificacionLote1.getId());
        assertThat(clasificacionLote1).isEqualTo(clasificacionLote2);
        clasificacionLote2.setId(2L);
        assertThat(clasificacionLote1).isNotEqualTo(clasificacionLote2);
        clasificacionLote1.setId(null);
        assertThat(clasificacionLote1).isNotEqualTo(clasificacionLote2);
    }
}
