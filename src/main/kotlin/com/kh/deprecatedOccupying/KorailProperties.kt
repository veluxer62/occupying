package com.kh.deprecatedOccupying

import org.springframework.core.io.ClassPathResource
import org.yaml.snakeyaml.Yaml

class KorailProperties {
    var host: String
    var contextPath: String

    init {
        val yaml = Yaml()
        val resource = ClassPathResource("application.yml")
        val map = yaml.load<Map<String, Any>>(resource.inputStream)
        val korail = map?.get("korail") as Map<*, *>
        this.host = korail["host"] as String
        this.contextPath = korail["contextPath"] as String
    }
}