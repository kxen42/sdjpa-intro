/**
 * AuthorEmbedded
 * Copyright (c) 2022, FastBridge Learning LLC
 * Created on January 19, 2022
 */
package guru.springframework.sdjpaintro.jpa.domain.composite;

import java.util.Objects;
import java.util.StringJoiner;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Demo @EmbeddedId
 */
@Entity
@Table(name="author_composite")
public class AuthorEmbedded {

    @EmbeddedId
    private NameId nameId;
    private String twitter;

    public AuthorEmbedded() {
    }

    public AuthorEmbedded(NameId nameId) {
        this.nameId = nameId;
    }

    public NameId getNameId() {
        return nameId;
    }

    public void setNameId(NameId nameId) {
        this.nameId = nameId;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorEmbedded that = (AuthorEmbedded) o;
        return nameId.equals(that.nameId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameId);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AuthorEmbedded.class.getSimpleName() + "[", "]")
            .add("nameId=" + nameId)
            .add("twitter='" + twitter + "'")
            .toString();
    }
}
