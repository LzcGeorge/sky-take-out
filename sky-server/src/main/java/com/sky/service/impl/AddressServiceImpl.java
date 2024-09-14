package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.mapper.AddressMapper;
import com.sky.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;


    public List<AddressBook> list(AddressBook addressBook) {
        return addressMapper.list(addressBook);
    }

    public void insert(AddressBook addressBook) {
        Long userId = BaseContext.getCurrentId();
        addressBook.setUserId(userId);
        addressBook.setIsDefault(0);
        addressMapper.insert(addressBook);
    }

    public void updateInfo(AddressBook addressBook) {
        addressMapper.updateInfo(addressBook);
    }

    @Override
    public void setAllUnDefault() {
        AddressBook addressBook = new AddressBook();
        Long userId = BaseContext.getCurrentId();
        addressBook.setUserId(userId);
        addressBook.setIsDefault(0);
        addressMapper.updateInfo(addressBook);
    }
}
