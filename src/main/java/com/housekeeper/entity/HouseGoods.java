package com.housekeeper.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author yezy
 * @since 2019/3/7
 */
@Entity
@Table(name = "HOUSE_GOODS")
public class HouseGoods extends HouseGoodsBase {

    @Id
    @Column(name = "GOODS_ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long goodsId;

    @Override
    public Long getGoodsId() {
        return goodsId;
    }

    @Override
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HouseGoods)) return false;

        HouseGoods that = (HouseGoods) o;

        return getGoodsId() != null ? getGoodsId().equals(that.getGoodsId()) : that.getGoodsId() == null;
    }

    @Override
    public int hashCode() {
        return getGoodsId() != null ? getGoodsId().hashCode() : 0;
    }
}
