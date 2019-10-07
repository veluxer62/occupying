package com.kh.api.response.listCard

data class ListCard(
        val header: Header,
        val items: List<ItemsItem>,
        val buttons: List<ButtonsItem>
)