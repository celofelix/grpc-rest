package br.com.zup.edu

import com.google.protobuf.Any
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.grpc.protobuf.StatusProto
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.exceptions.HttpStatusException

@Controller(value = "/api/fretes")
class CalculadoraDeFretesController(private val fretesClient: FretesServiceGrpc.FretesServiceBlockingStub) {

    @Get
    fun calcula(@QueryValue cep: String): FreteResponse {

        val request = CalculaFreteRequest.newBuilder()
            .setCep(cep)
            .build()
//        try {
            return fretesClient.calculaFrete(request)
                .let {
                    FreteResponse(cep = it.cep, valor = it.valor)
                }
//        } catch (e: StatusRuntimeException) {
//
//            if (e.status.code == Status.Code.INVALID_ARGUMENT) {
//                throw HttpStatusException(HttpStatus.BAD_REQUEST, e.status.description)
//            }
//
//            if (e.status.code == Status.Code.PERMISSION_DENIED) {
//
//                val statusProto = StatusProto.fromThrowable(e)
//                    ?: throw HttpStatusException(HttpStatus.FORBIDDEN, e.status.description)
//
//                val anyDetails: Any = statusProto.detailsList.get(0)
//                val errorDetails = anyDetails.unpack(ErrorDetails::class.java)
//
//                throw HttpStatusException(HttpStatus.FORBIDDEN, "${errorDetails.code}: ${errorDetails.message}")
//            }
//
//            throw HttpStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.message)
//        }
    }
}