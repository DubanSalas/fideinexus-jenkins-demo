package vallegrande.edu.pe.fideinexus.domain.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para la entidad Request
 */
class RequestTest {

    @Test
    void testRequestCreation() {
        // Given
        Request request = Request.builder()
                .id(1L)
                .tenantId("TENANT001")
                .churchId(1L)
                .sacramentId(1L)
                .applicantPersonId(100L)
                .requestDate(LocalDate.now())
                .status("PENDING")
                .comments("Nueva solicitud de bautismo")
                .createdAt(LocalDateTime.now())
                .createdBy("admin@fideinexus.com")
                .active(true)
                .build();

        // Then
        assertNotNull(request);
        assertEquals(1L, request.getId());
        assertEquals("TENANT001", request.getTenantId());
        assertEquals("PENDING", request.getStatus());
        assertTrue(request.getActive());
    }

    @Test
    void testRequestStatusEnum() {
        // When
        Request.Status pending = Request.Status.PENDING;
        Request.Status approved = Request.Status.APPROVED;

        // Then
        assertEquals("Pendiente", pending.getDescription());
        assertEquals("Aprobada", approved.getDescription());
    }

    @Test
    void testRequestBuilder() {
        // When
        Request request = Request.builder()
                .tenantId("TENANT002")
                .status("IN_PROGRESS")
                .build();

        // Then
        assertNotNull(request);
        assertEquals("TENANT002", request.getTenantId());
        assertEquals("IN_PROGRESS", request.getStatus());
    }

    @Test
    void testRequestSettersAndGetters() {
        // Given
        Request request = new Request();

        // When
        request.setId(10L);
        request.setTenantId("TENANT003");
        request.setStatus("COMPLETED");
        request.setActive(false);

        // Then
        assertEquals(10L, request.getId());
        assertEquals("TENANT003", request.getTenantId());
        assertEquals("COMPLETED", request.getStatus());
        assertFalse(request.getActive());
    }
}
