package vallegrande.edu.pe.fideinexus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * FideiNexus Jenkins Demo Application
 * Sistema de Gestión Sacramental Parroquial
 * 
 * @author Duban Alexander Salas Napán
 * @version 1.0.0
 */
@SpringBootApplication
public class FideiNexusApplication {

    public static void main(String[] args) {
        SpringApplication.run(FideiNexusApplication.class, args);
        System.out.println("🚀 FideiNexus Demo Application Started!");
        System.out.println("📖 Swagger UI: http://localhost:8087/swagger-ui.html");
    }
}
