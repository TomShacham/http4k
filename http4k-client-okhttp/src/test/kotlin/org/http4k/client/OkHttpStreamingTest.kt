package org.http4k.client

import org.http4k.core.BodyMode
import org.http4k.server.SunHttp

class OkHttpStreamingTest : Http4kClientContract({ SunHttp(it) }, OkHttp(bodyMode = BodyMode.Stream),
    OkHttp(timeout, bodyMode = BodyMode.Stream))