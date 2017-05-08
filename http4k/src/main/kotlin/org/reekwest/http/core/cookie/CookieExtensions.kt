package org.http4k.http.core.cookie

import org.http4k.http.core.Request
import org.http4k.http.core.Response
import org.http4k.http.unquoted
import java.time.LocalDateTime
import java.time.ZoneOffset

fun Response.cookie(cookie: Cookie): Response = header("Set-Cookie", cookie.toString())

fun Response.removeCookie(name: String): Response = copy(headers = headers.filterNot { it.first.equals("Set-Cookie", true) && (it.second?.startsWith("$name=") ?: false) })

fun Response.replaceCookie(cookie: Cookie): Response = removeCookie(cookie.name).cookie(cookie)

fun Request.cookie(name: String, value: String): Request = replaceHeader("Cookie", cookies().plus(Cookie(name, value)).toCookieString())

internal fun String.toCookieList(): List<Cookie> = split("; ").filter { it.trim().isNotBlank() }.map { it.split("=").let { Cookie(it.elementAt(0), it.elementAtOrElse(1, { "\"\"" }).unquoted()) } }

fun Request.cookies(): List<Cookie> = header("Cookie")?.toCookieList() ?: listOf()

fun Request.cookie(name: String): Cookie? = cookies().filter { it.name == name }.sortedByDescending { it.path?.length ?: 0 }.firstOrNull()

private fun List<Cookie>.toCookieString() = map(Cookie::toString).joinToString("")

fun Response.cookies(): List<Cookie> = headerValues("set-cookie").filterNotNull().map { Cookie.parse(it) }.filterNotNull()

fun Cookie.invalidate(): Cookie = copy(value = "").maxAge(0).expires(LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.UTC))