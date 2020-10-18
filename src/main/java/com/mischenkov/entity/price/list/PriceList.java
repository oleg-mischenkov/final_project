package com.mischenkov.entity.price.list;

import com.mischenkov.dtm.ServiceBox;

import java.util.List;

public interface PriceList {

    byte[] deriveData(List<ServiceBox> serviceBoxList);

}
