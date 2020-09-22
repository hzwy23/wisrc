package com.wisrc.merchandise.entity;

import java.util.Objects;

public class MskuInfoEpitaphEntity {
    private String id;
    private String epitaph;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEpitaph() {
        return epitaph;
    }

    public void setEpitaph(String epitaph) {
        this.epitaph = epitaph;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MskuInfoEpitaphEntity that = (MskuInfoEpitaphEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(epitaph, that.epitaph);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, epitaph);
    }
}
