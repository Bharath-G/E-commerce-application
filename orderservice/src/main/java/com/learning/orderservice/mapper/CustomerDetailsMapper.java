package com.learning.orderservice.mapper;

import com.learning.orderservice.entity.CustomerDetails;
import com.learning.orderservice.model.CustomerDetailsRequest;
import org.springframework.stereotype.Component;

@Component
public class CustomerDetailsMapper {
    public CustomerDetails mapCustomerDetails(CustomerDetailsRequest customerDetailsRequest) {

        return CustomerDetails.builder()
                .customerId(customerDetailsRequest.getCustomerId())
                .firstName(customerDetailsRequest.getFirstName())
                .middleName(customerDetailsRequest.getMiddleName())
                .lastName(customerDetailsRequest.getLastName())
                .countryCode(customerDetailsRequest.getCountryCode())
                .phoneNumber(customerDetailsRequest.getPhoneNumber())
                .addressLine1(customerDetailsRequest.getAddressLine1())
                .addressLine2(customerDetailsRequest.getAddressLine2())
                .state(customerDetailsRequest.getState())
                .country(customerDetailsRequest.getCountry())
                .build();

    }
}
