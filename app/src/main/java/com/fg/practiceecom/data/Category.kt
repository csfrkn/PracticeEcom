package com.fg.practiceecom.data

sealed class Category(
    val category: String
) {
    object Tshirt : Category("Tshirt")
    object Coat : Category("Coat")
    object Trouser : Category("Trouser")
    object Sweatshirt : Category("Sweatshirt")
    object Shoe : Category("Shoe")
}
