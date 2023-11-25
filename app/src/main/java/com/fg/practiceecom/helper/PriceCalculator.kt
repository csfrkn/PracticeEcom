package com.fg.practiceecom.helper

fun Float?.getProductPrice(price: Float): Float {
    if (this == null)
        return price
    val remaningPricePercentage = 1f - this
    val priceAfterOffer = remaningPricePercentage * price

    return priceAfterOffer
}