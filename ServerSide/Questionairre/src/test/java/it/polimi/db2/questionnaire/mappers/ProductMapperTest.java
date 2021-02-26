package it.polimi.db2.questionnaire.mappers;

import java.io.IOException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.multipart.MultipartFile;

import it.polimi.db2.questionnaire.dto.requests.ProductRequest;
import it.polimi.db2.questionnaire.mappers.ProductMapper;
import it.polimi.db2.questionnaire.model.Product;

//@ContextConfiguration(classes = { SpringTestConfig.class })
public class ProductMapperTest {

	private ProductMapper productMapper;

	@BeforeEach
	public void setUp() {
		productMapper = ProductMapper.INSTANCE;
	}

	@Test
	@DisplayName("Test the mapping from an addProductRequest to a Product Model")
	public void toProductTest() {
	
		String name = "TestedProduct";
		byte[] photo = "1234".getBytes();
		ProductMapper productMapper= Mappers.getMapper(ProductMapper.class);
		MultipartFile file = new MockMultipartFile("image", photo);
		ProductRequest request = new ProductRequest("TestedProduct", file);
		Product product = Product.builder().name(name).photo(photo).build();
		try {
			Product mapped = productMapper.toProduct(request);
			Assertions.assertThat(mapped.getId()).isEqualTo(null);
		} catch (IOException e) {
			Assertions.fail(e.getMessage());
		}

	}

}
