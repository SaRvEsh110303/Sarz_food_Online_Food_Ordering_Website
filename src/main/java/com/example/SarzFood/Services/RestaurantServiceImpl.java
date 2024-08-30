package com.example.SarzFood.Services;

import com.example.SarzFood.DTOs.RestaurantDto;
import com.example.SarzFood.Entity.Address;
import com.example.SarzFood.Entity.Restaurant;
import com.example.SarzFood.Entity.User;
import com.example.SarzFood.Repositories.AddressRepo;
import com.example.SarzFood.Repositories.RestaurantRepository;
import com.example.SarzFood.Repositories.UserRepository;
import com.example.SarzFood.Request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService{
    @Autowired
    private RestaurantRepository restaurantRepo;
    @Autowired
    private AddressRepo addressRepo;
    @Autowired
    private UserRepository userRepo;
    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
        Address address = addressRepo.save(req.getAddress());
        Restaurant restaurant=new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setName(req.getName());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);
        return restaurantRepo.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception {
        Restaurant restaurantById = findRestaurantById(restaurantId);
        if(restaurantById.getCuisineType()!=null){
            restaurantById.setCuisineType(updatedRestaurant.getCuisineType());
        }
        if(restaurantById.getDescription()!=null){
            restaurantById.setDescription(updatedRestaurant.getDescription());
        }
        if(restaurantById.getName()!=null){
            restaurantById.setName(updatedRestaurant.getName());
        }
        if (updatedRestaurant.getAddress() != null) {
            Address updatedAddress = updatedRestaurant.getAddress();
            // Save or update the address before setting it to the restaurant
            Address savedAddress = addressRepo.save(updatedAddress);
            restaurantById.setAddress(savedAddress);
        }
        if(restaurantById.getOpeningHours()!=null){
            restaurantById.setOpeningHours(updatedRestaurant.getOpeningHours());
        }
        return restaurantRepo.save(restaurantById);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurantById = findRestaurantById(restaurantId);
        restaurantRepo.delete(restaurantById);
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepo.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepo.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> opt = restaurantRepo.findById(id);
        if(opt.isEmpty()) {
            throw new Exception("Restaurant not found with id " + id);
        }
        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant ownerId = restaurantRepo.findByOwnerId(userId);
        if(ownerId==null){
            throw new Exception("Restaurant not found with owner id");
        }
        return ownerId;
    }

    @Override
    public RestaurantDto addToFavourites(Long restaurantId, User user) throws Exception {
        Restaurant restaurantById = findRestaurantById(restaurantId);
        RestaurantDto restaurantDto=new RestaurantDto();
        restaurantDto.setDescription(restaurantById.getDescription());
        restaurantDto.setTitle(restaurantById.getName());
        restaurantDto.setImages(restaurantById.getImages());
//        if(user.getFavourites().contains(restaurantDto)){
//            user.getFavourites().remove(restaurantDto);
//        }
//        else
      user.getFavourites().add(restaurantDto);
        userRepo.save(user);
        return restaurantDto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurantById = findRestaurantById(id);
        restaurantById.setOpen(!restaurantById.isOpen());
        return restaurantRepo.save(restaurantById);
    }

    @Override
    public void RemoveAddToFavourites(Long restaurantId,User user) throws Exception {
        Restaurant restaurantById = findRestaurantById(restaurantId);
        RestaurantDto restaurantDto=new RestaurantDto();
        restaurantDto.setTitle(restaurantById.getName());
        restaurantDto.setDescription(restaurantById.getDescription());
        restaurantDto.setImages(restaurantById.getImages());
        List<RestaurantDto> favourites = user.getFavourites();
        favourites.remove(restaurantDto);
        userRepo.save(user);
    }



}
