package com.masudinn.finalbfaa.view.opsi

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat
import com.masudinn.finalbfaa.R
import com.masudinn.finalbfaa.Utils.Reminder


class SettingFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var reminderPreference: SwitchPreferenceCompat
    private lateinit var reminderUser: Reminder
    private lateinit var reminder: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = context?.getString(R.string.settings)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting, rootKey)

        reminderUser = Reminder()

        initReminder()
        initSharedPreference()
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == reminder){
            if (sharedPreferences != null){
                reminderPreference.isChecked = sharedPreferences.getBoolean(reminder, false)
            }
        }

        val state: Boolean = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(reminder, false)

        setReminder(state)
    }

    private fun initReminder(){
        reminder = getString(R.string.reminder_key)
        reminderPreference = findPreference<SwitchPreferenceCompat>(reminder) as SwitchPreferenceCompat
    }

    private fun initSharedPreference(){
        val sharedPreferences = preferenceManager.sharedPreferences
        reminderPreference.isChecked = sharedPreferences.getBoolean(reminder, false)
    }

    private fun setReminder(state: Boolean){
        if (state){
            context?.let {
                reminderUser.setRepeatingReminder(it)
            }
        } else {
            context?.let {
                reminderUser.cancelAlarm(it)
            }
        }
    }

}