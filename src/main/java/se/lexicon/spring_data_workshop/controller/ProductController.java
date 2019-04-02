package se.lexicon.spring_data_workshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import se.lexicon.spring_data_workshop.entity.Product;
import se.lexicon.spring_data_workshop.form.ProductForm;
import se.lexicon.spring_data_workshop.service.ProductService;

@Controller
public class ProductController {
	
	private ProductService productService;

	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping("product/create")
	public String getForm(Model model) {
		ProductForm theForm = new ProductForm();	
		
		model.addAttribute("product", theForm);
				
		return "register_product";
	}
	
	@PostMapping("product/create")
	public String registerProduct(@ModelAttribute("product") ProductForm theForm, Model theModel) {
		Product newProduct = new Product(theForm.getName(), theForm.getPrice());
		
		theModel.addAttribute("product", productService.save(newProduct));		
		
		return "product";
	}
	
	@GetMapping("product/{id}")
	public String getProductById(@PathVariable("id")int id, Model model) {
		
		try {
			model.addAttribute("product", productService.findById(id));
			return "product";
		}catch(IllegalArgumentException e) {
			model.addAttribute("error", e);
			return "notFound";
		}		
	}
	
	

}
