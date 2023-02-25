package com.kh.api.config

import com.kh.deprecatedOccupying.Korail
import com.kh.deprecatedOccupying.KorailProperties
import com.kh.deprecatedOccupying.WebClientWrapper
import com.kh.service.AlarmSender
import com.kh.service.BackgroundExecutor
import com.kh.util.SecretProperties
import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer


@Configuration
class AppConfig {

    @Bean
    fun korail() = Korail(WebClientWrapper(KorailProperties()))

    @Bean
    fun getJavaMailSender(): JavaMailSender {
        val secret = SecretProperties()
        return NaverMailSenderConfig.getSender(
                id = secret.email.id,
                pw = secret.email.pw
        )
    }

    @Bean
    fun alarmSender() = AlarmSender(getJavaMailSender())

    @Bean
    fun asyncService() = BackgroundExecutor(korail(), alarmSender())

}

@Configuration
@EnableAsync
class AsyncConfig : AsyncConfigurer {

    override fun getAsyncExecutor(): Executor? {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 5
        executor.maxPoolSize = 10
        executor.setQueueCapacity(5)
        executor.setRejectedExecutionHandler(ThreadPoolExecutor.CallerRunsPolicy())
        executor.initialize()
        return executor
    }

}

@Configuration
@EnableWebFlux
class CorsGlobalConfiguration : WebFluxConfigurer {
    override fun addCorsMappings(corsRegistry: CorsRegistry) {
        corsRegistry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("*")
            .allowedHeaders("*")
            .maxAge(3600)
    }
}
