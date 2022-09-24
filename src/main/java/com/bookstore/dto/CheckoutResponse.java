package com.bookstore.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CheckoutResponse implements Serializable {
	double totalPrice;
}
