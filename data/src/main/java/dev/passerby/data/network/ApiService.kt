package dev.passerby.data.network

import dev.passerby.data.models.dto.CoinsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("coins/")
    suspend fun getCoinsList(
        @Query(QUERY_PARAM_SKIP) skip: Int = 0,
        @Query(QUERY_PARAM_LIMIT) limit: Int = 50,
        @Query(QUERY_PARAM_CURRENCY) currency: String = CURRENCY
    ): Response<CoinsDto>

    companion object {
        private const val QUERY_PARAM_SKIP = "skip"
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_CURRENCY = "currency"
        private const val CURRENCY = "USD"
    }
}