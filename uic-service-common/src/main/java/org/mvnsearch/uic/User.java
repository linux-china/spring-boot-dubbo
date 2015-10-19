package org.mvnsearch.uic;

import java.io.Serializable;
import java.util.Date;

/**
 * user
 *
 * @author linux_china
 */
public class User implements Serializable {
    private Long id;
    private String nick;
    private Date createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
