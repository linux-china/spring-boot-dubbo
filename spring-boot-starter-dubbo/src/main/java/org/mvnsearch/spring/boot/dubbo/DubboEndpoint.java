package org.mvnsearch.spring.boot.dubbo;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;

/**
 * dubbo endpoint
 *
 * @author linux_china
 */
public class DubboEndpoint extends AbstractEndpoint {
    private DubboProperties dubboProperties;

    public DubboEndpoint(DubboProperties dubboProperties) {
        super("dubbo", false, false);
        this.dubboProperties = dubboProperties;
    }

    public Object invoke() {
        return "dubbo";
    }
}
