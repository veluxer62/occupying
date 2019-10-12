package com.kh.util

import com.fasterxml.jackson.annotation.JsonProperty
import kotlin.reflect.full.primaryConstructor

inline fun <reified T: Any> Map<*, *>.mapTo(custom: Map<String, () -> Any> = mapOf()): T =
        T::class.primaryConstructor!!.run {
            callBy(args = parameters.associate {
                val property = it.annotations
                        .find { x -> x.annotationClass == JsonProperty::class }
                        as? JsonProperty
                val fieldName = property?.value ?: it.name
                val value = this@mapTo[fieldName]
                it to (custom[fieldName]?.invoke() ?: this@mapTo[fieldName])
            })
        }