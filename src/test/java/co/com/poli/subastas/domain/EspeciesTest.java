package co.com.poli.subastas.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.com.poli.subastas.web.rest.TestUtil;

public class EspeciesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Especies.class);
        Especies especies1 = new Especies();
        especies1.setId(1L);
        Especies especies2 = new Especies();
        especies2.setId(especies1.getId());
        assertThat(especies1).isEqualTo(especies2);
        especies2.setId(2L);
        assertThat(especies1).isNotEqualTo(especies2);
        especies1.setId(null);
        assertThat(especies1).isNotEqualTo(especies2);
    }
}
