package com.kh.api.response

data class ListCard(
        val header: Header,
        val items: List<ItemsItem>,
        val buttons: List<ButtonsItem>
)