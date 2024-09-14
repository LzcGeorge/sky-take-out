package com.sky.controller.user;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.mapper.AddressMapper;
import com.sky.result.Result;
import com.sky.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user/addressBook")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/list")
    public Result<List<AddressBook>> list() {
        Long userId = BaseContext.getCurrentId();
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(userId);

        List<AddressBook> addressBookList = addressService.list(addressBook);
        return Result.success(addressBookList);
    }

    @PostMapping
    public Result addAddress(@RequestBody AddressBook addressBook) {
        addressService.insert(addressBook);
        return Result.success();
    }

    @PutMapping("/default")
    public Result setDefault(@RequestBody AddressBook addressBook) {
        addressService.setAllUnDefault();

        addressBook.setIsDefault(1);
        addressService.updateInfo(addressBook);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<AddressBook> getById(@PathVariable Long id) {
        AddressBook addressBook = new AddressBook();
        addressBook.setId(id);
        List<AddressBook> list = addressService.list(addressBook);
        AddressBook addressBook1 = list.get(0);

        return Result.success(addressBook1);
    }
}
