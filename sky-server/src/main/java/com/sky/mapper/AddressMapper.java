package com.sky.mapper;

import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AddressMapper {

    List<AddressBook> list(AddressBook addressBook);

    @Insert("insert into address_book (user_id,consignee,sex,phone,province_code,province_name,city_code,city_name,district_code,district_name,detail,label,is_default)"
            + "values (#{userId},#{consignee},#{sex},#{phone},#{provinceCode},#{provinceName},#{cityCode},#{cityName},#{districtCode},#{districtName},#{detail},#{label},#{isDefault})")
    void insert(AddressBook addressBook);

    void updateInfo(AddressBook addressBook);

    @Select("select * from address_book where id=#{id}")
    AddressBook getById(Long id);
}
