package com.shubham.project.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.management.AttributeNotFoundException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;
import com.shubham.project.dto.OrderRequest;
import com.shubham.project.entity.Order;
import com.shubham.project.service.OrderService;
import com.shubham.project.util.PDFGenerator;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	private ApplicationContext ac;
	
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    
    }
    
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody @Valid OrderRequest orderRequest){
        return new ResponseEntity<>(orderService.createOrder(orderRequest), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
   	public ResponseEntity<?> getOrderById(@PathVariable int id){
   		Order order = orderService.getOrderById(id);
   		return new ResponseEntity<Order>(order,HttpStatus.FOUND);
   	}
       
       @PutMapping("/{id}")
   	public Order updateOrder(@PathVariable int id, @RequestBody @Valid OrderRequest orderRequest){
   		orderRequest.setOrdId(id);
   		return orderService.updateOrder(orderRequest);
   		
   	}
       
       @DeleteMapping("/{id}")
       public ResponseEntity<?> deleteOrder(@PathVariable int id){
       	
       	orderService.deleteOrder(id);
    	return  ResponseEntity.ok().body("Deleted Successfully");

    }
       
       
    @GetMapping("/productId/{id}")
    public List<Order> getOrdersByProductId(@PathVariable int id){
    	return orderService.getOrderByProductId(id);
    }
       

    
    @GetMapping("/export")
  	public void exportOrderDetailsToPDF(HttpServletResponse response) throws DocumentException, IOException {
  		
  	
  		PDFGenerator pDFGenerator = ac.getBean("pdfGenerator",PDFGenerator.class);
		
		pDFGenerator.generatePdfReport(response);
  	}
    
    
}
