package org.mvnsearch.spring.boot.dubbo;

import org.mvnsearch.spring.boot.dubbo.listener.ConsumerInvokeStaticsFilter;
import org.mvnsearch.spring.boot.dubbo.listener.ProviderInvokeStaticsFilter;
import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * dubbo metrics
 *
 * @author linux_china
 */
@Component
public class DubboMetrics implements PublicMetrics {
    public Collection<Metric<?>> metrics() {
        List<Metric<?>> metrics = new LinkedList<Metric<?>>();
        if (!ConsumerInvokeStaticsFilter.statics.isEmpty()) {
            for (Map.Entry<String, AtomicLong> entry : ConsumerInvokeStaticsFilter.statics.entrySet()) {
                metrics.add(new Metric<Number>("dubbo." + entry.getKey(), entry.getValue().get()));
            }
        }
        if (!ProviderInvokeStaticsFilter.statics.isEmpty()) {
            for (Map.Entry<String, AtomicLong> entry : ProviderInvokeStaticsFilter.statics.entrySet()) {
                metrics.add(new Metric<Number>("dubbo." + entry.getKey(), entry.getValue().get()));
            }
        }
        return metrics;
    }
}
