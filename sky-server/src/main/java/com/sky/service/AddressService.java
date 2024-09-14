package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

public interface AddressService {


    List<AddressBook> list(AddressBook addressBook);

    void insert(AddressBook addressBook);

    void updateInfo(AddressBook addressBook);

    void setAllUnDefault();
}
