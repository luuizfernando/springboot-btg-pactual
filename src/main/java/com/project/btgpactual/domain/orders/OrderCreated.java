package com.project.btgpactual.domain.orders;

import java.math.BigDecimal;

public record OrderCreated(

    String product,
    Integer quantity,
    BigDecimal price
    
) {}