package vallegrande.edu.pe.fideinexus.domain.port;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vallegrande.edu.pe.fideinexus.domain.model.Request;

/**
 * Puerto de salida: Repositorio de Solicitudes
 * Define las operaciones de persistencia sin depender de implementación específica
 */
public interface RequestRepository {

    /**
     * Buscar solicitud por ID
     */
    Mono<Request> findById(Long id);

    /**
     * Listar todas las solicitudes activas
     */
    Flux<Request> findAll();

    /**
     * Buscar solicitudes por tenant
     */
    Flux<Request> findByTenantId(String tenantId);

    /**
     * Buscar solicitudes por estado
     */
    Flux<Request> findByStatus(String status);

    /**
     * Guardar solicitud
     */
    Mono<Request> save(Request request);

    /**
     * Eliminar solicitud (borrado lógico)
     */
    Mono<Void> deleteById(Long id);

    /**
     * Contar solicitudes por tenant
     */
    Mono<Long> countByTenantId(String tenantId);
}
