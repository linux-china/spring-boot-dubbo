package org.mvnsearch.spring.boot.dubbo.listener;

import com.alibaba.dubbo.common.URL;
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
    /**
     * exported interfaces
     */
    public static Set<Class> exportedInterfaces = new HashSet<Class>();
    /**
     * exported urls
     */
    public static Set<URL> exportedUrl = new HashSet<URL>();

    public void exported(Exporter<?> exporter) throws RpcException {
        Class<?> anInterface = exporter.getInvoker().getInterface();
        exportedInterfaces.add(anInterface);
        URL url = exporter.getInvoker().getUrl();
        if (!url.getProtocol().equals("injvm")) {
            exportedUrl.add(url);
        }
    }

    public void unexported(Exporter<?> exporter) {
        exportedInterfaces.remove(exporter.getInvoker().getInterface());
        URL url = exporter.getInvoker().getUrl();
        if (!url.getProtocol().equals("injvm")) {
            exportedUrl.remove(url);
        }
    }
}
