package ma.omnishore.clients.api.feign.wrapper;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import ma.omnishore.clients.api.feign.SalesClient;
import ma.omnishore.clients.dto.SaleDto;

@Component
public class SalesClientWrapper {

	private static final Logger log = LoggerFactory.getLogger(SalesClientWrapper.class);

	@Autowired
	private SalesClient salesClient;

	@CircuitBreaker(name = "salesCB", fallbackMethod = "salesFallback")
	public List<SaleDto> getClientSales(Long clientId) {
		return salesClient.getClientSales(clientId);
	}

	public List<SaleDto> salesFallback(Long clientId, Throwable throwable) {
		log.error("Error when getting client Sales by clientId : {}", clientId, throwable);
		return new ArrayList<>();
	}
}
