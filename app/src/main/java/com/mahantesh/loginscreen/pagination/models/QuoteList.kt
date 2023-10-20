package com.mahantesh.loginscreen.pagination.models

data class QuoteList(
    val count : Int,
    val lastIndex : Int,
    val page : Int,
    val results : List<Result>,
    val totalCount : Int,
    val totalPage : Int
)
