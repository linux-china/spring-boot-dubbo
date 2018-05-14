package org.mvnsearch.uic;

import java.util.Optional;

/**
 * uic template
 *
 * @author linux_china
 */
public interface UicTemplate {

    public User findById(Long id);

    Optional<Long> isEmailUnique(String email);
}
