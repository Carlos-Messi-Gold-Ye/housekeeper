package com.housekeeper.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class HouseGoodsBase {

    @Transient
    private Long goodsId;
    @Column(name = "GOODS_NAME")
    private String goodsName;
    @Column(name = "GOODS_SIZE")
    private Integer goodsSize;
    @Transient
    private String desc;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsSize() {
        return goodsSize;
    }

    public void setGoodsSize(Integer goodsSize) {
        this.goodsSize = goodsSize;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
