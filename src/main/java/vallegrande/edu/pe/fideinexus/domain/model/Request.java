package vallegrande.edu.pe.fideinexus.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad de dominio: Solicitud Sacramental
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("requests")
public class Request {

    @Id
    private Long id;
    
    private String tenantId;
    private Long churchId;
    private Long sacramentId;
    private Long applicantPersonId;
    private LocalDate requestDate;
    private String status;
    private String comments;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private Boolean active;

    /**
     * Estados posibles de una solicitud
     */
    public enum Status {
        PENDING("Pendiente"),
        IN_PROGRESS("En Progreso"),
        APPROVED("Aprobada"),
        REJECTED("Rechazada"),
        COMPLETED("Completada");

        private final String description;

        Status(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
