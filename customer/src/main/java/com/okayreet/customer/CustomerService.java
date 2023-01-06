package com.okayreet.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        customerRepository.saveAndFlush(customer);
        // todo: check if email valid
        // todo: check if email not taken
      FraudCheckResponse fraudCheckResponse =  restTemplate.getForObject(
                "http://FRAUD/api/v1/fraud-check/{customerId}"
                , FraudCheckResponse.class,
                customer.getId()
        );

      if(fraudCheckResponse.isFrauduster()){
          throw new IllegalStateException("fraudster");
      }
    }
}
