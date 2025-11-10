package com.realestate;

import com.realestate.enums.OfferType;
import com.realestate.enums.PropertyConfig;
import com.realestate.model.*;
import com.realestate.repository.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(BrokerRepository brokerRepo,
                               CustomerRepository customerRepo,
                               PropertyRepository propertyRepo,
                               PasswordEncoder passwordEncoder) {
        return args -> {
            if (brokerRepo.count() > 0 || customerRepo.count() > 0 || propertyRepo.count() > 0) return;

            // Broker + cascaded User
            User brokerUser = new User();
            brokerUser.setEmail("broker@gmail.com");
            brokerUser.setPassword(passwordEncoder.encode("1111"));         // plain for now
            brokerUser.setRole("BROKER");           // IMPORTANT: exact string of your role
            brokerUser.setCity("Pune");
            brokerUser.setMobile("9999999999");

            Broker broker = new Broker();
            broker.setBroName("Acme Realty");
            broker.setUser(brokerUser);             // cascades with broker save
            broker = brokerRepo.save(broker);

            // Customer + cascaded User
            User custUser = new User();
            custUser.setEmail("cust@gmail.com");
            custUser.setPassword(passwordEncoder.encode("2222"));
            custUser.setRole("CUSTOMER");
            custUser.setCity("Pune");
            custUser.setMobile("8888888888");

            Customer customer = new Customer();
            customer.setCustName("John Customer");
            customer.setUser(custUser);             // cascades with customer save
            customer = customerRepo.save(customer);

            //Properties for brokers
            Property p1 = new Property();
            p1.setConfiguration(PropertyConfig.FLAT);
            p1.setOfferType(OfferType.SELL);
            p1.setOfferCost(5500000);
            p1.setAreaSqft(900);
            p1.setAddress("A-101, Blue Heights");
            p1.setStreet("Baner Road");
            p1.setCity("Pune");
            p1.setStatus(true);
            p1.setBroker(broker);
            // Add sample image URLs for the flat
            p1.getImageUrls().add("https://images.unsplash.com/photo-1560184897-ae75f418493e?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80");
            p1.getImageUrls().add("https://images.unsplash.com/photo-1484154218962-a197022b5858?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80");
            p1.getImageUrls().add("https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80");

            Property p2 = new Property();
            p2.setConfiguration(PropertyConfig.SHOP);
            p2.setOfferType(OfferType.RENT);
            p2.setOfferCost(45000);
            p2.setAreaSqft(300);
            p2.setAddress("12, Market Plaza");
            p2.setStreet("MG Road");
            p2.setCity("Pune");
            p2.setStatus(true);
            p2.setBroker(broker);
            // Add sample image URLs for the shop
            p2.getImageUrls().add("https://images.unsplash.com/photo-1441986300917-64674bd600d8?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80");
            p2.getImageUrls().add("https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80");

            propertyRepo.save(p1);
            propertyRepo.save(p2);

            System.out.println("Seeded: broker(broker@gmail.com/1111), customer(cust@gmail.com/2222), + 2 properties");
        };
    }
}
