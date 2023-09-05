package com.xikra.staybooker.mapper;

import com.xikra.staybooker.domain.Hotel;
import com.xikra.staybooker.domain.Image;
import com.xikra.staybooker.domain.Room;
import com.xikra.staybooker.model.HotelDTO;
import com.xikra.staybooker.model.ImageDTO;
import com.xikra.staybooker.model.RoomDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Mapper
public interface HotelMapper {

    @Mapping(target = "street" , source = "address.street")
    @Mapping(target = "number" , source = "address.number")
    @Mapping(target = "city" , source = "address.city")
    @Mapping(target = "state" , source = "address.state")
    @Mapping(target = "zipcode" , source = "address.zipcode")
    HotelDTO toDTO(Hotel hotel);

    @Mapping(target = "address.street" , source = "street")
    @Mapping(target = "address.number" , source = "number")
    @Mapping(target = "address.city" , source = "city")
    @Mapping(target = "address.state" , source = "state")
    @Mapping(target = "address.zipcode" , source = "zipcode")
    Hotel toEntity(HotelDTO hotelDTO);



    default RoomDTO roomToRoomDTO(Room room) {
        return room != null ? RoomDTO.builder()
                .id(room.getId())
                .roomNumber(room.getRoomNumber())
                .roomType(room.getRoomType())
                .availability(room.getAvailability())
                .price(room.getPrice())
                .build() : null;
    }

    default Room roomDTOToRoom(RoomDTO roomDTO) {
        return roomDTO != null ? Room.builder()
                .id(roomDTO.getId())
                .roomNumber(roomDTO.getRoomNumber())
                .roomType(roomDTO.getRoomType())
                .availability(roomDTO.getAvailability())
                .price(roomDTO.getPrice())
                .build() : null;
    }

    default ImageDTO imageToImageDTO(Image image){
        return image != null ? ImageDTO.builder()
                .id(image.getId())
                .url(image.getUrl())
                .build(): null;
    }

    default Image imageDTOToImage(ImageDTO imageDTO){
        return imageDTO != null ? Image.builder()
                .id(imageDTO.getId())
                .url(imageDTO.getUrl())
                .build() : null;
    }


    default Set<RoomDTO> roomSetToRoomDTOSet(Set<Room> set) {
        if (set == null) {
            return null;
        }

        Set<RoomDTO> set1 = new LinkedHashSet<>(Math.max((int) (set.size() / .75f) + 1, 16));
        for (Room room : set) {
            set1.add(roomToRoomDTO(room));
        }

        return set1;
    }

    default Set<Room> roomDTOSetToRoomSet(Set<RoomDTO> set) {
        if (set == null) {
            return null;
        }

        Set<Room> set1 = new LinkedHashSet<>(Math.max((int) (set.size() / .75f) + 1, 16));
        for (RoomDTO roomDTO : set) {
            set1.add(roomDTOToRoom(roomDTO));
        }

        return set1;
    }

    default Set<ImageDTO> imageToImageDTOSet(Set<Image> set) {
        if (set == null) {
            return null;
        }

        Set<ImageDTO> set1 = new LinkedHashSet<>(Math.max((int) (set.size() / .75f) + 1, 16));
        for (Image image : set) {
            set1.add(imageToImageDTO(image));
        }

        return set1;
    }

    default Set<Image> ImageDTOToImageSet(Set<ImageDTO> set) {
        if (set == null) {
            return null;
        }

        Set<Image> set1 = new LinkedHashSet<>(Math.max((int) (set.size() / .75f) + 1, 16));
        for (ImageDTO imageDTO : set) {
            set1.add(imageDTOToImage(imageDTO));
        }

        return set1;
    }

    default Page<HotelDTO> toDTOPage(Page<Hotel> hotelPage){
        List<HotelDTO> hotelDTOList = new ArrayList<>();

        for(Hotel hotel: hotelPage){
            hotelDTOList.add(toDTO(hotel));
        }

        return new PageImpl<>(hotelDTOList);
    }

    default Page<Hotel> toEntityPage(Page<HotelDTO> hotelDTOPage){
        List<Hotel> hotelList = new ArrayList<>();

        for(HotelDTO hotelDTO : hotelDTOPage){
            hotelList.add(toEntity(hotelDTO));
        }

        return new PageImpl<>(hotelList);
    }
}
