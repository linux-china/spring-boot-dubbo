package org.mvnsearch.spring.boot.dubbo.listener;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.listener.InvokerListenerAdapter;

import java.util.*;

/**
 * dubbo client invoker listener
 *
 * @author linux_china
 */
@Activate
public class ConsumerSubscribeListener extends InvokerListenerAdapter {
    public static Set<Class> subscribedInterfaces = new HashSet<Class>();
    public static Map<String, Set<String>> connections = new HashMap<String, Set<String>>();

    @Override
    public void referred(Invoker<?> invoker) throws RpcException {
        Class<?> subscribeInterface = invoker.getInterface();
        subscribedInterfaces.add(subscribeInterface);
        String subscribeInterfaceCanonicalName = subscribeInterface.getCanonicalName();
        if (!connections.containsKey(subscribeInterfaceCanonicalName)) {
            connections.put(subscribeInterfaceCanonicalName, new HashSet<String>());
        }
        connections.get(subscribeInterfaceCanonicalName).add(invoker.getUrl().toString());
    }

    @Override
    public void destroyed(Invoker<?> invoker) {
        Class<?> subscribedInterface = invoker.getInterface();
        subscribedInterfaces.remove(subscribedInterface);
        String subscribedInterfaceCanonicalName = subscribedInterface.getCanonicalName();
        if (connections.containsKey(subscribedInterfaceCanonicalName)) {
            connections.get(subscribedInterfaceCanonicalName).remove(invoker.getUrl().toString());
        }
    }
}
