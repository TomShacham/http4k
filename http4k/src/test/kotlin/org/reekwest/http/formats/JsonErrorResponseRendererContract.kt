package org.http4k.http.formats

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import org.http4k.http.lens.Invalid
import org.http4k.http.lens.Meta
import org.http4k.http.lens.Missing
import org.http4k.http.lens.ParamMeta.StringParam

abstract class JsonErrorResponseRendererContract<ROOT : NODE, NODE: Any>(val j: Json<ROOT, NODE>){

    @Test
    fun `can build 400`() {
        val response = JsonErrorResponseRenderer(j).badRequest(listOf(
            Missing(Meta(true, "location1", StringParam, "name1")),
            Invalid(Meta(false, "location2", StringParam, "name2"))))
        assertThat(response.bodyString(),
            equalTo("""{"message":"Missing/invalid parameters","params":[{"name":"name1","type":"location1","required":true,"reason":"Missing"},{"name":"name2","type":"location2","required":false,"reason":"Invalid"}]}"""))
    }

    @Test
    fun `can build 404`() {
        val response = JsonErrorResponseRenderer(j).notFound()
        assertThat(response.bodyString(),
            equalTo("""{"message":"No route found on this path. Have you used the correct HTTP verb?"}"""))
    }
}