package org.cdb.labwitch.configuration

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.cdb.labwitch.exceptions.NotFoundException
import org.cdb.labwitch.exceptions.UnauthorizedException
import org.cdb.labwitch.models.security.StatusResponse
import java.io.IOException

fun Application.configureExceptions() {
    fun Exception.toErrorResponse(status: HttpStatusCode) =
        StatusResponse(
            false,
            message ?: this::class.qualifiedName ?: "Something wrong occurred",
            status.value,
        )

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            when (cause) {
                is AccessDeniedException -> call.respond(HttpStatusCode.Forbidden, cause.toErrorResponse(HttpStatusCode.Forbidden))
                is IllegalAccessException -> call.respond(HttpStatusCode.Forbidden, cause.toErrorResponse(HttpStatusCode.Forbidden))
                is IOException -> call.respond(HttpStatusCode.BadRequest, cause.toErrorResponse(HttpStatusCode.BadRequest))
                is IllegalArgumentException -> call.respond(HttpStatusCode.BadRequest, cause.toErrorResponse(HttpStatusCode.BadRequest))
                is UnauthorizedException -> call.respond(HttpStatusCode.Unauthorized, cause.toErrorResponse(HttpStatusCode.Unauthorized))
                is NotFoundException -> call.respond(HttpStatusCode.NotFound, cause.toErrorResponse(HttpStatusCode.NotFound))
                else ->
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        StatusResponse(false, cause.message ?: "Something went wrong", HttpStatusCode.InternalServerError.value),
                    )
            }
        }
    }
}
