package com.kh.api.response.listCard

import com.kh.api.response.Buttons

data class ListCard(
        val header: Header,
        val items: List<ItemsItem>,
        val buttons: List<Buttons>
)