package com.example.rssirecorder

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.content.Context
import android.net.wifi.WifiManager
import android.net.wifi.ScanResult
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView

class WifiListAdapter(context: Context, private val scanResults: List<ScanResult>) :
        ArrayAdapter<ScanResult>(context, android.R.layout.simple_list_item_1, scanResults) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = scanResults[position].SSID
        Log.d("RSSI", scanResults[position].SSID)
        return view
    }
}
class MainActivity : AppCompatActivity() {

    private lateinit var wifiManager: WifiManager
    private lateinit var listView: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        listView = findViewById(R.id.listView)


        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val scanResults = wifiManager.scanResults
            if (scanResults.isEmpty()) {
                Log.d("SCAN", "There are no available Wi-Fi networks.")
            } else {
                Log.d("SCAN", "There are available Wi-Fi networks.")
            }
            val wifiListAdapter = WifiListAdapter(this, scanResults)
            listView.adapter = wifiListAdapter

            wifiManager.startScan()
            wifiListAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}