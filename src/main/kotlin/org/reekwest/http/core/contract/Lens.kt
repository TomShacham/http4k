package org.reekwest.http.core.contract

abstract class Lens<in IN, OUT : Any, FINAL>(val meta: Meta, private val spec: LensSpec<IN, OUT>) {
    operator fun invoke(target: IN): FINAL = try {
        convertIn(spec.get(target, meta.name)?.let { it.map { it?.let(spec.deserialize) } })
    } catch (e: Missing) {
        throw e
    } catch (e: Exception) {
        throw Invalid(meta)
    }

    abstract internal fun convertIn(o: List<OUT?>?): FINAL
    abstract internal fun convertOut(o: FINAL): List<OUT>

    @Suppress("UNCHECKED_CAST")
    operator fun <R : IN> invoke(target: R, value: FINAL): R =
        spec.set(target, meta.name, convertOut(value).map(spec.serialize)) as R
}