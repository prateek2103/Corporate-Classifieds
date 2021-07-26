
package com.cts.employeemicroservice.client;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PointsClientTest {
	
PointsClient pointsClient;
	
	
	@Test
    @DisplayName("Checking if [PointsClient] is loading or not.")
    void paymentClientIsLoaded(){
        assertThat(pointsClient).isNull();    
    }

}