package com.crm.spring.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.crm.spring.entity.Customer;
import com.crm.spring.service.CustomerService;


@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	// need to inject CustomerService
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/list")
	public String listCustomers(Model theModel) {
		
		// get the customers to the dao
		List<Customer> theCustomers = customerService.getCustomers();
		
		// add the customers to the model
		theModel.addAttribute("customers", theCustomers);
		
		return "list-customers";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind for data
		Customer theCustomer = new Customer();
		
		theModel.addAttribute("customer", theCustomer);
		
		
		return "customer-form";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@Valid @ModelAttribute("customer") Customer theCustomer, BindingResult theBindingResult) {
		
		
		if(theBindingResult.hasErrors()) {
			return "customer-form";
		}
		else {
			customerService.saveCustomer(theCustomer);
			return "redirect:/customer/list";
		}
		
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theId, Model theModel) {
		
		// get the customer from the service
		Customer theCustomer = customerService.getCustomer(theId);
		
		// set the customer as a model attribute to prepopulate the form
		theModel.addAttribute("customer", theCustomer);
		
		// send over to our form
		
		
		return "customer-form";
	}
	
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int theId) {
		
		// delete the customer
		customerService.deleteCustomer(theId);
		
		
		return "redirect:/customer/list";
	}
	
	
	
}
