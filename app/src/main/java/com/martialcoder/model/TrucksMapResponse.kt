package com.martialcoder.model


data class TrucksMapResponse(
    val data: ArrayList<Data>,
    val response: ResponseCode
)
data class Data(
    val breakdown: Boolean,
    val companyId: Int,
    val createTime: Long,
    val deactivated: Boolean,
    val durationInsideSite: Int,
    val externalTruck:Boolean,
    val fuelSensorInstalled: Boolean,
    val id: Int,
    val imeiNumber: String,
    val lastRunningState: LastRunningState,
    val lastWaypoint: LastWaypoint,
    val name: String,
    val password: String,
    val simNumber: String,
    val trackerType: Int,
    val truckNumber: String,
    val truckSizeId: Int,
    val truckTypeId: Int
)

data class LastRunningState(
    val lat: Double,
    val lng:Double,
    val stopNotificationSent: Int,
    val stopStartTime: Long,
    val truckRunningState: Int
)

data class LastWaypoint(
    val accuracy: Double,
    val batteryLevel :Int,
    val batteryPower: Boolean,
    val bearing: Double,
    val createTime: Long,
    val fuelLevel: Int,
    val id: Int,
    val ignitionOn:Boolean,
    val lat: Double,
    val lng:  Double,
    val odometerReading: Double,
    val speed: Double,
    val updateTime: Long
)
data class ResponseCode(
    val message: String,
    val responseCode: Int
)