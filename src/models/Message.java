package models;

import javax.persistence.*;

/**
 * Created by neek on 05.05.2016.
 */
@Entity
@Table(name = "message", schema = "test")
public class Message {
    private int id;
    private String text;
    private String hash;
    private int checked;
    private int fromId;
    private int toId;
    private int isSecret;

    @Basic
    @Column(name = "checked")
    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Basic
    @Column(name = "hash")
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Basic
    @Column(name = "from_id")
    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    @Basic
    @Column(name = "to_id")
    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message that = (Message) o;

        if (id != that.id) return false;
        if (checked != that.checked) return false;
        if (fromId != that.fromId) return false;
        if (toId != that.toId) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        if (hash != null ? !hash.equals(that.hash) : that.hash != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (hash != null ? hash.hashCode() : 0);
        result = 31 * result + (checked);
        result = 31 * result + fromId;
        result = 31 * result + toId;
        return result;
    }

    @Override
    public String toString() {
        Utils utils = new Utils();
        return "Message{" +
                "[from=" + utils.getUser(fromId).getLogin() +
                "]{to=" + utils.getUser(toId).getLogin() +
                "}{hash=" + hash +

                "][checked=" + (checked==1?true:false) +
                "]}";
    }

    @Basic
    @Column(name = "isSecret")
    public int getIsSecret() {
        return isSecret;
    }

    public void setIsSecret(int isSecret) {
        this.isSecret = isSecret;
    }
}
