package ir.transport_x.taxi.app

import android.content.SharedPreferences
import com.google.android.gms.maps.model.LatLng

class PrefManager {

    private val prefName = MyApplication.context.applicationInfo.name
    private var sharedPreferences: SharedPreferences =
        MyApplication.context.getSharedPreferences(prefName, 0)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    private val USER_NAME = "userName"
    private val REFRESH_TOKEN = "refreshToken"
    private val AUTHORIZATION = "authorixation"
    private val ID_TOKEN = "idToken"
    private val LAST_NOTIFICATION = "lastNotification"
    private val KEY_AVA_PID = "AvaPID"
    private val KEY_AVA_TOKEN = "AvaToken"
    private val KEY_USE_ALARM_MANAGER = "userAlarmManager"
    private val KEY_APP_STATUS = "AppStatus"
    private val DRIVER_STATUS = "driverStatus"
    private val STATION_REGISTER_STATUS = "stationRegisterStatus"
    private val KEY_LAST_LAT = "lastLat"
    private val KEY_LAST_LNG = "lastLang"
    private val API_REQUEST_TIME = "requestTime"
    private val CHARGE = "charge"
    private val LOCK_STATUS = "LockStatus"
    private val LOCK_REASONES = "lockReasons"
    private val LOCK_DAYS = "lockDays"
    private val IBAN = "iban"
    private val NATIONAL_CODE = "nationlCode"
    private val REPEAT_TIME = "repeatTime"
    private val KEY_ACTIVATION_REMAINING_TIME = "activationRemainingTime"
    private val DRIVER_ID = "driverId"
    private val KEY_COUNT_NOTIFICATION = "countNotification"
    private val CARD_NUMBER = "cardNumber"
    private val CARD_NAME = "cardName"
    private val SUPPORT_NUMBER = "supportNumber"
    private val IS_FROM_GET_SERVICE_ACTIVITY = "IsFromGetServiceActivity"
    private val PRICING = "pricing"
    private val ONLINE_URL = "onlineUrl"
    private val ABOUT_US = "aboutUs"
    private val PUSH_URL = "pushUrl"
    private val MUTE_NOTIFICATIONS = "muteNotifications"
    private val CANCEL_REASON = "cancelReason"
    private val GPS_INTERVAL = "gpsInterval"
    private val GET_STATUS_INTERVAL = "getStatusInterval"
    private val ALLOW_TO_PLAY_VOICE = "allowToPlayVoice"

    var allowToPlayVoice: Boolean
        get() {
            return sharedPreferences.getBoolean(ALLOW_TO_PLAY_VOICE, true)
        }
        set(allowToPlayVoice) {
            editor.putBoolean(ALLOW_TO_PLAY_VOICE, allowToPlayVoice)
            editor.commit()
        }

    fun cleanPrefManger() {
        sharedPreferences.edit().clear().apply()
    }

    /**
     * when it is (true) it means notifications are muted
     **/
    var muteNotifications: Boolean
        get() {
            return sharedPreferences.getBoolean(MUTE_NOTIFICATIONS, false)
        }
        set(muteNotifications) {
            editor.putBoolean(MUTE_NOTIFICATIONS, muteNotifications)
            editor.commit()
        }

    var pushUrl: String?
        get() {
            return sharedPreferences.getString(PUSH_URL, "")
        }
        set(pushUrl) {
            editor.putString(PUSH_URL, pushUrl)
            editor.commit()
        }

    var aboutUs: String?
        get() {
            return sharedPreferences.getString(ABOUT_US, "")
        }
        set(aboutUs) {
            editor.putString(ABOUT_US, aboutUs)
            editor.commit()
        }

    var onlineUrl: String?
        get() {
            return sharedPreferences.getString(ONLINE_URL, "")
        }
        set(onlineUrl) {
            editor.putString(ONLINE_URL, onlineUrl)
            editor.commit()
        }

    var getStatusInterval: Int
        get() {
            return sharedPreferences.getInt(GET_STATUS_INTERVAL, 60000)
        }
        set(getStatusInterval) {
            editor.putInt(GET_STATUS_INTERVAL, getStatusInterval)
            editor.commit()
        }

    var gpsInterval: Int
        get() {
            return sharedPreferences.getInt(GPS_INTERVAL, 30000)
        }
        set(gpsInterval) {
            editor.putInt(GPS_INTERVAL, gpsInterval)
            editor.commit()
        }

    var pricing: Int
        get() {
            return sharedPreferences.getInt(PRICING, 0)
        }
        set(pricing) {
            editor.putInt(PRICING, pricing)
            editor.commit()
        }

    var lockDays: Int
        get() {
            return sharedPreferences.getInt(LOCK_DAYS, 0)
        }
        set(lockDays) {
            editor.putInt(LOCK_DAYS, lockDays)
            editor.commit()
        }

    var isFromGetServiceActivity: Boolean
        get() {
            return sharedPreferences.getBoolean(IS_FROM_GET_SERVICE_ACTIVITY, false)
        }
        set(isFromGetServiceActivity) {
            editor.putBoolean(IS_FROM_GET_SERVICE_ACTIVITY, isFromGetServiceActivity)
            editor.commit()
        }

    var supportNumber: String?
        get() {
            return sharedPreferences.getString(SUPPORT_NUMBER, "")
        }
        set(supportNumber) {
            editor.putString(SUPPORT_NUMBER, supportNumber)
            editor.commit()
        }

    var cardNumber: String?
        get() {
            return sharedPreferences.getString(CARD_NUMBER, "")
        }
        set(cardNumber) {
            editor.putString(CARD_NUMBER, cardNumber)
            editor.commit()
        }

    var cardName: String?
        get() {
            return sharedPreferences.getString(CARD_NAME, "")
        }
        set(cardName) {
            editor.putString(CARD_NAME, cardName)
            editor.commit()
        }

    var cancelReason: String?
        get() {
            return sharedPreferences.getString(CANCEL_REASON, "")
        }
        set(cancelReason) {
            editor.putString(CANCEL_REASON, cancelReason)
            editor.commit()
        }


    fun setCountNotification(count: Int) {
        editor.putInt(KEY_COUNT_NOTIFICATION, count)
        editor.commit()
    }

    fun getCountNotification(): Int {
        return sharedPreferences.getInt(KEY_COUNT_NOTIFICATION, 0)
    }

    fun setActivationRemainingTime(v: Long) {
        editor.putLong(KEY_ACTIVATION_REMAINING_TIME, v)
        editor.commit()
    }

    fun getActivationRemainingTime(): Long {
        return sharedPreferences.getLong(KEY_ACTIVATION_REMAINING_TIME, 60000)
    }

    fun getRefreshToken(): String? {
        return sharedPreferences.getString(REFRESH_TOKEN, "")
    }

    fun setApiRequestTime(i: Long) {
        editor.putLong(API_REQUEST_TIME, i)
        editor.commit()
    }

    fun getApiRequestTime(): Long {
        return sharedPreferences.getLong(API_REQUEST_TIME, 0)
    }

    fun getLastLocation(): LatLng {
        return LatLng(
            sharedPreferences.getFloat(KEY_LAST_LAT, 36.317265F).toDouble(),
            sharedPreferences.getFloat(KEY_LAST_LNG, 59.562635F).toDouble()
        )
    }

    fun setLastLocation(location: LatLng) {
        editor.putFloat(KEY_LAST_LNG, location.longitude.toFloat())
        editor.putFloat(KEY_LAST_LAT, location.latitude.toFloat())
        editor.commit()
    }

    fun getDriverStatus(): Boolean {
        return sharedPreferences.getBoolean(DRIVER_STATUS, false)
    }

    fun setDriverStatus(v: Boolean) {
        editor.putBoolean(DRIVER_STATUS, v)
        editor.commit()
    }

    fun getRepetitionTime(): Int {
        return sharedPreferences.getInt(REPEAT_TIME, 0)
    }

    fun setRepetitionTime(v: Int) {
        editor.putInt(REPEAT_TIME, v)
        editor.commit()
    }

    fun getDriverId(): Int {
        return sharedPreferences.getInt(DRIVER_ID, 0)
    }

    fun setDriverId(v: Int) {
        editor.putInt(DRIVER_ID, v)
        editor.commit()
    }

    fun getLockStatus(): Int {
        return sharedPreferences.getInt(LOCK_STATUS, 0)
    }

    fun setLockStatus(v: Int) {
        editor.putInt(LOCK_STATUS, v)
        editor.commit()
    }

    fun getLockReasons(): String {
        return sharedPreferences.getString(LOCK_REASONES, "").toString()
    }

    fun setLockReasons(v: String) {
        editor.putString(LOCK_REASONES, v)
        editor.commit()
    }

    fun getIban(): String {
        return sharedPreferences.getString(IBAN, "").toString()
    }

    fun setIban(v: String) {
        editor.putString(IBAN, v)
        editor.commit()
    }

    fun getNational(): String {
        return sharedPreferences.getString(NATIONAL_CODE, "").toString()
    }

    fun setNational(v: String) {
        editor.putString(NATIONAL_CODE, v)
        editor.commit()
    }

    fun getStationRegisterStatus(): Boolean {
        return sharedPreferences.getBoolean(STATION_REGISTER_STATUS, false)
    }

    fun setStationRegisterStatus(v: Boolean) {
        editor.putBoolean(STATION_REGISTER_STATUS, v)
        editor.commit()
    }

    fun useAlarmManager(): Boolean {
        return sharedPreferences.getBoolean(KEY_USE_ALARM_MANAGER, true)
    }

    fun setUseAlarmManager(v: Boolean) {
        editor.putBoolean(KEY_USE_ALARM_MANAGER, v)
        editor.commit()
    }

    fun setAppRun(v: Boolean) {
        editor.putBoolean(KEY_APP_STATUS, v)
        editor.commit()
    }

    fun isAppRun(): Boolean {
        return sharedPreferences.getBoolean(KEY_APP_STATUS, false)
    }

    fun getAvaPID(): Int {
        return sharedPreferences.getInt(KEY_AVA_PID, 3)
    }

    fun setAvaPID(v: Int) {
        editor.putInt(KEY_AVA_PID, v)
        editor.commit()
    }

    fun getAvaToken(): String? {
        return sharedPreferences.getString(KEY_AVA_TOKEN, null)
    }

    fun setAvaToken(v: String?) {
        editor.putString(KEY_AVA_TOKEN, v)
        editor.commit()
    }

    fun setRefreshToken(refreshToken: String) {
        editor.putString(REFRESH_TOKEN, refreshToken)
        editor.commit()
    }

    fun getAuthorization(): String? {
        return sharedPreferences.getString(AUTHORIZATION, "")
    }

    fun setAuthorization(authorization: String?) {
        editor.putString(AUTHORIZATION, authorization)
        editor.commit()
    }

    fun getCharge(): String? {
        return sharedPreferences.getString(CHARGE, "")
    }

    fun setCharge(charge: String?) {
        editor.putString(CHARGE, charge)
        editor.commit()
    }

    fun getIdToken(): String? {
        return sharedPreferences.getString(ID_TOKEN, "")
    }

    fun setIdToken(idToken: String?) {
        editor.putString(ID_TOKEN, idToken)
        editor.commit()
    }

    fun getLastNotification(): String? {
        return sharedPreferences.getString(LAST_NOTIFICATION, null)
    }

    fun setLastNotification(v: String?) {
        editor.putString(LAST_NOTIFICATION, v)
        editor.commit()
    }

    fun getUserName(): String? {
        return sharedPreferences.getString(USER_NAME, "")
    }

    fun setUserName(userName: String) {
        editor.putString(USER_NAME, userName)
        editor.commit()
    }


}