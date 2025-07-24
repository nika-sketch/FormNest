package com.example.formnest.shared

interface Mapper<I, O> {
  fun map(input: I): O
}
