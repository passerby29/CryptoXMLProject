package dev.passerby.data.repos

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import dev.passerby.data.database.AppDatabase
import dev.passerby.data.mappers.CoinMapper
import dev.passerby.data.models.db.CoinDbModel
import dev.passerby.data.models.dto.CoinsDto
import dev.passerby.data.network.ApiFactory
import dev.passerby.data.network.BaseResponse
import dev.passerby.domain.models.CoinModel
import dev.passerby.domain.repos.MainRepository

class MainRepositoryImpl(application: Application) : MainRepository {

    private val db = AppDatabase.getInstance(application)
    private val coinDao = db.coinDao()
    private val favoriteDao = db.favoriteDap()
    private val apiService = ApiFactory.apiService
    private val coinMapper = CoinMapper()
    private var result: MutableLiveData<BaseResponse<CoinsDto>> = MutableLiveData()

    override fun getCoinInfo(coinId: String): LiveData<CoinModel> {
        val coinInfo = coinDao.getCoinInfo(coinId)
        return coinInfo.map { coinMapper.mapDbModelToEntity(it) }
    }

    override fun getCoinList(): LiveData<List<CoinModel>> {
        val coinList = coinDao.getCoinList()
        return getEntityList(coinList)
    }

    override fun getTopCoinList(): LiveData<List<CoinModel>> {
        val coinList = coinDao.getTopCoins()
        return getEntityList(coinList)
    }

    override fun getFavorites(): LiveData<List<CoinModel>> {
        val favoriteList = favoriteDao.getFavoritesList()
        return getEntityList(favoriteList)
    }

    override fun searchCoins(filter: String): LiveData<List<CoinModel>> {
        val coinList = coinDao.searchCoins(filter)
        return getEntityList(coinList)
    }

    override suspend fun loadCoins() {
        result.value = BaseResponse.Loading()
        try {
            val response = apiService.getCoinsList()
            if (response.code() == 200) {
                result.value = BaseResponse.Success(response.body())
                val dbModelList = response.body()?.coins?.map {
                    coinMapper.mapDtoToDbModel(it)
                }
                coinDao.insertCoin(dbModelList ?: emptyList())
                Log.d(TAG, "loadCoinsTry: ${response.body()?.coins}")
            } else {
                result.value = BaseResponse.Error(response.message())
                Log.d(TAG, "loadCoinsCatch: ${response.message()}")
            }
        } catch (ex: Exception) {
            Log.d(TAG, "loadCoinsCatch: $ex")
            result.value = BaseResponse.Error(ex.message)
        }
    }

    private fun getEntityList(coinsList: LiveData<List<CoinDbModel>>): LiveData<List<CoinModel>> {
        return coinsList.map { list ->
            list.map {
                coinMapper.mapDbModelToEntity(it)
            }
        }
    }

    companion object {
        private const val TAG = "MainRepositoryImplTag"
    }
}