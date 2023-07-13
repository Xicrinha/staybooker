package com.xikra.staybooker.mapper;

import com.xikra.staybooker.domain.Adress;
import com.xikra.staybooker.model.AdressDTO;
import org.mapstruct.Mapper;

@Mapper
public interface AdressMapper {

    AdressDTO adressToAdressDTO(Adress adress);

    Adress adressDTOToAdress(AdressDTO adressDTO);

}
