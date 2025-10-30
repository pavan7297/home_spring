package com.home.data.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "purchases")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {

    @Id
    private String id;

    @NotBlank(message = "User ID cannot be blank")
    private String userId;

    @NotBlank(message = "Purchase item cannot be blank")
    private String purchaseItem;

    @NotNull(message = "Purchase price is required")
    @Min(value = 1, message = "Price must be greater than 0")
    private Double purchasePrice;

    private LocalDate date;
    
}