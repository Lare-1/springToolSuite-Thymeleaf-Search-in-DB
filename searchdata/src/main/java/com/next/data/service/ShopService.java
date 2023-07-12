package com.next.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.next.data.model.Shop;
import com.next.data.repository.ShopRepository;

@Service
public class ShopService {
 
	@Autowired
    private ShopRepository repository;
 
    public List<Shop> getAllShops(){
        List<Shop> list =  (List<Shop>)repository.findAll();
        return list;
    }
 
     public List<Shop> getByKeyword(String keyword){
         return repository.findByKeyword(keyword);
     }
}