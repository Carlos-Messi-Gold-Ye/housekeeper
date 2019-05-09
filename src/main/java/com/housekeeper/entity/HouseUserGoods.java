package com.housekeeper.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "HOUSE_USER_GOODS")
public class HouseUserGoods {

    @EmbeddedId
    private HouseUserGoodsPk pk = new HouseUserGoodsPk();
    @Column(name = "total")
    private Integer total;

    public HouseUserGoodsPk getPk() {
        return pk;
    }

    public void setPk(HouseUserGoodsPk pk) {
        this.pk = pk;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HouseUserGoods)) return false;

        HouseUserGoods that = (HouseUserGoods) o;

        return getPk().equals(that.getPk());
    }

    @Override
    public int hashCode() {
        return getPk().hashCode();
    }
}
