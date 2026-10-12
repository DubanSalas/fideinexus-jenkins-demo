package vallegrande.edu.pe.fideinexus.infrastructure.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vallegrande.edu.pe.fideinexus.domain.model.Request;
import vallegrande.edu.pe.fideinexus.domain.port.RequestRepository;

/**
 * Controlador REST para gestión de Solicitudes Sacramentales
 * Adaptador de entrada: Expone endpoints HTTP
 */
@RestController
@RequestMapping("/api/v1/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestRepository requestRepository;

    /**
     * Listar todas las solicitudes
     * GET /api/v1/requests
     */
    @GetMapping
    public Flux<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    /**
     * Obtener solicitud por ID
     * GET /api/v1/requests/{id}
     */
    @GetMapping("/{id}")
    public Mono<Request> getRequestById(@PathVariable Long id) {
        return requestRepository.findById(id);
    }

    /**
     * Listar solicitudes por tenant
     * GET /api/v1/requests/tenant/{tenantId}
     */
    @GetMapping("/tenant/{tenantId}")
    public Flux<Request> getRequestsByTenant(@PathVariable String tenantId) {
        return requestRepository.findByTenantId(tenantId);
    }

    /**
     * Listar solicitudes por estado
     * GET /api/v1/requests/status/{status}
     */
    @GetMapping("/status/{status}")
    public Flux<Request> getRequestsByStatus(@PathVariable String status) {
        return requestRepository.findByStatus(status);
    }

    /**
     * Crear nueva solicitud
     * POST /api/v1/requests
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Request> createRequest(@RequestBody Request request) {
        return requestRepository.save(request);
    }

    /**
     * Actualizar solicitud existente
     * PUT /api/v1/requests/{id}
     */
    @PutMapping("/{id}")
    public Mono<Request> updateRequest(@PathVariable Long id, @RequestBody Request request) {
        request.setId(id);
        return requestRepository.save(request);
    }

    /**
     * Eliminar solicitud (borrado lógico)
     * DELETE /api/v1/requests/{id}
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteRequest(@PathVariable Long id) {
        return requestRepository.deleteById(id);
    }

    /**
     * Contar solicitudes por tenant
     * GET /api/v1/requests/tenant/{tenantId}/count
     */
    @GetMapping("/tenant/{tenantId}/count")
    public Mono<Long> countRequestsByTenant(@PathVariable String tenantId) {
        return requestRepository.countByTenantId(tenantId);
    }

    /**
     * Health check endpoint
     * GET /api/v1/requests/health
     */
    @GetMapping("/health")
    public Mono<String> health() {
        return Mono.just("✅ FideiNexus Requests Service is UP!");
    }
}
