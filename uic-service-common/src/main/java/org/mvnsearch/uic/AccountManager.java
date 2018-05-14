package org.mvnsearch.uic;

/**
 * account manager
 *
 * @author linux_china
 */
public interface AccountManager {

    public User findById(Long id);

    public void create(User user);
}
