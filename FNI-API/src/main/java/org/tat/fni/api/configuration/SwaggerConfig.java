package org.tat.fni.api.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)//
				.select()//
				.apis(RequestHandlerSelectors.any())//
				.paths(Predicates.not(PathSelectors.regex("/error")))//
				.build()//
				.apiInfo(metadata())//
				.useDefaultResponseMessages(false)//
				.securitySchemes(new ArrayList<>(Arrays.asList(new ApiKey("Bearer %token", "Authorization", "Header"))))//
				.tags(new Tag("users", "Operations about users"))//
				.tags(new Tag("Agents", "Operations about Agent")).tags(new Tag("Banks", "Operations about Bank"))
				.tags(new Tag("Branches", "Operations about Branch"))
				.tags(new Tag("Countrys", "Operations about Country"))
				.tags(new Tag("Customers", "Operations about Customer"))
				.tags(new Tag("Farmers", "Operation about Farmer")).tags(new Tag("Grades", "Operations about Grade"))
				.tags(new Tag("Group Criticalillness", "Operations about Group Criticalillness"))
				.tags(new Tag("Group Health", "Operations about Group Health"))
				.tags(new Tag("groupLife", "Operations about Group Life"))
				.tags(new Tag("Hospitals", "Operations about Hospital"))
				.tags(new Tag("Individual Criticalillness", "Operations about Individual criticalillness"))
				.tags(new Tag("Individual Health", "Operations about Individual Health"))
				.tags(new Tag("Micro Health", "Operations about Micro Health"))
				.tags(new Tag("Occupations", "Operations about Occupation"))
				.tags(new Tag("Organizations", "Operations about Organization"))
				.tags(new Tag("Payment Types", "Operations about Payment Type"))
				.tags(new Tag("Personal Accident", "Operations about Personal Accident"))
				.tags(new Tag("Provinces", "Operations about Province"))
				.tags(new Tag("Policy Data", "Operations about PolicyData"))
				.tags(new Tag("Endowment Life", "Operations about Endowment Life"))
				.tags(new Tag("Relationships", "Operations about Relationship"))
				.tags(new Tag("Religions", "Operations about Religion"))
				.tags(new Tag("Qualifications", "Operations about Qualification"))
				.tags(new Tag("SaleMan", "Operations about SaleMan"))
				.tags(new Tag("SalePoints", "Operations about Salepoint"))
				.tags(new Tag("Schools", "Operations about School"))
				.tags(new Tag("Short-Term Life Insurance", "Operations about Short-Term Life Insurance"))
				.tags(new Tag("Snake Bite", "Operations about Snake Bite"))
				.tags(new Tag("Sport Man", "Operations about Sport Man"))
				.tags(new Tag("Student-Life", "Operations about Student-Life"))
				.tags(new Tag("Type Of Sports", "Operations about Type Of Sports"))
				.tags(new Tag("Townships", "Operations about Township")).genericModelSubstitutes(Optional.class);

	}

	private ApiInfo metadata() {
		return new ApiInfoBuilder()//
				.title("JSON Web Token Authentication API")//
				.description(
						"This is a sample JWT authentication service. You can find out more about JWT at [https://jwt.io/](https://jwt.io/). For this sample, you can use the `admin` or `client` users (password: admin and client respectively) to test the authorization filters. Once you have successfully logged in and obtained the token, you should click on the right top button `Authorize` and introduce it with the prefix \"Bearer \".")//
				.version("1.0.1")//
				.license("MIT License").licenseUrl("http://opensource.org/licenses/MIT")//
				.contact(new Contact(null, null, "pyaesonehein1141991@gmail.com"))//
				.build();
	}

}
