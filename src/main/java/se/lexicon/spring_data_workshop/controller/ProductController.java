package se.lexicon.spring_data_workshop.controller;

import javax.validation.Valid;

import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

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
	
	@GetMapping("product")
	public String allProducts(Model model) {
		model.addAttribute("products", productService.findAll());
		
		return "products";
	}
	
	@GetMapping("product/create")
	public String getForm(Model model) {
		ProductForm theForm = new ProductForm();	
		
		model.addAttribute("product", theForm);
				
		return "register_product";
	}
	
	@PostMapping("product/create")
	public ModelAndView registerProduct(@Valid @ModelAttribute("product") ProductForm theForm, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			ModelAndView model = new ModelAndView();
			model.setViewName("register_product");
			return model;
		}
		
		Product newProduct = new Product(theForm.getName(), theForm.getPrice());
		newProduct = productService.save(newProduct);
		ModelAndView model = new ModelAndView("redirect:/product/" + newProduct.getId());
		
		return model;
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
	
	@DeleteMapping("product/{id}/delete")
	public String delete(@PathVariable("id")int id, Model model) {
		
		try {
			productService.removeProduct(id);
		}catch(Exception e) {
			System.err.println(e.getCause());
			model.addAttribute("error", "Object cant be removed");
			model.addAttribute("product", productService.findById(id));
			return "redirect:/product/" + id;
		}
		
		
		return "redirect:/product";
		
	}
	
	@GetMapping("product/{id}/update")
	public String getUpdateForm(@PathVariable("id") int id, Model model) {
		ProductForm form = new ProductForm();
		Product theProduct = productService.findById(id);
		form.setId(theProduct.getId());
		form.setName(theProduct.getName());
		form.setPrice(theProduct.getPrice());
		
		model.addAttribute("product", form);
		
		return "edit";		
	}
	
	@PutMapping("product/{id}/update")
	public String update(@PathVariable("id") int id , @ModelAttribute("product") ProductForm form) {
		Product toUpdate = productService.findById(id);
		toUpdate.setName(form.getName());
		toUpdate.setPrice(form.getPrice());
		toUpdate = productService.save(toUpdate);
		
		
		return "redirect:/product/" + toUpdate.getId();
	}	
}
