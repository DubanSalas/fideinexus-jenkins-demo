package vallegrande.edu.pe.fideinexus;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FideiNexusApplicationTests {

    @Test
    void contextLoads() {
        // Verifica que el contexto de Spring se carga correctamente
    }

    @Test
    void applicationStarts() {
        // Verifica que la aplicación puede iniciar sin errores
        FideiNexusApplication.main(new String[]{});
    }
}
