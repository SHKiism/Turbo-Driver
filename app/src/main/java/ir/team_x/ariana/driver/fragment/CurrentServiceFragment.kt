package ir.team_x.ariana.driver.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.team_x.ariana.driver.adapter.CurrentServiceAdapter
import ir.team_x.ariana.driver.app.EndPoint
import ir.team_x.ariana.driver.app.MyApplication
import ir.team_x.ariana.driver.databinding.FragmentCurrentServicesBinding
import ir.team_x.ariana.driver.model.ServiceDataModel
import ir.team_x.ariana.driver.okHttp.RequestHelper
import org.json.JSONObject

class CurrentServiceFragment : Fragment() {

    private lateinit var binding: FragmentCurrentServicesBinding
    val serviceModels: ArrayList<ServiceDataModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrentServicesBinding.inflate(inflater, container, false)

        binding.imgBack.setOnClickListener { MyApplication.currentActivity.onBackPressed() }

        getActiveService()

        return binding.root
    }

    private fun getActiveService() {
        RequestHelper.builder(EndPoint.ACTIVES)
            .listener(activeServiceCallBack)
            .get()
    }

    private val activeServiceCallBack: RequestHelper.Callback = object : RequestHelper.Callback() {
        override fun onResponse(reCall: Runnable?, vararg args: Any?) {
            MyApplication.handler.post {
                try {
                    val jsonObject = JSONObject(args[0].toString())
                    val success = jsonObject.getBoolean("success")
                    val message = jsonObject.getString("message")
                    if (success) {
                        val dataArr = jsonObject.getJSONArray("data")
                        for (i in 0 until dataArr.length()) {
                            val dataObj = dataArr.getJSONObject(i)
                            val model = ServiceDataModel(
                                dataObj.getInt("id"),
                                dataObj.getInt("customerId"),
                                dataObj.getInt("sourceAddressId"),
                                dataObj.getInt("destinationAddressId"),
                                dataObj.getInt("count"),
                                dataObj.getString("description"),
                                dataObj.getInt("carType"),
                                dataObj.getInt("stopTime"),
                                dataObj.getInt("driverHelp"),
                                dataObj.getString("saveDate"),
                                dataObj.getInt("weight"),
                                dataObj.getInt("userId"),
                                dataObj.getInt("costId"),
                                dataObj.getInt("paymentSide"),
                                dataObj.getInt("cargoId"),
                                dataObj.getInt("status"),
                                dataObj.getInt("driverId"),
                                dataObj.getString("finishDate"),
                                dataObj.getString("voipId"),
                                dataObj.getString("acceptDate"),
                                dataObj.getString("price"),
                                dataObj.getString("customerName"),
                                dataObj.getString("phoneNumber"),
                                dataObj.getString("mobile")
                            )

                            serviceModels.add(model)
                        }

                        val adapter = CurrentServiceAdapter(serviceModels)
                        binding.listCurrentService.adapter = adapter

                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        override fun onFailure(reCall: Runnable?, e: java.lang.Exception?) {
            MyApplication.handler.post {

            }
        }
    }

}