package it.polimi.db2.questionnaire.mappers;

import java.io.IOException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import it.polimi.db2.questionnaire.dto.requests.ProductRequest;
import it.polimi.db2.questionnaire.model.Product;

public class ProductMapperTest {

	private ProductMapperImpl productMapper;

	@BeforeEach
	public void setUp() {
		productMapper = new ProductMapperImpl(null);
	}

	@Test
	@DisplayName("Test the mapping from a ProductRequest to a Product Model")
	public void toProductTest() {
	
		String name = "TestedProduct";
		byte[] photo = "1234".getBytes();
		MultipartFile file = new MockMultipartFile("image", photo);
		ProductRequest request = new ProductRequest("TestedProduct", file);
		try {
			Product mapped = productMapper.toProduct(request);
			Assertions.assertThat(mapped.getId()).isEqualTo(null);
			Assertions.assertThat(mapped.getName()).isEqualTo(name);
			Assertions.assertThat(mapped.getPhoto()).isEqualTo(photo);
		} catch (IOException e) {
			Assertions.fail(e.getMessage());
		}

	}

}
