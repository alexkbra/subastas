package co.com.poli.subastas.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.com.poli.subastas.web.rest.TestUtil;

public class LotesToAnimalesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LotesToAnimales.class);
        LotesToAnimales lotesToAnimales1 = new LotesToAnimales();
        lotesToAnimales1.setId(1L);
        LotesToAnimales lotesToAnimales2 = new LotesToAnimales();
        lotesToAnimales2.setId(lotesToAnimales1.getId());
        assertThat(lotesToAnimales1).isEqualTo(lotesToAnimales2);
        lotesToAnimales2.setId(2L);
        assertThat(lotesToAnimales1).isNotEqualTo(lotesToAnimales2);
        lotesToAnimales1.setId(null);
        assertThat(lotesToAnimales1).isNotEqualTo(lotesToAnimales2);
    }
}
