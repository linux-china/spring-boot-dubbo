package org.mvnsearch.uic;

import com.alibaba.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

/**
 * account manager implementation
 *
 * @author linux_china
 */
@Component
@DubboService(interfaceClass = AccountManager.class)
public class AccountManagerImpl implements AccountManager {
    public User findById(Long id) {
        User user = new User();
        user.setId(id);
        user.setNick("account:" + id);
        return user;
    }

    public void create(User user) {
        //
    }
}
