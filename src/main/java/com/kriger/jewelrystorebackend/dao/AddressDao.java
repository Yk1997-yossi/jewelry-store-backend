package com.kriger.jewelrystorebackend.dao;

import javax.sql.DataSource;

public class AddressDao {
    private final DataSource dataSource;

    public AddressDao(DataSource dataSource){
        this.dataSource = dataSource;
    }
}
