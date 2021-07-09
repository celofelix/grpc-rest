package br.com.zup.edu

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.hateoas.JsonError
import io.micronaut.http.server.exceptions.ExceptionHandler
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class HandlerEx : ExceptionHandler<StatusRuntimeException, HttpResponse<Any>> {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun handle(request: HttpRequest<*>?, exception: StatusRuntimeException?): HttpResponse<Any> {

        val (httpStatus, message) = when (exception?.status?.code) {
            Status.INVALID_ARGUMENT.code -> HttpStatus.BAD_REQUEST to exception?.status?.description
            Status.PERMISSION_DENIED.code -> HttpStatus.FORBIDDEN to exception?.status?.description
            Status.UNAVAILABLE.code -> HttpStatus.INTERNAL_SERVER_ERROR to "Não foi possível completar a requisição"

            else -> {
                logger.error("Erro inesperado ${exception?.javaClass?.name} ao processar requisição", exception)
                HttpStatus.INTERNAL_SERVER_ERROR to "Não foi possível completar a requisição"
            }
        }

        return HttpResponse
            .status<JsonError>(httpStatus)
            .body(JsonError(message))
    }
}