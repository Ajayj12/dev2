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
	public OrdersEntity placeOrder(Integer customerId, List<OrderDetails> orderDetails, List<CartItem> cartItems) throws Exception{
		
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
		order.setOrderDetails(orderDetails);
		OrdersEntity saveOrder = ordersRepo.save(order);
		
		for(CartItem cartIt : cartItems) {
			Optional<ProductEntity> productOptional = productRepo.findById((cartIt.getProduct().getId()));
			ProductEntity product = productOptional.get();
		    
		    Optional<Cart> cartOptional = cartRepo.findByCustomer(cust);
		    Cart cart = cartOptional.get();
		    if(cartOptional.isPresent()) {
		    	Optional<CartItem> cartItemOptional = cartItemRepo.findByCartAndProduct(cart, product);
		    	
		    	CartItem cartItem = cartItemOptional.get();
		    	cartItemRepo.delete(cartItem);
		    	
		    }
			
		}

		
		return saveOrder;
		
	}
	
	
	public List<OrdersEntity> getOrdersById(Integer customerId) throws Exception {
		
		return ordersRepo.findByCustomerCustomerId(customerId);
		
		
		
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
	
	public CartItem increaseQuantity(CustomerEntity customer, Integer productId) throws Exception {
		Optional<ProductEntity> productOptional = productRepo.findById(productId);
	    if (!productOptional.isPresent()) {
	        throw new Exception("Product not found");
	    }
	    ProductEntity product = productOptional.get();
	    Optional<Cart> cartOptional = cartRepo.findByCustomer(customer);
	    Cart cart = cartOptional.get();
	    CartItem cartItem;
	    if(cartOptional.isPresent()) {
	    	Optional<CartItem> cartItemOptional = cartItemRepo.findByCartAndProduct(cart, product);

	    	cartItem = cartItemOptional.get();
	    	cartItem.setQuantity(cartItem.getQuantity() + 1);
	    	cartItemRepo.save(cartItem);
	    	return cartItem;	
	}return null;
	    
	}
	
	public CartItem decreaseQuantity(CustomerEntity customer, Integer productId) throws Exception {
		Optional<ProductEntity> productOptional = productRepo.findById(productId);
	    if (!productOptional.isPresent()) {
	        throw new Exception("Product not found");
	    }
	    ProductEntity product = productOptional.get();
	    Optional<Cart> cartOptional = cartRepo.findByCustomer(customer);
	    Cart cart = cartOptional.get();
	    CartItem cartItem = null;
	    if(cartOptional.isPresent()) {
	    	Optional<CartItem> cartItemOptional = cartItemRepo.findByCartAndProduct(cart, product);

	    	cartItem = cartItemOptional.get();
	    	cartItem.setQuantity(cartItem.getQuantity() - 1);
	    	cartItemRepo.save(cartItem);
	    	if(cartItem.getQuantity() == 0) {
	    		throw new Exception("Cart is Empty");
	    	};
	    	
	}return cartItem;
	    
	}
	
	//delete from cart
	public void removeFromCart(CustomerEntity customer, Integer productId) throws Exception {
		 Optional<ProductEntity> productOptional = productRepo.findById(productId);
		    if (!productOptional.isPresent()) {
		        throw new Exception("Product not found");
		    }
		    ProductEntity product = productOptional.get();
		    
		    Optional<Cart> cartOptional = cartRepo.findByCustomer(customer);
		    Cart cart = cartOptional.get();
		    if(cartOptional.isPresent()) {
		    	Optional<CartItem> cartItemOptional = cartItemRepo.findByCartAndProduct(cart, product);
		    	
		    	CartItem cartItem = cartItemOptional.get();
		    	cartItemRepo.delete(cartItem);
		    	
		    }
		    
		
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
	
	
	public void removeFromWishList(Integer customerId, Integer productId ) throws Exception {
		Optional<CustomerEntity> customer = customerRepo.findById(customerId);
		
		Optional<ProductEntity> productOptional = productRepo.findById(productId);
	    if (!productOptional.isPresent()) {
	        throw new Exception("Product not found");
	    }
	    ProductEntity product = productOptional.get();
	    
	    Optional<WishList> WishListItem = wishListRepo.findByCustomerAndProduct(customer.get(), product);
        if (WishListItem.isPresent()) {
        	WishList Item = WishListItem.get();
        	wishListRepo.delete(Item);
        }
	    
		
	}
	
	
	
	
	
	public Optional<WishList> getWishlistByCustomerId(Integer customerId) {
	    return wishListRepo.findByCustomerCustomerId(customerId);
	}
	
	

}
