package com.kh.util

import org.springframework.core.io.ClassPathResource
import org.yaml.snakeyaml.Yaml

class SecretProperties {

    inner class Korail(
            val id: String,
            val pw: String
    )

    inner class Mail(
            val id: String,
            val pw: String
    )

    var korail: Korail
    var email: Mail

    init {
        val yaml = Yaml()
        val resource = ClassPathResource("secret.yml")
        val map = yaml.load<Map<String, Any>>(resource.inputStream)
        val korail = map["korail"] as Map<*, *>
        val email = map["email"] as Map<*, *>

        this.korail = Korail(korail["id"].toString(), korail["pw"].toString())
        this.email = Mail(email["id"].toString(), email["pw"].toString())
    }
}