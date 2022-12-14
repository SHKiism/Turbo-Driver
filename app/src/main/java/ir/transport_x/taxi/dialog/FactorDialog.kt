package ir.transport_x.taxi.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import ir.transport_x.taxi.R
import ir.transport_x.taxi.app.EndPoint
import ir.transport_x.taxi.app.MyApplication
import ir.transport_x.taxi.databinding.DialogFactorBinding
import ir.transport_x.taxi.okHttp.RequestHelper
import ir.transport_x.taxi.push.AvaCrashReporter
import ir.transport_x.taxi.utils.KeyBoardHelper
import ir.transport_x.taxi.utils.StringHelper
import ir.transport_x.taxi.utils.TypeFaceUtilJava
import ir.transport_x.taxi.webServices.UpdateCharge
import org.json.JSONObject

class FactorDialog {

    lateinit var dialog: Dialog
    lateinit var binding: DialogFactorBinding


    interface FinishServiceListener {
        fun onFinishService(isFinish: Boolean)
    }

    private lateinit var finishServiceListener: FinishServiceListener

    fun show(priceObj: JSONObject, serId: Int, finishServiceListener: FinishServiceListener) {
        dialog = Dialog(MyApplication.currentActivity)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        binding = DialogFactorBinding.inflate(LayoutInflater.from(dialog.context))
        dialog.setContentView(binding.root)
        TypeFaceUtilJava.overrideFonts(binding.root, MyApplication.iranSansMediumTF)
        TypeFaceUtilJava.overrideFonts(binding.txtCreditType, MyApplication.iranSansTF)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val wlp: WindowManager.LayoutParams? = dialog.window?.attributes
        wlp?.gravity = Gravity.CENTER
        wlp?.width = WindowManager.LayoutParams.MATCH_PARENT
        wlp?.windowAnimations = R.style.ExpandAnimation
        dialog.window?.attributes = wlp
        dialog.setCancelable(true)

        this.finishServiceListener = finishServiceListener

        binding.imgClose.setOnClickListener { dismiss() }
        binding.btnEndTrip.setOnClickListener {
            GeneralDialog()
                .message("از اتمام سرویس اطمینان دارید؟")
                .firstButton("بله") {
                    finish(serId, priceObj.getString("priceService"))
                }
                .secondButton("خیر") {}
                .show()
        }
        binding.txtTotalAmount.text =
            StringHelper.toPersianDigits(
                StringHelper.setComma(priceObj.getString("priceService")))
        binding.txtTax.text =
            StringHelper.toPersianDigits(
                StringHelper.setComma(priceObj.getString("tax")))
        binding.txtCompanyShare.text =
            StringHelper.toPersianDigits(
                StringHelper.setComma(priceObj.getString("commission")))
        binding.txtDiscount.text =
            StringHelper.toPersianDigits(
                StringHelper.setComma(priceObj.getString("discount")))
        binding.txtDriverShare.text =
            StringHelper.toPersianDigits(
                StringHelper.setComma(priceObj.getString("finalPrice")))
        binding.txtCustomerPrice.text =
            StringHelper.toPersianDigits(
                StringHelper.setComma(priceObj.getString("priceCustomer")))

        dialog.show()

    }

    private fun finish(serviceId: Int, price: String) {
        binding.vfEndService.displayedChild = 1
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
                    binding.vfEndService.displayedChild = 0
                    val jsonObject = JSONObject(args[0].toString())
                    val success = jsonObject.getBoolean("success")
                    val message = jsonObject.getString("message")
                    if (success) {
                        val dataObj = jsonObject.getJSONObject("data")
                        val result = dataObj.getBoolean("result")
                        if (result) {
                            UpdateCharge().update(object : UpdateCharge.ChargeListener {
                                override fun getCharge(charge: String, response: String) {
                                }
                            })
                            GeneralDialog().message(message).firstButton("باشه") {
                                MyApplication.currentActivity.onBackPressed()
                            }.show()
                            finishServiceListener.onFinishService(true)
                            dismiss()
                        } else {
                            finishServiceListener.onFinishService(false)
                            GeneralDialog().message(message).secondButton("باشه") {}.show()
                        }
                    } else {
                        finishServiceListener.onFinishService(false)
                        GeneralDialog().message(message).secondButton("باشه") {}.show()
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                    binding.vfEndService.displayedChild = 0
                }
            }
        }

        override fun onFailure(reCall: Runnable?, e: java.lang.Exception?) {
            MyApplication.handler.post {
                binding.vfEndService.displayedChild = 0
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