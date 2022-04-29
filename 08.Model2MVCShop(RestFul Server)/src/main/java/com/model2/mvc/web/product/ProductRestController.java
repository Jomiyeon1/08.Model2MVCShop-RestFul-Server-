package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@RestController
@RequestMapping("/product/*")
public class ProductRestController {
	/// Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;

	public ProductRestController() {
		System.out.println(this.getClass());
	}

	/// add
	@RequestMapping(value = "json/addProduct", method = RequestMethod.GET)
	public String addProduct() throws Exception {

		System.out.println("/product/json/addProduct : GET");

		return "redirect:/product/addProductView.jsp";
	}

	// @RequestMapping("/addProduct.do")
	@RequestMapping(value = "json/addProduct", method = RequestMethod.POST)
	public Product addProduct(@RequestBody Product product) throws Exception {

		System.out.println("/product/json/addProduct : POST");
		System.out.println("add => "+product);
		 productService.addProduct(product);
		

		return product;

	}
	///
	//*************get
	@RequestMapping(value = "json/getProduct/{prodNo}", method = RequestMethod.GET)
	public Product getProduct(@PathVariable int prodNo) throws Exception {

		System.out.println("/product/getProduct : GET ");

		
		return productService.getProduct(prodNo);

	}
	
	// UPDATE
	@RequestMapping(value = "json/updateProduct/{prodNo}", method = RequestMethod.GET)
	public Product updateProduct(@PathVariable int prodNo) throws Exception {
		
		System.out.println("/product/updateProduct : GET");
		System.out.println("update" + prodNo);


		return productService.getProduct(prodNo);
	}
	
	
	@RequestMapping(value = "json/updateProduct", method = RequestMethod.POST)
	public Product updateProduct(@RequestBody Product product) throws Exception {
		System.out.println("/product/updateProduct : POST");
		
		productService.updateProduct(product);
		
		 return product;
	}
	
	@RequestMapping(value = "json/listProduct")
	public Map listProduct(@RequestBody Search search, HttpServletRequest request) throws Exception {

		System.out.println("/product/listProduct : GET / POST");

		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);

		Map<String, Object> map = productService.getProductList(search);

		Page resultPage = new Page(search.getCurrentPage(), ((Integer) map.get("totalCount")).intValue(), pageUnit,
				pageSize);
		System.out.println(resultPage);


		return map;
	}
}
