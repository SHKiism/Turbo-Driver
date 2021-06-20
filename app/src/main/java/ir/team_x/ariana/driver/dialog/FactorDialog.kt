package ir.team_x.ariana.driver.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import ir.team_x.ariana.driver.R
import ir.team_x.ariana.driver.app.EndPoint
import ir.team_x.ariana.driver.app.MyApplication
import ir.team_x.ariana.driver.databinding.DialogFactorBinding
import ir.team_x.ariana.driver.model.ServiceDataModel
import ir.team_x.ariana.driver.okHttp.RequestHelper
import ir.team_x.ariana.driver.push.AvaCrashReporter
import ir.team_x.ariana.driver.utils.KeyBoardHelper
import ir.team_x.ariana.operator.utils.TypeFaceUtil
import org.json.JSONObject

class FactorDialog {

    lateinit var dialog: Dialog
    lateinit var binding: DialogFactorBinding

    fun show(serviceModel: ServiceDataModel) {
        dialog = Dialog(MyApplication.currentActivity)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        binding = DialogFactorBinding.inflate(LayoutInflater.from(MyApplication.context))
        dialog.setContentView(binding.root)
        TypeFaceUtil.overrideFont(binding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val wlp: WindowManager.LayoutParams? = dialog.window?.attributes
        wlp?.gravity = Gravity.CENTER
        wlp?.width = WindowManager.LayoutParams.MATCH_PARENT
        wlp?.windowAnimations = R.style.ExpandAnimation
        dialog.window?.attributes = wlp
        dialog.setCancelable(true)

        binding.imgClose.setOnClickListener { dismiss() }
        binding.btnEndTrip.setOnClickListener { finish(serviceModel.id, serviceModel.priceService) }
        binding.txtTotalAmount.text = serviceModel.priceService
        binding.txtTax.text = serviceModel.tax
        binding.txtCompanyShare.text = serviceModel.commission
        binding.txtDiscount.text = serviceModel.discount
        binding.txtDriverShare.text = serviceModel.finalPrice
        binding.txtCustomerPrice.text = serviceModel.priceCustomer

        dialog.show()

    }

    private fun finish(serviceId: Int, price: String) {
        RequestHelper.builder(EndPoint.FINISH)
            .listener(finishCallBack)
            .addParam("serviceId", serviceId)
            .addParam("price", price)
            .post()
    }

    private val finishCallBack: RequestHelper.Callback = object : RequestHelper.Callback() {
        override fun onResponse(reCall: Runnable?, vararg args: Any?) {
            MyApplication.handler.post {
                try {
                    val jsonObject = JSONObject(args[0].toString())
                    val success = jsonObject.getBoolean("success")
                    val message = jsonObject.getString("message")
                    if (success) {
                        val dataArr = jsonObject.getJSONArray("data")
                        val result = dataArr.getJSONObject(0).getBoolean("result")
                        if (result) {
                            MyApplication.Toast(message, Toast.LENGTH_SHORT)
                            dismiss()
                            MyApplication.currentActivity.onBackPressed()
                        }
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

    private fun dismiss() {
        try {
            dialog.dismiss()
            KeyBoardHelper.hideKeyboard()
        } catch (e: Exception) {
            AvaCrashReporter.send(e, "FactorDialog class, dismiss method")
        }
    }

}