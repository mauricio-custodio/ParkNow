package com.mcustodio.parknow.view.edit

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.gms.maps.model.LatLng
import com.mcustodio.parknow.R
import com.mcustodio.parknow.hideWhenKeyboardIsVisible
import com.mcustodio.parknow.model.ParkingLot
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProviders.of(this).get(EditViewModel::class.java) }
    val parkingId by lazy { intent.getLongExtra(KEY_ID, -1) }
    val initialPosition by lazy { LatLng(intent.getDoubleExtra(KEY_LAT, 0.0), intent.getDoubleExtra(KEY_LNG, 0.0)) }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        fab_edit_done.hideWhenKeyboardIsVisible(this)
        setupFields()
        setupView()
        setupViewModel()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigateUp(): Boolean {
        finish()
        return super.onNavigateUp()
    }

    private fun setupFields() {
        input_edit_name
        input_edit_description
        input_edit_address
        input_edit_phone
        input_edit_openat
        input_edit_closeat
        input_edit_priceperhour
        input_edit_priceperday
        input_edit_pricepermonth
        fab_edit_done
    }

    private fun setupView() {
        fab_edit_done.setOnClickListener {
            val parkingLot = ParkingLot()
            parkingLot.name = input_edit_name.text.toString()
            parkingLot.description = input_edit_description.text.toString()
            parkingLot.address = input_edit_address.text.toString()
            parkingLot.phone = input_edit_phone.text.toString()
//            parkingLot.openAt = input_edit_openat
//            parkingLot.closeAt = input_edit_closeat
//            parkingLot.pricePerHour = input_edit_priceperhour.text.toString().toFloat()
//            parkingLot.pricePerDay = input_edit_priceperday.text.toString().toFloat()
//            parkingLot.pricePerMonth = input_edit_pricepermonth.text.toString().toFloat()
            parkingLot.latitude = initialPosition.latitude
            parkingLot.longitude = initialPosition.longitude
            viewModel.insert(parkingLot)
        }
    }

    private fun setupViewModel() {
        viewModel.insertSuccess.observe(this, Observer {
            if (it == true) finish()
        })
    }



    companion object {
        const val KEY_ID = "KEY_ID"
        const val KEY_LAT = "KEY_LAT"
        const val KEY_LNG = "KEY_LNG"

        fun launchNew(context: Context, pos: LatLng) {
            val intent = Intent(context, EditActivity::class.java)
            intent.putExtra(KEY_LAT, pos.latitude)
            intent.putExtra(KEY_LNG, pos.longitude)
            context.startActivity(intent)
        }

        fun launchEdit(context: Context, id: Long) {
            val intent = Intent(context, EditActivity::class.java)
            intent.putExtra(KEY_ID, id)
            context.startActivity(intent)
        }
    }
}
