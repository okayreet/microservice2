package com.okayreet.customer;

import com.okayreet.clients.fraud.FraudCheckResponse;
import com.okayreet.clients.fraud.FraudClient;
import com.okayreet.clients.notification.NotificationClient;
import com.okayreet.clients.notification.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    private final FraudClient fraudClient;
    private final NotificationClient notificationClient;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        customerRepository.saveAndFlush(customer);
        // todo: check if email valid
        // todo: check if email not taken
      FraudCheckResponse fraudCheckResponse =  fraudClient.isFraudster(customer.getId());

      if(fraudCheckResponse.isFraudster()){
          throw new IllegalStateException("fraudster");
      }

        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, welcome to Okayreet World...",
                        customer.getFirstName())
        );
        notificationClient.sendNotification(notificationRequest);
    }
}
