package co.com.poli.subastas.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.com.poli.subastas.web.rest.TestUtil;

public class PujadoresTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pujadores.class);
        Pujadores pujadores1 = new Pujadores();
        pujadores1.setId(1L);
        Pujadores pujadores2 = new Pujadores();
        pujadores2.setId(pujadores1.getId());
        assertThat(pujadores1).isEqualTo(pujadores2);
        pujadores2.setId(2L);
        assertThat(pujadores1).isNotEqualTo(pujadores2);
        pujadores1.setId(null);
        assertThat(pujadores1).isNotEqualTo(pujadores2);
    }
}
