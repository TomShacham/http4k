package org.http4k.core

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import junit.framework.TestCase.assertNull
import org.junit.Test

class UriTest {
    @Test
    fun `parsing a full uri`() {
        val value = "http://user:pass@host:1234/some/path?q1=v1&q2=v2#abc"
        val uri = Uri.of(value)
        assertThat(uri.scheme, equalTo("http"))
        assertThat(uri.authority, equalTo("user:pass@host:1234"))
        assertThat(uri.host, equalTo("host"))
        assertThat(uri.port, equalTo(1234))
        assertThat(uri.fragment, equalTo("abc"))
        assertThat(uri.path, equalTo("/some/path"))
        assertThat(uri.query, equalTo("q1=v1&q2=v2"))
        assertThat(uri.toString(), equalTo(value))
    }

    @Test
    fun `creating a full uri by hand`() {
        val uri = Uri.of("")
            .scheme("https")
            .userInfo("user:pass")
            .host("example.com")
            .port(1234)
            .fragment("bob")
            .path("/a/b/c")
            .query("foo=bar")

        assertThat(uri.toString(), equalTo("https://user:pass@example.com:1234/a/b/c?foo=bar#bob"))
    }

    @Test
    fun can_parse_minimal_uri() {
        val value = "http://host"
        val uri = Uri.of(value)
        assertThat(uri.scheme, equalTo("http"))
        assertThat(uri.host, equalTo("host"))
        assertThat(uri.authority, equalTo("host"))
        assertThat(uri.userInfo, equalTo(""))
        assertNull(uri.port)
        assertThat(uri.fragment, equalTo(""))
        assertThat(uri.path, equalTo(""))
        assertThat(uri.query, equalTo(""))
        assertThat(uri.toString(), equalTo(value))
    }

    @Test
    fun handles_empty_uri() {
        val uri = Uri.of("")
        assertThat(uri.toString(), equalTo(""))
    }

    @Test
    fun can_add_parameter() {
        val uri = Uri.of(value = "http://ignore").query("a", "b")
        assertThat(uri.toString(), equalTo("http://ignore?a=b"))
    }

    @Test
    fun parameters_can_be_defined_in_value(){
        assertThat(Uri.of("http://www.google.com?a=b"), equalTo(Uri.of("http://www.google.com").query("a", "b")))
    }
}