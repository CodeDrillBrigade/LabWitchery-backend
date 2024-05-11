package org.cdb.homunculus.controller

import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.cdb.homunculus.logic.ProcessLogic
import org.koin.ktor.ext.inject

fun Routing.processController() =
	route("/process") {
		val processLogic by inject<ProcessLogic>()

		post("/passwordReset") {
			val email = checkNotNull(call.parameters["email"]) { "Email must not be null" }
			processLogic.initiatePasswordResetProcess(email)
			call.respond("ok")
		}
	}