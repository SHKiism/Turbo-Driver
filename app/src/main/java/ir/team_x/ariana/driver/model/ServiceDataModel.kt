package ir.team_x.ariana.driver.model

import org.json.JSONArray

data class ServiceDataModel(
  var id:Int,
  var customerId:Int,
  var sourceAddressId:Int,
  var count:Int,
  var description:String,
  var carType:Int,
  var stopTime:Int,
  var driverHelp:Int,
  var saveDate:String,
  var weight:Int,
  var userId:Int,
  var costId:Int,
  var paymentSide:Int,
  var cargoId:Int,
  var status:Int,
  var driverId:Int,
  var finishDate:String,
  var voipId:String,
  var acceptDate:String,
  var price:String,
  var customerName:String,
  var phoneNumber:String,
  var mobile:String,
  var statusStr:String,
  var cargoName:String,
  var costName:String,
  var weightName:String,
  var carTypeName:String,
  var sourceAddress:String,
  var destinationAddress:String,
  var priceService:String,
  var returnBack:Int
)