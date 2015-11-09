package org.mvnsearch.spring.boot.dubbo.listener;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.listener.InvokerListenerAdapter;

import java.util.HashSet;
import java.util.Set;

/**
 * dubbo client invoker listener
 *
 * @author linux_china
 */
@Activate
public class ConsumerSubscribeListener extends InvokerListenerAdapter {
    public static Set<Class> subscribedInterfaces = new HashSet<Class>();

    @Override
    public void referred(Invoker<?> invoker) throws RpcException {
        subscribedInterfaces.add(invoker.getInterface());
    }

    @Override
    public void destroyed(Invoker<?> invoker) {
        subscribedInterfaces.remove(invoker.getInterface());
    }
}
