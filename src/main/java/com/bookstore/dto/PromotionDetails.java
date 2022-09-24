package com.bookstore.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode(exclude = {"discountPercentage"})
@NoArgsConstructor
public class PromotionDetails {

	private String type;
	private double discountPercentage;
	private String promotionCode;
}
