package com.example.appssquaretask.ui.map.view

import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.appssquaretask.utils.GoogleMapDTO
import com.example.appssquaretask.R
import com.example.appssquaretask.ui.main.view.MainActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_map.*
import okhttp3.OkHttpClient
import okhttp3.Request

class MapActivity : AppCompatActivity() , OnMapReadyCallback{

    override fun onMapReady(p0: GoogleMap?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    lateinit var mapFragment:SupportMapFragment
    lateinit var googleMap:GoogleMap
    lateinit var latlngList:ArrayList<LatLng>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        latlngList= ArrayList()
        mapFragment=supportFragmentManager.findFragmentById(R.id.map)as SupportMapFragment
        mapFragment.getMapAsync {

            googleMap=it
            val location =LatLng(30.674774,30.9392334)
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,10f))
            googleMap.setOnMapClickListener {
                if(latlngList.size==2){
                    googleMap.clear()
                    latlngList.clear()
                    latlngList.add(LatLng(it.latitude,it.longitude))
                    googleMap.addMarker(MarkerOptions().position(LatLng(it.latitude,it.longitude)))


                }
                else{

                    latlngList.add(LatLng(it.latitude,it.longitude))
                    googleMap.addMarker(MarkerOptions().position(LatLng(it.latitude,it.longitude)))





                }

            }
//            googleMap.setOnMapLongClickListener {
//
//
////                Toast.makeText(this,it.latitude.toString(),Toast.LENGTH_LONG).show()
//            }
        }
        goBtn.setOnClickListener(View.OnClickListener {
            if(latlngList.size==2){

                val polyline1 = googleMap.addPolyline(PolylineOptions()
                    .clickable(true)
                    .add(
                        LatLng(latlngList.get(0).latitude,latlngList.get(0).longitude),
                        LatLng(latlngList.get(1).latitude,latlngList.get(1).longitude)))


//                        Log.d("GoogleMap", "before URL")
//                        val URL = getDirectionURL(latlngList.get(0),latlngList.get(1))
//                        Log.d("GoogleMap", "URL : $URL")
//                        GetDirection(URL).execute()
            }

        })
        nextBtn.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)

        })


    }
    fun getDirectionURL(origin:LatLng,dest:LatLng) : String{
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}&destination=${dest.latitude},${dest.longitude}&sensor=false&mode=driving&key=AIzaSyCZkz9-EUPuKSCbnu4zw0jmH9L2DRbI6f0"
    }
    private inner class GetDirection(val url : String) : AsyncTask<Void, Void, List<List<LatLng>>>(){
        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body?.string()
            Log.d("GoogleMap" , " data : $data")
            val result =  ArrayList<List<LatLng>>()
            try{
                val respObj = Gson().fromJson(data, GoogleMapDTO::class.java)

                val path =  ArrayList<LatLng>()

                for (i in 0..(respObj.routes[0].legs[0].steps.size-1)){
//                    val startLatLng = LatLng(respObj.routes[0].legs[0].steps[i].start_location.lat.toDouble()
//                            ,respObj.routes[0].legs[0].steps[i].start_location.lng.toDouble())
//                    path.add(startLatLng)
//                    val endLatLng = LatLng(respObj.routes[0].legs[0].steps[i].end_location.lat.toDouble()
//                            ,respObj.routes[0].legs[0].steps[i].end_location.lng.toDouble())
                    path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
                }
                result.add(path)
            }catch (e:Exception){
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(result: List<List<LatLng>>) {
            val lineoption = PolylineOptions()
            for (i in result.indices){
                lineoption.addAll(result[i])
                lineoption.width(10f)
                lineoption.color(Color.RED)
                lineoption.geodesic(true)
            }
            googleMap.addPolyline(lineoption)
        }
    }

    public fun decodePolyline(encoded: String): List<LatLng> {

        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val latLng = LatLng((lat.toDouble() / 1E5),(lng.toDouble() / 1E5))
            poly.add(latLng)
        }

        return poly
    }
}
