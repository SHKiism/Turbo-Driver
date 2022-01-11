package ir.team_x.cloud_transport.taxi_driver.model

data class WaitingLoadsModel(
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
    var costName:String,
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
    var sourceStationName:String,
    var cargoName:String,
    var fixedMessage:String,
    var returnBack:Int,
    var packageValue:String,
    var sourceAddress:String,
    var destinationAddress:String
)