package com.mischenkov.dtm;

import com.mischenkov.entity.Service;
import com.mischenkov.entity.Tariff;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ServiceBox implements Serializable {

    private static final long serialVersionUID = 7638861447654386442L;
    private final Service service;
    private final List<Tariff> tariffs;

    public ServiceBox(Service service, List<Tariff> tariffs) {
        this.service = service;
        this.tariffs = tariffs;
    }

    public Service getService() {
        return service;
    }

    public List<Tariff> getTariffs() {
        return tariffs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceBox that = (ServiceBox) o;
        return Objects.equals(service, that.service) &&
                Objects.equals(tariffs, that.tariffs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(service, tariffs);
    }

    @Override
    public String toString() {
        return "ServiceBox{" +
                "service=" + service +
                ", tariffs=" + tariffs +
                '}';
    }
}
