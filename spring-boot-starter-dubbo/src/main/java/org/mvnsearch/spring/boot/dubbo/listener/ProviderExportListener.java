package org.mvnsearch.spring.boot.dubbo.listener;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Exporter;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.listener.ExporterListenerAdapter;

import java.util.HashSet;
import java.util.Set;

/**
 * provider export listener
 *
 * @author linux_china
 */
@Activate
public class ProviderExportListener extends ExporterListenerAdapter {
    public static Set<Class> exportedInterfaces = new HashSet<Class>();

    public void exported(Exporter<?> exporter) throws RpcException {
        Class<?> anInterface = exporter.getInvoker().getInterface();
        exportedInterfaces.add(anInterface);
    }

    public void unexported(Exporter<?> exporter) {
        exportedInterfaces.remove(exporter.getInvoker().getInterface());
    }
}
