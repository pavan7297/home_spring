package com.home.data.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.home.data.model.Purchase;
import com.home.data.repository.PurchaseRepository;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/purchases")
public class PurchaseDataController {
	

	 @Autowired
	    private PurchaseRepository purchaseRepository;

	    // ✅ Create Purchase
	    @PostMapping
	    public ResponseEntity<Map<String, Object>> savePurchase(@Valid @RequestBody Purchase purchase) {
	        Map<String, Object> response = new HashMap<>();
	        try {
	            if (purchase.getDate() == null) {
	                purchase.setDate(LocalDate.now());
	            }
	            Purchase savedPurchase = purchaseRepository.save(purchase);

	            response.put("responseCode", HttpStatus.OK.value());
	            response.put("message", "Purchase saved successfully");
	            response.put("data", savedPurchase);

	            return ResponseEntity.ok(response);
	        } catch (Exception e) {
	            response.put("responseCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
	            response.put("message", "Error saving purchase: " + e.getMessage());
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	        }
	    }

	    // ✅ Get All Purchases
	    @GetMapping
	    public ResponseEntity<Map<String, Object>> getAllPurchases() {
	        Map<String, Object> response = new HashMap<>();
	        try {
	            List<Purchase> purchases = purchaseRepository.findAll();

	            if (purchases.isEmpty()) {
	                response.put("responseCode", HttpStatus.NO_CONTENT.value());
	                response.put("message", "No purchases found");
	                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
	            }

	            response.put("responseCode", HttpStatus.OK.value());
	            response.put("message", "Purchases fetched successfully");
	            response.put("data", purchases);
	            return ResponseEntity.ok(response);

	        } catch (Exception e) {
	            response.put("responseCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
	            response.put("message", "Error fetching purchases: " + e.getMessage());
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	        }
	    }

	    // ✅ Get Purchase by ID
	    @GetMapping("/{id}")
	    public ResponseEntity<Map<String, Object>> getPurchaseById(@PathVariable String id) {
	        Map<String, Object> response = new HashMap<>();
	        try {
	            Optional<Purchase> purchase = purchaseRepository.findById(id);

	            if (purchase.isPresent()) {
	                response.put("responseCode", HttpStatus.OK.value());
	                response.put("message", "Purchase fetched successfully");
	                response.put("data", purchase.get());
	                return ResponseEntity.ok(response);
	            } else {
	                response.put("responseCode", HttpStatus.NOT_FOUND.value());
	                response.put("message", "Purchase not found");
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	            }

	        } catch (Exception e) {
	            response.put("responseCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
	            response.put("message", "Error fetching purchase: " + e.getMessage());
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	        }
	    }

	    // ✅ Update Purchase
	    @PutMapping("/{id}")
	    public ResponseEntity<Map<String, Object>> updatePurchase(@PathVariable String id, @Valid @RequestBody Purchase updatedPurchase) {
	        Map<String, Object> response = new HashMap<>();
	        try {
	            Optional<Purchase> existingPurchase = purchaseRepository.findById(id);

	            if (existingPurchase.isPresent()) {
	                Purchase purchase = existingPurchase.get();
	                purchase.setUserId(updatedPurchase.getUserId());
	                purchase.setPurchaseItem(updatedPurchase.getPurchaseItem());
	                purchase.setPurchasePrice(updatedPurchase.getPurchasePrice());
	                purchase.setDate(updatedPurchase.getDate() != null ? updatedPurchase.getDate() : purchase.getDate());

	                Purchase savedPurchase = purchaseRepository.save(purchase);

	                response.put("responseCode", HttpStatus.OK.value());
	                response.put("message", "Purchase updated successfully");
	                response.put("data", savedPurchase);
	                return ResponseEntity.ok(response);

	            } else {
	                response.put("responseCode", HttpStatus.NOT_FOUND.value());
	                response.put("message", "Purchase not found for update");
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	            }

	        } catch (Exception e) {
	            response.put("responseCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
	            response.put("message", "Error updating purchase: " + e.getMessage());
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	        }
	    }

	    // ✅ Delete Purchase
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Map<String, Object>> deletePurchase(@PathVariable String id) {
	        Map<String, Object> response = new HashMap<>();
	        try {
	            if (purchaseRepository.existsById(id)) {
	                purchaseRepository.deleteById(id);
	                response.put("responseCode", HttpStatus.OK.value());
	                response.put("message", "Purchase deleted successfully");
	                return ResponseEntity.ok(response);
	            } else {
	                response.put("responseCode", HttpStatus.NOT_FOUND.value());
	                response.put("message", "Purchase not found for deletion");
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	            }

	        } catch (Exception e) {
	            response.put("responseCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
	            response.put("message", "Error deleting purchase: " + e.getMessage());
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	        }
	    }

}
