package org.http4k.http.lens

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import org.http4k.http.core.ContentType.Companion.TEXT_PLAIN
import org.http4k.http.core.Request.Companion.get
import org.http4k.http.core.Response
import org.http4k.http.core.Status.Companion.OK
import org.http4k.http.core.with
import org.http4k.http.lens.Header.Common.CONTENT_TYPE

class HttpMessageTest {

    private val emptyRequest = get("")

    @Test
    fun `can bind many objects to a request`() {
        val populated = emptyRequest.with(
            Body.string(TEXT_PLAIN).required() to "the body",
            Header.int().required("intHeader") to 123,
            Query.boolean().required("boolean") to true
        )

        assertThat(populated.bodyString(), equalTo("the body"))
        assertThat(populated.header("intHeader"), equalTo("123"))
        assertThat(populated.query("boolean"), equalTo("true"))
        assertThat(CONTENT_TYPE(populated), equalTo(TEXT_PLAIN))
    }

    @Test
    fun `can bind many objects to a response`() {
        val populated = Response(OK).with(
            Body.string(TEXT_PLAIN).required() to "the body",
            Header.int().required("intHeader") to 123
        )

        assertThat(populated.bodyString(), equalTo("the body"))
        assertThat(populated.header("intHeader"), equalTo("123"))
        assertThat(CONTENT_TYPE(populated), equalTo(TEXT_PLAIN))
    }
}


