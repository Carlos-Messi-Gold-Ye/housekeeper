package com.housekeeper.entity;

import javax.persistence.Column;

public class HouseUserGoodsPk {

    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "GOODS_ID")
    private Long goodsId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HouseUserGoodsPk)) return false;

        HouseUserGoodsPk that = (HouseUserGoodsPk) o;

        if (getUserId() != null ? !getUserId().equals(that.getUserId()) : that.getUserId() != null) return false;
        return getGoodsId() != null ? getGoodsId().equals(that.getGoodsId()) : that.getGoodsId() == null;
    }

    @Override
    public int hashCode() {
        int result = getUserId() != null ? getUserId().hashCode() : 0;
        result = 31 * result + (getGoodsId() != null ? getGoodsId().hashCode() : 0);
        return result;
    }
}
