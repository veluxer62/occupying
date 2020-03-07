package com.kh.testingHelper

import com.appmattus.kotlinfixture.kotlinFixture
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver

class FixtureExtension : ParameterResolver {
    override fun supportsParameter(
            parameterContext: ParameterContext?,
            extensionContext: ExtensionContext?): Boolean {
        return true
    }

    override fun resolveParameter(
            parameterContext: ParameterContext?,
            extensionContext: ExtensionContext?): Any {
        val type = parameterContext!!.parameter.type
        return kotlinFixture().create(type)!!
    }
}