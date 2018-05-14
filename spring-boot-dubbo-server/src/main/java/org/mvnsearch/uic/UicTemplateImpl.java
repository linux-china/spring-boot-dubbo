package org.mvnsearch.uic;

import com.alibaba.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

/**
 * uic template implementation
 *
 * @author linux_china
 */
@Component
@DubboService(interfaceClass = UicTemplate.class)
public class UicTemplateImpl implements UicTemplate {
    public User findById(Long id) {
        User user = new User();
        user.setId(id);
        user.setNick("nick:" + id);
        user.setCreatedAt(new Date());
        return user;
    }

    public Optional<Long> isEmailUnique(String email) {
        return Optional.of(1L);
    }
}
