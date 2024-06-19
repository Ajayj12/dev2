package com.example.dev2.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.example.dev2.Dto.CustomerDTO;
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
import com.example.dev2.repository.OrderDetailsRepository;
import com.example.dev2.repository.OrdersRepository;
import com.example.dev2.repository.ProductCategoryRepository;
import com.example.dev2.repository.ProductRepository;
import com.example.dev2.repository.WishListRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private OrderDetailsRepository orderDetailsRepo;
	@Autowired
	private ProductCategoryRepository productCatRepo;
	@Autowired
	private OrdersRepository ordersRepo;
	@Autowired
	private CartRepository cartRepo;
	@Autowired
	private CartItemRepository cartItemRepo;
	@Autowired
	private WishListRepository wishListRepo;
	
	
	public List<ProductEntity> getAllProducts(ProductEntity product) {
		
		List<ProductEntity> prod = productRepo.findAll();
		return prod;
		
	}
	
	public Optional<ProductEntity> getProductById(Integer id) {
		return productRepo.findById(id);
	}
	
	public List<ProductEntity> getProductsByCategoryId(Integer productcategoryId){
		
		return productRepo.findProductsByCategory_Id(productcategoryId);
	}
	
	public List<ProductEntity> getTrending() {
		
		int stockleft = 95;
        return productRepo.findByStockLessThanEqual(stockleft);
		
	}
	
		
		//PLACE ORDER BY CUSTOMER
	public OrdersEntity placeOrder(Integer customerId, List<OrderDetails> orderDetails) throws Exception{
		
		Optional<CustomerEntity> customer = customerRepo.findById(customerId);
		if(!customer.isPresent()) {
			throw new Exception("Customer not found");
			
		}
		
		CustomerEntity cust = customer.get();
		OrdersEntity order = new OrdersEntity();
		order.setCustomer(cust);
		order.setOrderDate(new Date());
		order.setStatus("Pending");
		
		double totalAmount = 0;
		
		for(OrderDetails ordList : orderDetails) {
			 Optional<ProductEntity> productOptional = productRepo.findById((ordList.getProduct().getId()));
	            if (!productOptional.isPresent()) {
	                throw new Exception("Product not found for ID: " + (ordList.getProduct().getId()));
	            }
	            
	          
	         ProductEntity product = productOptional.get();
	         if(product.getStock() != 0) {
	         ordList.setOrder(order);
	         ordList.setUnitPrice(product.getAmount());
	         ordList.setTotalPrice(product.getAmount() * ordList.getQuantity());
	         
	         totalAmount += ordList.getTotalPrice();
	         }
		}
		
		order.setTotalAmount(totalAmount);
		
		OrdersEntity saveOrder = ordersRepo.save(order);
		
		for(OrderDetails ordList : orderDetails) {
			orderDetailsRepo.save(ordList);
		}
		
		return saveOrder;
		
	}
	
	// ADD TO CART
	public Cart addToCart(CustomerEntity customer, Integer productId, Integer quantity) throws Exception {
	    Optional<ProductEntity> productOptional = productRepo.findById(productId);
	    if (!productOptional.isPresent()) {
	        throw new Exception("Product not found");
	    }
	    ProductEntity product = productOptional.get();

	    Optional<Cart> cartOptional = cartRepo.findByCustomer(customer);
	    Cart cart;
	    if (cartOptional.isPresent()) {
	        cart = cartOptional.get();
	    } else {
	        cart = new Cart();
	        cart.setCustomer(customer);
	        cartRepo.save(cart);
	    }

	    Optional<CartItem> cartItemOptional = cartItemRepo.findByCartAndProduct(cart, product);
	    CartItem cartItem;
	    if (cartItemOptional.isPresent()) {
	        cartItem = cartItemOptional.get();
	        cartItem.setQuantity(cartItem.getQuantity() + quantity);
	    } else {
	        cartItem = new CartItem();
	        cartItem.setProduct(product);
	        cartItem.setQuantity(quantity);
	        cartItem.setCart(cart);
	    }
	    cartItemRepo.save(cartItem);
	    return cart;
	}

	
	// ADD to wishlist
	
	
	public WishList addItemToWishList(Integer customerId, Integer productId) {
        Optional<CustomerEntity> customer = customerRepo.findById(customerId);
        Optional<ProductEntity> product = productRepo.findById(productId);
        if (customer.isPresent() && product.isPresent()) {
            // Check if the product is already in the wishlist
            Optional<WishList> existingWishListItem = wishListRepo.findByCustomerAndProduct(customer.get(), product.get());
            if (existingWishListItem.isPresent()) {
                // Product is already in the wishlist, return a message
                throw new RuntimeException("Product is already in the wishlist.");
            } else {
                // Create a new wishlist item
                WishList wishListItem = new WishList();
                wishListItem.setCustomer(customer.get());
                wishListItem.setProduct(product.get());
                return wishListRepo.save(wishListItem);
            }
        } else {
            throw new RuntimeException("Customer or product not found.");
        }
    }
	
	
	public Optional<WishList> getWishlistByCustomerId(Integer customerId) {
	    return wishListRepo.findByCustomerCustomerId(customerId);
	}
	
	

}
