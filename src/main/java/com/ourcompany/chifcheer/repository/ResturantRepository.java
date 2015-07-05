package com.ourcompany.chifcheer.repository;

import com.ourcompany.chifcheer.domain.Resturant;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Resturant entity.
 */
public interface ResturantRepository extends MongoRepository<Resturant,String> {

}
