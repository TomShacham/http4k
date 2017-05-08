package org.http4k.http.contract

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.http4k.http.core.Response
import org.http4k.http.core.Status
import org.junit.Test

class NoRendererTest {
    @Test
    fun `renders not found`() {
        assertThat(NoRenderer.notFound(), equalTo(Response(Status.Companion.NOT_FOUND)))
    }

    @Test
    fun `renders bad request`() {
        assertThat(NoRenderer.badRequest(listOf()), equalTo(Response(Status.Companion.BAD_REQUEST)))
    }

    @Test
    fun `renders description`() {
        assertThat(NoRenderer.description(Root, NoSecurity, listOf()), equalTo(Response(Status.Companion.OK)))
    }

}