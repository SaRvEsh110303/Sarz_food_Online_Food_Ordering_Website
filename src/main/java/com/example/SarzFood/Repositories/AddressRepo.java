package com.example.SarzFood.Repositories;

import com.example.SarzFood.Entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address,Long> {
}
