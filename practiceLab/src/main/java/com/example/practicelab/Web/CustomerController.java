package com.example.practicelab.Web;

import com.example.practicelab.Repositories.CustomerRepository;
import com.example.practicelab.entities.Customer;
import com.example.practicelab.models.Projection;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public String getAllCustomers(Model model) {
        model.addAttribute("customers", customerRepository.findAll());
        return "index";  // This is the Thymeleaf template name
    }

    @GetMapping("/add")
    public String showAddCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("savingsTypes", new String[]{"Savings-Deluxe", "Savings-Regular"});
        return "add-customer";  // This is the Thymeleaf template name for adding customer
    }

    @PostMapping("/add")
    public String addCustomer(@ModelAttribute Customer customer, Model model) {
        Optional<Customer> existingCustomer = customerRepository.findByCustomerNumber(customer.getCustomerNumber());
        if (existingCustomer.isPresent()) {
            model.addAttribute("errorMessage", "The record you are trying to add is already existing. Choose a different customer number.");
            model.addAttribute("customers", customerRepository.findAll()); // Add this line
            return "index";
        }
        customer.setCreatedAt(new Date());  // Ensure createdAt is set
        customerRepository.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/edit/{id}")
    public String showEditCustomerForm(@PathVariable("id") Long id, Model model) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            model.addAttribute("customer", customer.get());
            model.addAttribute("savingsTypes", new String[]{"Savings-Deluxe", "Savings-Regular"});
            return "edit-customer";
        } else {
            return "redirect:/customers";
        }
    }

    @PostMapping("/edit")
    public String editCustomer(@ModelAttribute Customer customer, Model model) {
        Optional<Customer> existingCustomer = customerRepository.findByCustomerNumber(customer.getCustomerNumber());
        if (existingCustomer.isPresent() && !existingCustomer.get().getId().equals(customer.getId())) {
            model.addAttribute("errorMessage", "The record you are trying to edit to has an existing customer number. Choose a different customer number.");
            model.addAttribute("customers", customerRepository.findAll());
            return "index";
        }
        customer.setCreatedAt(new Date());  // Update createdAt if needed
        customerRepository.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Long id) {
        customerRepository.deleteById(id);
        return "redirect:/customers";
    }

    @GetMapping("/projected/{id}")
    public String showProjectedInvestment(@PathVariable("id") Long id, Model model) {
        Optional<Customer> customerOpt = customerRepository.findById(id);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            model.addAttribute("customer", customer);
            double interestRate = customer.getSavingsType().equals("Savings-Deluxe") ? 0.15 : 0.10;
            model.addAttribute("interestRate", interestRate);

            // Calculate projected table
            List<Projection> projections = new ArrayList<>();
            double startingAmount = customer.getDeposit();
            for (int year = 1; year <= customer.getYears(); year++) {
                double interest = startingAmount * interestRate;
                double endingBalance = startingAmount + interest;
                projections.add(new Projection(year, startingAmount, interest, endingBalance));
                startingAmount = endingBalance;
            }
            model.addAttribute("projections", projections);
            return "projected-investment";
        } else {
            return "redirect:/customers";
        }
    }
}
