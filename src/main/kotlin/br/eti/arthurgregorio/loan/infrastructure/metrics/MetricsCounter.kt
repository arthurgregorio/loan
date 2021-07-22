package br.eti.arthurgregorio.loan.infrastructure.metrics

import io.micrometer.core.instrument.MeterRegistry
import org.springframework.stereotype.Component

@Component
class MetricsCounter(
    private val meterRegistry: MeterRegistry
) {
    fun increase(metric: String) {
        meterRegistry.counter(metric).increment()
    }
}