package com.example.dev2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dev2.Dto.AddToCartDto;
import com.example.dev2.Dto.AddWishListDto;
import com.example.dev2.Dto.OrderRequestDto;
import com.example.dev2.Dto.ProductRequestDTO;
import com.example.dev2.entity.Cart;
import com.example.dev2.entity.CartItem;
import com.example.dev2.entity.CustomerEntity;
import com.example.dev2.entity.OrderDetails;
import com.example.dev2.entity.OrdersEntity;
import com.example.dev2.entity.ProductCategory;
import com.example.dev2.entity.ProductEntity;
import com.example.dev2.entity.WishList;
import com.example.dev2.repository.CartItemRepository;
import com.example.dev2.repository.CartRepository;
import com.example.dev2.repository.CustomerRepository;
import com.example.dev2.repository.ProductCategoryRepository;
import com.example.dev2.repository.ProductRepository;
import com.example.dev2.repository.WishListRepository;
import com.example.dev2.service.AdminService;
import com.example.dev2.service.CustomerService;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.transaction.Transactional;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path="/api/user" , headers="Accept=application/json")
public class CustomerController {
	
	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductCategoryRepository productCatRepo;
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private CartRepository cartRepo;
	@Autowired
	private CartItemRepository cartItemRepo;
	@Autowired
	private WishListRepository wishRepo;
	
	
	
	
	@Secured("permitAll") 
	@GetMapping("products")
	public List<ProductEntity> getAllProducts(ProductEntity product) {
		
		return customerService.getAllProducts(product);
	}
	
	
	@Secured("permitAll") 
	@GetMapping("products/c/{productcategoryId}")
	public List<ProductEntity> getProductsByCategoryId(@PathVariable("productcategoryId") Integer productcategoryId){
		
		return customerService.getProductsByCategoryId(productcategoryId);
	}
	
	
	@Secured("permitAll") 
	@GetMapping("products/{id}")
	public Optional<ProductEntity> getProductById(@PathVariable("id")Integer id){
		return customerService.getProductById(id);
		
	}
	

	@Secured("permitAll") 
	@GetMapping("products/trending")
	public List<ProductEntity> getTrending(){
		return customerService.getTrending();
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("placeOrder/{customerId}")
	public ResponseEntity<OrdersEntity> placeOrder(@RequestBody OrderRequestDto orderRequest) {
		try {
			OrdersEntity order = customerService.placeOrder(orderRequest.getCustomerId(), orderRequest.getOrderDetails());
			return ResponseEntity.ok(order);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(null);
		}
	}
	

	
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("addCart")
	public ResponseEntity<Cart> addToCart(@RequestBody AddToCartDto addcart) {
	    try {
        Optional<CustomerEntity> customerOptional = customerRepo.findById(addcart.getCustomerId());
	        if (!customerOptional.isPresent()) {
	            return ResponseEntity.badRequest().body(null);
	        }
	        
	        CustomerEntity customer = customerOptional.get();
	        Cart cart = customerService.addToCart(customer,addcart.getProduct().getId(),addcart.getQuantity());
	        
        return ResponseEntity.ok(cart);

	    } catch (Exception e) {
	        return ResponseEntity.badRequest().body(null);
	    }
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("addToWishList/{productId}")
	 public WishList addItemToWishList(@RequestBody AddWishListDto addWishList) {
	        return customerService.addItemToWishList(addWishList.getCustomerId(),addWishList.getProductId());
	    }

	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("cart/{customerId}")
	public ResponseEntity<List<CartItem>> getCartItemsByCustomerId(@PathVariable Integer customerId){
		Optional<CustomerEntity> customerOptional = customerRepo.findById(customerId);
		if(customerOptional.isEmpty()) {
			return ResponseEntity.badRequest().body(null);
		}
		CustomerEntity customer = customerOptional.get();
		Optional<Cart> cartOptional = cartRepo.findByCustomer(customer);
		if(!cartOptional.isPresent()) {
			 return ResponseEntity.ok(new ArrayList<>());
		}
		Cart cart = cartOptional.get();
		List<CartItem> cartItems = cartItemRepo.findByCart(cart);
		return ResponseEntity.ok(cartItems);
		
		
	}
	
	
	@GetMapping("/wishlist/{customerId}")
	public ResponseEntity<List<WishList>> getWishlistByCustomerId(@PathVariable Integer customerId) {
		Optional<CustomerEntity> customerOptional = customerRepo.findById(customerId);
		if(customerOptional.isEmpty()) {
			return ResponseEntity.badRequest().body(null);
		}
		CustomerEntity customer = customerOptional.get();
		 
		    List<WishList> wishItems = wishRepo.findByCustomer(customer);
		    return ResponseEntity.ok(wishItems);
		
		
	    
	}
	

}