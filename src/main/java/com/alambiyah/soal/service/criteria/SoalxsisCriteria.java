package com.alambiyah.soal.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.alambiyah.soal.domain.Soalxsis} entity. This class is used
 * in {@link com.alambiyah.soal.web.rest.SoalxsisResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /soalxses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SoalxsisCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter description;

    private FloatFilter rating;

    private StringFilter image;

    private LocalDateFilter created_at;

    private LocalDateFilter updated_at;

    private Boolean distinct;

    public SoalxsisCriteria() {}

    public SoalxsisCriteria(SoalxsisCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.rating = other.rating == null ? null : other.rating.copy();
        this.image = other.image == null ? null : other.image.copy();
        this.created_at = other.created_at == null ? null : other.created_at.copy();
        this.updated_at = other.updated_at == null ? null : other.updated_at.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SoalxsisCriteria copy() {
        return new SoalxsisCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public StringFilter title() {
        if (title == null) {
            title = new StringFilter();
        }
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public FloatFilter getRating() {
        return rating;
    }

    public FloatFilter rating() {
        if (rating == null) {
            rating = new FloatFilter();
        }
        return rating;
    }

    public void setRating(FloatFilter rating) {
        this.rating = rating;
    }

    public StringFilter getImage() {
        return image;
    }

    public StringFilter image() {
        if (image == null) {
            image = new StringFilter();
        }
        return image;
    }

    public void setImage(StringFilter image) {
        this.image = image;
    }

    public LocalDateFilter getCreated_at() {
        return created_at;
    }

    public LocalDateFilter created_at() {
        if (created_at == null) {
            created_at = new LocalDateFilter();
        }
        return created_at;
    }

    public void setCreated_at(LocalDateFilter created_at) {
        this.created_at = created_at;
    }

    public LocalDateFilter getUpdated_at() {
        return updated_at;
    }

    public LocalDateFilter updated_at() {
        if (updated_at == null) {
            updated_at = new LocalDateFilter();
        }
        return updated_at;
    }

    public void setUpdated_at(LocalDateFilter updated_at) {
        this.updated_at = updated_at;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SoalxsisCriteria that = (SoalxsisCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(description, that.description) &&
            Objects.equals(rating, that.rating) &&
            Objects.equals(image, that.image) &&
            Objects.equals(created_at, that.created_at) &&
            Objects.equals(updated_at, that.updated_at) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, rating, image, created_at, updated_at, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SoalxsisCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (rating != null ? "rating=" + rating + ", " : "") +
            (image != null ? "image=" + image + ", " : "") +
            (created_at != null ? "created_at=" + created_at + ", " : "") +
            (updated_at != null ? "updated_at=" + updated_at + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
