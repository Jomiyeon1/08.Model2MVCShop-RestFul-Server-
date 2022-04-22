package com.model2.mvc.web.product;

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

@Controller
@RequestMapping("/product/*")
public class ProductRestController {
	/// Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productservice;

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
	public String addProduct(@ModelAttribute("product") Product product) throws Exception {

		System.out.println("/product/json/addProduct : POST");
		// Business Logic
		productservice.addProduct(product);

		return "forward:/product/addView.jsp";

	}
	///
	//*************get
	@RequestMapping(value = "json/getProduct", method = RequestMethod.GET)
	public String getProduct(@RequestParam("prodNo") int prodNo, Model model) throws Exception {

		System.out.println("/product/getProduct : GET ");

		Product product = productservice.getProduct(prodNo);

		model.addAttribute("product", product);

		return "forward:/product/getProduct.jsp";

	}
}
