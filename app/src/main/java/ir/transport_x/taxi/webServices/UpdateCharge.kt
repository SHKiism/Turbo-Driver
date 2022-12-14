package ir.transport_x.taxi.webServices

import ir.transport_x.taxi.app.EndPoint
import ir.transport_x.taxi.app.MyApplication
import ir.transport_x.taxi.okHttp.RequestHelper
import org.json.JSONObject

class UpdateCharge {

    interface ChargeListener {
        fun getCharge(charge: String, response: String)
    }

    lateinit var listener: ChargeListener

    fun update(listener: ChargeListener) {
        this.listener = listener
        RequestHelper.builder(EndPoint.CHARGE)
            .listener(getChargeCallBack)
            .get()
    }

    private val getChargeCallBack: RequestHelper.Callback = object : RequestHelper.Callback() {
        override fun onResponse(reCall: Runnable?, vararg args: Any?) {
            MyApplication.handler.post {
                try {
                    val jsonObject = JSONObject(args[0].toString())
                    val success = jsonObject.getBoolean("success")
                    val message = jsonObject.getString("message")

                    if (success) {
                        val dataObj = jsonObject.getJSONObject("data")
                        val charge = dataObj.getString("charge")
                        listener.getCharge(charge, dataObj.toString())
                        MyApplication.prefManager.setCharge(charge)
                    } else {
                        listener.getCharge("","")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    listener.getCharge("","")
                }
            }
        }

        override fun onFailure(reCall: Runnable?, e: java.lang.Exception?) {
            MyApplication.handler.post {
                listener.getCharge("","")
            }
        }
    }

}